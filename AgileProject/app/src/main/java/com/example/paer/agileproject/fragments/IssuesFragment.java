package com.example.paer.agileproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.paer.agileproject.GithubProjectInfoBundle;
import com.example.paer.agileproject.R;
import com.example.paer.agileproject.implementations.GithubInfoAsyncTask;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;

import java.text.SimpleDateFormat;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class IssuesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        SharedPreferences shared = getActivity().getSharedPreferences("SetupFragment", Context.MODE_PRIVATE);
        GitHubClient client = new GitHubClient();
        client.setUserAgent("agile-android-project");
        client.setCredentials(shared.getString("username", ""), shared.getString("password", ""));

        View view = inflater.inflate(R.layout.fragment_github_issues, container, false);
        final LinearLayout issuesList = (LinearLayout)view.findViewById(R.id.issues_linear_layout);

        if (issuesList.getChildCount() == 0) {
            new GithubInfoAsyncTask(IssuesFragment.this.getActivity()) {
                @Override
                protected void onPostExecute(GithubProjectInfoBundle githubProjectInfoBundle) {
                    super.onPostExecute(githubProjectInfoBundle);
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    for (Issue issue : githubProjectInfoBundle.issues) {
                        RelativeLayout issuesLayout = (RelativeLayout) getLayoutInflater(savedInstanceState).inflate(R.layout.issue_list_element, issuesList, false);
                        TextView issueTitle = (TextView) issuesLayout.findViewById(R.id.issue_list_element_title);
                        TextView issueName = (TextView) issuesLayout.findViewById(R.id.issue_list_element_name);
                        TextView issueTime = (TextView) issuesLayout.findViewById(R.id.issue_list_element_time);

                        issueTitle.setText(issue.getTitle());
                        issueName.setText(issue.getUser().getName());
                        issueTime.setText(df.format(issue.getCreatedAt()));


                        issuesList.addView(issuesLayout);
                    }
                }
            }.execute(client);
        }

        return view;
    }
}
