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
public class GithubBranchAsyncTask extends LoadingAsyncTask<Void, Void, ArrayList<String>> {

    private GitHubClient githubClient;
    private final Spinner branchSpinner;

    public GithubBranchAsyncTask(Context context, Spinner branchSpinner, GitHubClient client) {
        super(context, "Loading branches", "Please wait, loading all the branches...");

        this.branchSpinner = branchSpinner;
        this.githubClient = client;
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

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);

        if(strings != null && !strings.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, strings);
            branchSpinner.setAdapter(adapter);
        } else {
            Toast.makeText(context, "Branches received were null", Toast.LENGTH_LONG);
        }
    }
}
