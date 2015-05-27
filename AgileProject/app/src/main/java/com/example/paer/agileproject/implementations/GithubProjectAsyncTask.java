package com.example.paer.agileproject.implementations;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paer.agileproject.GithubProject;

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
public class GithubProjectAsyncTask extends LoadingAsyncTask<GitHubClient, Void, ArrayList<GithubProject>> {

    public GithubProjectAsyncTask(Context context) {
        super(context, "Loading projects", "Please wait, loading all the projects...");
    }

    @Override
    protected ArrayList<GithubProject> doInBackground(GitHubClient... gitHubClients) {
        ArrayList<GithubProject> projectNames = new ArrayList<GithubProject>();

        try {
            RepositoryService service = new RepositoryService(gitHubClients[0]);
            List<Repository> repos = service.getRepositories();
            for (Repository repo : repos) {
                projectNames.add(new GithubProject(repo.getName(), repo.getOwner().getLogin()));
            }
        } catch (Exception e) {
            Log.e("GithubProjectAsyncTask", e.getMessage());
            return null;
        }

        return projectNames;
    }
}
