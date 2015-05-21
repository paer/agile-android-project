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

    public GithubProjectAsyncTask(Context context) {
        super(context, "Loading projects", "Please wait, loading all the projects...");
    }

    @Override
    protected ArrayList<String> doInBackground(GitHubClient... gitHubClients) {
        ArrayList<String> projectNames = new ArrayList<String>();
        projectNames.add("Select a project");

        try {
            RepositoryService service = new RepositoryService(gitHubClients[0]);
            List<Repository> repos = service.getRepositories();
            for (Repository repo : repos) {
                projectNames.add(repo.getName());
            }
        } catch (Exception e) {
            Log.e("GithubProjectAsyncTask.doInBackground", e.getMessage());
            return null;
        }

        return projectNames;
    }
}
