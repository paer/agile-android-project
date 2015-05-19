package com.example.paer.agileproject.implementations;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Loads all the branches for a certain user
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubBranchAsyncTask extends LoadingAsyncTask<Void, Void, String[]> {

    private final Spinner branchSpinner;

    public GithubBranchAsyncTask(Context context, Spinner branchSpinner) {
        super(context, "Loading branches", "Please wait, loading all the branches...");

        this.branchSpinner = branchSpinner;
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

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);

        if(strings != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, strings);
            branchSpinner.setAdapter(adapter);
        } else {
            Toast.makeText(context, "Branches received were null", Toast.LENGTH_LONG);
        }
    }
}
