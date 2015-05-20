package com.example.paer.agileproject.implementations;

import android.content.Context;
import android.util.Pair;
import android.widget.Toast;

import org.eclipse.egit.github.core.client.GitHubClient;

/**
 * Authenticates the user against Github
 *
 * @author Marc
 * @since 2015-05
 */
public abstract class GithubAuthenticateAsyncTask extends LoadingAsyncTask<String, Void, Pair<GithubAuthenticateAsyncTask.AuthenticationResult, GitHubClient>> {

    /**
     * Sets the needed references
     * @param context The invoking context
     */
    public GithubAuthenticateAsyncTask(Context context) {
        super(context, "Authenticating", "Please wait while the information is being authenticated.");
    }

    public enum AuthenticationResult {
        MissingInformation,
        MissingUsername,
        MissingPassword,
        Success
    }

    @Override
    protected Pair<AuthenticationResult, GitHubClient> doInBackground(String... strings) {
        if(strings.length != 2) {
            return new Pair<>(AuthenticationResult.MissingInformation, null);
        }
        String username = strings[0];
        String password = strings[1];

        if(username.isEmpty())
            return new Pair<>(AuthenticationResult.MissingUsername, null);
        if(password.isEmpty())
            return new Pair<>(AuthenticationResult.MissingPassword, null);

        GitHubClient client = new GitHubClient();
        client.setUserAgent("agile-android-project");
        client.setCredentials(username, password);

        return new Pair<>(AuthenticationResult.Success, client);
    }

    @Override
    protected void onPostExecute(Pair<AuthenticationResult, GitHubClient> authenticationResultGitHubClientPair) {
        super.onPostExecute(authenticationResultGitHubClientPair);

        AuthenticationResult result = authenticationResultGitHubClientPair.first;
        GitHubClient client = authenticationResultGitHubClientPair.second;

        switch (result){
            case MissingInformation:
                Toast.makeText(context, "Could not authenticate, missing information.", Toast.LENGTH_LONG).show();
                break;
            case MissingUsername:
                Toast.makeText(context, "Could not authenticate, missing username.", Toast.LENGTH_LONG).show();
                break;
            case MissingPassword:
                Toast.makeText(context, "Could not authenticate, missing password.", Toast.LENGTH_LONG).show();
                break;
            case Success:
                if(client == null){
                    Toast.makeText(context, "Could not authenticate, could not initiate client.", Toast.LENGTH_LONG).show();
                } else {
                    onSuccessfulAuthentication(client);
                }
                break;
        }
    }

    public abstract void onSuccessfulAuthentication(GitHubClient client);
}
