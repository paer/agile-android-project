package com.example.paer.agileproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.paer.agileproject.GithubProject;
import com.example.paer.agileproject.GithubProjectInfoBundle;
import com.example.paer.agileproject.R;
import com.example.paer.agileproject.implementations.GithubInfoAsyncTask;

import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;

import java.text.SimpleDateFormat;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class CommitsFragment extends Fragment {
    private GitHubClient client;
    private String username;
    private String password;
    private String project;
    private String branch;
    private String owner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences shared = getActivity().getSharedPreferences("SetupFragment", Context.MODE_PRIVATE);
        this.username = (shared.getString("username", ""));
        this.password = (shared.getString("password", ""));
        this.project = (shared.getString("project", ""));
        this.owner = (shared.getString("owner", ""));
        this.branch = (shared.getString("branch", ""));

        this.client = new GitHubClient();
        this.client.setUserAgent("agile-android-project");
        this.client.setCredentials(username, password);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        SharedPreferences shared = getActivity().getSharedPreferences("SetupFragment", Context.MODE_PRIVATE);
        GitHubClient client = new GitHubClient();
        client.setUserAgent("agile-android-project");
        client.setCredentials(shared.getString("username", ""), shared.getString("password", ""));

        View view = inflater.inflate(R.layout.fragment_github_commits, container, false);
        TextView branchNameText = (TextView)view.findViewById(R.id.commits_branch_name);
        final LinearLayout commitsList = (LinearLayout)view.findViewById(R.id.commits_linear_layout);
        branchNameText.setText(shared.getString("branch", ""));

        if (commitsList.getChildCount() == 0) {
            new GithubInfoAsyncTask(CommitsFragment.this.getActivity()) {
                @Override
                protected void onPostExecute(GithubProjectInfoBundle githubProjectInfoBundle) {
                    super.onPostExecute(githubProjectInfoBundle);
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    for (int i = 0; i < 10; i++) {
                        RepositoryCommit repoCommit = githubProjectInfoBundle.commits.get(i);
                        RelativeLayout commitLayout = (RelativeLayout) getLayoutInflater(savedInstanceState).inflate(R.layout.commit_list_element, commitsList, false);
                        TextView commitMessage = (TextView) commitLayout.findViewById(R.id.commit_list_element_message);
                        TextView commitName = (TextView) commitLayout.findViewById(R.id.commit_list_element_name);
                        TextView commitTime = (TextView) commitLayout.findViewById(R.id.commit_list_element_time);

                        commitMessage.setText(repoCommit.getCommit().getMessage());
                        commitName.setText(repoCommit.getCommit().getAuthor().getName());
                        commitTime.setText(df.format(repoCommit.getCommit().getAuthor().getDate()));


                        commitsList.addView(commitLayout);
                    }
                }
            }.execute(new Pair(client, new GithubProject(project, owner)));
        }

        return view;
    }
}
