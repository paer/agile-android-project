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

/**
 * Loads all the branches for a certain user
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubBranchAsyncTask extends LoadingAsyncTask<String, Void, ArrayList<String>> {

    private GitHubClient githubClient;

    public GithubBranchAsyncTask(Context context, GitHubClient client) {
        super(context, "Loading branches", "Please wait, loading all the branches...");
        this.githubClient = client;
    }

    /**
     * @return The branches received, null if none
     */
    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        if(strings.length != 1)
            return null;
        String branchName = strings[0]; // "agile-android-project"

        ArrayList<String> branchNames = new ArrayList<>();
        branchNames.add("Select a branch");
        try {
            RepositoryService repositoryService = new RepositoryService(githubClient);
            List<RepositoryBranch> branches = repositoryService.getBranches(new RepositoryId("paer", branchName));
            for (RepositoryBranch branch : branches) {
                branchNames.add(branch.getName());
            }
        }
        catch (Exception e) {
            Log.e("GithubBranchAsyncTask.doInBackground", e.getMessage());
        }

        return branchNames;
    }
}
