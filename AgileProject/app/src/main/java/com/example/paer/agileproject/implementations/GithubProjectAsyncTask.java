package com.example.paer.agileproject.implementations;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import org.eclipse.egit.github.core.client.GitHubClient;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubProjectAsyncTask extends LoadingAsyncTask<GitHubClient, Void, String[]> {

    private Spinner projectSpinner;

    public GithubProjectAsyncTask(Context context, Spinner projectSpinner) {
        super(context, "Loading projects", "Please wait, loading all the projects...");

        this.projectSpinner = projectSpinner;
    }

    @Override
    protected String[] doInBackground(GitHubClient... gitHubClients) {
        return new String[]{
                "Project 1",
                "Project 2",
                "Project 3"
        };
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);

        if(strings != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, strings);
            projectSpinner.setAdapter(adapter);
        } else {
            Toast.makeText(context, "Branches received were null", Toast.LENGTH_LONG);
        }
    }
}
