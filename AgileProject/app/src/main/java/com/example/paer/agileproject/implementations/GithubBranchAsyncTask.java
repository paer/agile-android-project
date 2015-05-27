package com.example.paer.agileproject.implementations;

import android.content.Context;
import android.util.Log;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paer.agileproject.GithubProject;

/**
 * Loads all the branches for a certain user
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubBranchAsyncTask extends LoadingAsyncTask<GithubProject, Void, ArrayList<String>> {

    private GitHubClient githubClient;

    public GithubBranchAsyncTask(Context context, GitHubClient client) {
        super(context, "Loading branches", "Please wait, loading all the branches...");
        this.githubClient = client;
    }

    /**
     * @return The branches received, null if none
     */
    @Override
    protected ArrayList<String> doInBackground(GithubProject... projects) {
        if(projects.length != 1)
            return null;
        String projectName = projects[0].getName(); // "agile-android-project"
        String projectOwner = projects[0].getOwner();

        ArrayList<String> branchNames = new ArrayList<>();
        try {
            RepositoryService repositoryService = new RepositoryService(githubClient);
            List<RepositoryBranch> branches = repositoryService.getBranches(new RepositoryId(projectOwner, projectName));
            for (RepositoryBranch branch : branches) {
                branchNames.add(branch.getName());
            }
        }
        catch (Exception e) {
            Log.e("doInBackground", e.getMessage());
        }

        return branchNames;
    }
}
