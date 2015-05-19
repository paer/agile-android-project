package com.example.paer.agileproject.implementations;

import android.content.Context;

/**
 * Loads all the branches for a certain user
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubBranchAsyncTask extends LoadingAsyncTask<Void, Void, String[]> {

    public GithubBranchAsyncTask(Context context) {
        super(context, "Loading branches", "Please wait, loading all the branches...");
    }

    /**
     * @return The branches received, null if none
     */
    @Override
    protected String[] doInBackground(Void... voids) {
        return new String[]{
                "Branch 1",
                "Branch 2",
                "Branch 3"
        };
    }

}
