package com.example.paer.agileproject.implementations;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubProjectAsyncTask extends LoadingAsyncTask<GitHubClient, Void, ArrayList<String>> {

    private Spinner projectSpinner;

    public GithubProjectAsyncTask(Context context, Spinner projectSpinner) {
        super(context, "Loading projects", "Please wait, loading all the projects...");

        this.projectSpinner = projectSpinner;
    }

    @Override
    protected ArrayList<String> doInBackground(GitHubClient... gitHubClients) {
        ArrayList<String> projectNames = new ArrayList<String>();

        try {
            RepositoryService service = new RepositoryService(gitHubClients[0]);
            List<Repository> repos = service.getRepositories();
            for (Repository repo : repos) {
                projectNames.add(repo.getName());
            }
        } catch (Exception e) {
            Log.e("GithubProjectAsyncTask.doInBackground", e.getMessage());
        }

        return projectNames;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

        if(strings != null && !strings.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, strings);
            projectSpinner.setAdapter(adapter);
        } else {
            Toast.makeText(context, "No projects received.", Toast.LENGTH_LONG).show();
        }
    }
}
