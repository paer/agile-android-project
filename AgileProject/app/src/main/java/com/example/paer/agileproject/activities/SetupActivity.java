package com.example.paer.agileproject.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.example.paer.agileproject.R;

import com.example.paer.agileproject.implementations.GithubAuthenticateAsyncTask;
import com.example.paer.agileproject.implementations.GithubBranchAsyncTask;
import com.example.paer.agileproject.implementations.GithubProjectAsyncTask;

import org.eclipse.egit.github.core.client.GitHubClient;

import java.util.ArrayList;

/**
 * Sets up the initial information required by the application
 * @author alex
 * @since 2015-03-05
 */
public class SetupActivity extends Activity {

    /** The last successfully authenticated username */
    private String mUsername = null;
    /** The last successfully authenticated password */
    private String mPassword = null;
    /** The last chosen project */
    private String mProject = null;
    /** The last chosen branch */
    private String mBranch = null;
    /** The client */
    private GitHubClient githubClient;
    /** The owner of the project */
    private String mOwner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences("SetupActivity", Context.MODE_PRIVATE);
        String user = settings.getString("username", null);
        if(user != null){
            Intent intent = new Intent(SetupActivity.this, MainActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.fragment_setup_github);

        final EditText usernameField = (EditText) findViewById(R.id.setup_github_username_textfield);
        final EditText passwordField = (EditText) findViewById(R.id.setup_github_password_textfield);
        final Button authenticate = (Button) findViewById(R.id.setup_github_authenticate_button);
        final Spinner projectSpinner = (Spinner) findViewById(R.id.setup_github_project_spinner);
        projectSpinner.setEnabled(false);
        final Spinner branchSpinner = (Spinner) findViewById(R.id.setup_github_branch_spinner);
        branchSpinner.setEnabled(false);
        final Button finish = (Button) findViewById(R.id.setup_github_finish_button);
        finish.setEnabled(false);

        // Set what happens when the authentication button was pressed
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                final String usernameFinal = usernameField.getText().toString();
                final String passwordFinal = passwordField.getText().toString();

                new GithubAuthenticateAsyncTask(SetupActivity.this) {
                    @Override
                    public void onSuccessfulAuthentication(GitHubClient client) {
                        mUsername = usernameFinal;
                        mPassword = passwordFinal;
                        githubClient = client;
                        new GithubProjectAsyncTask(SetupActivity.this){
                            @Override
                            protected void onPostExecute(ArrayList<GithubProject> projects) {
                                super.onPostExecute(projects);

                                if(projects != null && !projects.isEmpty()) {
                                    ArrayAdapter<GithubProject> adapter = new ArrayAdapter<GithubProject>(context, android.R.layout.simple_spinner_item, projects);
                                    projectSpinner.setAdapter(adapter);
                                    projectSpinner.setEnabled(true);
                                } else {
                                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }.execute(client);
                    }
                }.execute(usernameFinal, passwordFinal);

            }
        });

        projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GithubProject item = (GithubProject)parent.getItemAtPosition(position);
                mProject = item.getName();
                mOwner = item.getOwner();
                new GithubBranchAsyncTask(SetupActivity.this, githubClient) {
                    @Override
                    protected void onPostExecute(ArrayList<String> strings) {
                        super.onPostExecute(strings);

                        if (strings != null && !strings.isEmpty()) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, strings);
                            branchSpinner.setAdapter(adapter);
                            branchSpinner.setEnabled(true);
                        } else {
                            Toast.makeText(context, "No branches received.", Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBranch = parent.getItemAtPosition(position).toString();
                finish.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                // If all items selected
                if(mUsername != null && mPassword != null && mProject != null && mBranch != null){
                    // Store information
                    SharedPreferences settings = SetupActivity.this.getSharedPreferences("SetupActivity", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", mUsername);
                    editor.putString("password", mPassword);
                    editor.putString("project", mProject);
                    editor.putString("branch", mBranch);
                    editor.putString("owner", mOwner);
                    editor.apply();

                    Intent intent = new Intent(SetupActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
