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

/**
 * Loads all the branches for a certain user
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubBranchAsyncTask extends LoadingAsyncTask<Void, Void, ArrayList<String>> {

    private GitHubClient githubClient;

    public GithubBranchAsyncTask(Context context, GitHubClient client) {
        super(context, "Loading branches", "Please wait, loading all the branches...");
        githubClient = client;
    }

    /**
     * @return The branches received, null if none
     */
    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        ArrayList<String> branchNames = new ArrayList<String>();
        try {
            RepositoryService repositoryService = new RepositoryService(githubClient);
            List<RepositoryBranch> branches = repositoryService.getBranches(new RepositoryId("paer", "agile-android-project"));
            for (RepositoryBranch branch : branches) {
                branchNames.add(branch.getName());
            }
        }
        catch (Exception e) {
            Log.e("Github Branches", e.getMessage());
        }

        return branchNames;
    }

}
