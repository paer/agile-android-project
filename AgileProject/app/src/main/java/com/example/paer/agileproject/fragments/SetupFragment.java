package com.example.paer.agileproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.paer.agileproject.GithubProject;
import com.example.paer.agileproject.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.example.paer.agileproject.implementations.GithubAuthenticateAsyncTask;
import com.example.paer.agileproject.implementations.GithubBranchAsyncTask;
import com.example.paer.agileproject.implementations.GithubProjectAsyncTask;

import database.DataBaseHandler;
import org.eclipse.egit.github.core.client.GitHubClient;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Sets up the initial information required by the application
 * @author alex
 * @since 2015-03-05
 */
public class SetupFragment extends Fragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_setup_github, container, false);

        final EditText usernameField = (EditText) view.findViewById(R.id.setup_github_username_textfield);
        final EditText passwordField = (EditText) view.findViewById(R.id.setup_github_password_textfield);
        final Button authenticate = (Button) view.findViewById(R.id.setup_github_authenticate_button);
        final Spinner projectSpinner = (Spinner) view.findViewById(R.id.setup_github_project_spinner);
        projectSpinner.setEnabled(false);
        final Spinner branchSpinner = (Spinner) view.findViewById(R.id.setup_github_branch_spinner);
        branchSpinner.setEnabled(false);
        final Button finish = (Button) view.findViewById(R.id.setup_github_finish_button);
        finish.setEnabled(false);

        // Set what happens when the authentication button was pressed
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                final String usernameFinal = usernameField.getText().toString();
                final String passwordFinal = passwordField.getText().toString();

                new GithubAuthenticateAsyncTask(SetupFragment.this.getActivity()) {
                    @Override
                    public void onSuccessfulAuthentication(GitHubClient client) {
                        mUsername = usernameFinal;
                        mPassword = passwordFinal;
                        githubClient = client;
                        new GithubProjectAsyncTask(SetupFragment.this.getActivity()){
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
                new GithubBranchAsyncTask(SetupFragment.this.getActivity(), githubClient) {
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
                    SharedPreferences settings = getActivity().getSharedPreferences("SetupFragment", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", mUsername);
                    editor.putString("password", mPassword);
                    editor.putString("project", mProject);
                    editor.putString("branch", mBranch);
                    editor.putString("owner", mOwner);
                    editor.apply();

                    GithubFragment newFragment = new GithubFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.flContent, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
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
