package com.example.paer.agileproject.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.paer.agileproject.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.example.paer.agileproject.implementations.GithubAuthenticateAsyncTask;
import com.example.paer.agileproject.implementations.GithubBranchAsyncTask;

import java.util.Arrays;

/**
 * Sets up the initial information required by the application
 * @author alex
 * @since 2015-03-05
 */
public class SetupFragment extends Fragment {

    /** The last successfully authenticated username */
    private String username;
    /** The last successfully authenticated password */
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_setup_github, container, false);

        final EditText usernameField = (EditText) view.findViewById(R.id.setup_github_username_textfield);
        final EditText passwordField = (EditText) view.findViewById(R.id.setup_github_password_textfield);
        final Button authenticate = (Button) view.findViewById(R.id.setup_github_authenticate_button);
        final Spinner projects = (Spinner) view.findViewById(R.id.setup_github_project_spinner);
        final Button finish = (Button) view.findViewById(R.id.setup_github_finish_button);
        finish.setEnabled(false);

        // Set what happens when the authentication has result
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                final String usernameFinal = usernameField.getText().toString();
                final String passwordFinal = passwordField.getText().toString();

                new GithubAuthenticateAsyncTask(SetupFragment.this.getActivity()) {
                    @Override
                    protected void onPostExecute(AuthenticationResult authenticationResult) {
                        super.onPostExecute(authenticationResult);

                        if (authenticationResult == AuthenticationResult.Success) {
                            // TODO: Store username and password? (they are in class variables)
                            finish.setEnabled(true);
                            username = usernameFinal;
                            password = passwordFinal;

                        } else {
                            Toast.makeText(SetupFragment.this.getActivity(), "Could not authenticate, please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute(usernameFinal, passwordFinal);
            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {

                // TODO: Check if we are allowed to do this yet
                // TODO: Check that a valid project and branch is selected

                new GithubBranchAsyncTask(SetupFragment.this.getActivity()) {
                    @Override
                    protected void onPostExecute(String[] strings) {
                        super.onPostExecute(strings);

                        if (strings == null) {
                            Toast.makeText(SetupFragment.this.getActivity(), "Could not get branches, please try again", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.e("Github Branches", Arrays.toString(strings));
                    }
                }.execute();

            /*
            GithubFragment newFragment = new GithubFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            */
            }
        });

        return view;
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/
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
