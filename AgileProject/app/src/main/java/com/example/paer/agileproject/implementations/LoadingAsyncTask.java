package com.example.paer.agileproject.implementations;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Adds a simple loading dialog to the AsyncTask
 *
 * @author Marc
 * @since 2015-05
 */
public abstract class LoadingAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    /** Reference to the invoking context */
    protected Context context;

    /** Reference to the dialog */
    private ProgressDialog dialog;

    /** Title of the dialog */
    private String title;

    /** Message of the dialog */
    private String message;

    public LoadingAsyncTask(Context context, String title, String message) {
        this.context = context;
        this.title = title;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);

        dialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        dialog.dismiss();
    }
}
