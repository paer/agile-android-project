package com.example.paer.agileproject.implementations;

import android.content.Context;

/**
 * Authenticates the user against Github
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubAuthenticateAsyncTask extends LoadingAsyncTask<String, Void, GithubAuthenticateAsyncTask.AuthenticationResult> {

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
    protected AuthenticationResult doInBackground(String... strings) {
        if(strings.length != 2) {
            return AuthenticationResult.MissingInformation;
        }
        String username = strings[0];
        String password = strings[1];

        // TODO: Do authentication here, return AuthenticationResult

        return AuthenticationResult.Success;
    }
}
