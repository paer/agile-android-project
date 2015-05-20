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
        Success,
        AuthenticationFailed,
        ConnectionFailed
    }

    @Override
    protected Pair<AuthenticationResult, GitHubClient> doInBackground(String... strings) {
        if(strings.length != 2) {
            return new Pair(AuthenticationResult.MissingInformation, null);
        }
        String username = strings[0];
        String password = strings[1];

        GitHubClient client = new GitHubClient();
        client.setUserAgent("agile-android-project");
        client.setCredentials(username, password);

        return new Pair(AuthenticationResult.Success, client);
    }

    @Override
    protected void onPostExecute(Pair<AuthenticationResult, GitHubClient> authenticationResultGitHubClientPair) {
        super.onPostExecute(authenticationResultGitHubClientPair);

        if (authenticationResultGitHubClientPair.first == AuthenticationResult.Success) {
            onSuccessfulAuthentication(authenticationResultGitHubClientPair.second);
        } else {
            Toast.makeText(context, "Could not authenticate; " + authenticationResultGitHubClientPair.first, Toast.LENGTH_LONG).show();
        }
    }

    public abstract void onSuccessfulAuthentication(GitHubClient client);
}
