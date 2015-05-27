package com.example.paer.agileproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paer.agileproject.GithubProject;
import com.example.paer.agileproject.GithubProjectInfoBundle;
import com.example.paer.agileproject.R;
import com.example.paer.agileproject.implementations.GithubInfoAsyncTask;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GitHubService;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class ProjectInfoFragment extends Fragment {

    private GitHubClient client;
    private String username;
    private String password;
    private String project;
    private String branch;
    private String owner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences shared = getActivity().getSharedPreferences("SetupActivity", Context.MODE_PRIVATE);
        this.username = (shared.getString("username", ""));
        this.password = (shared.getString("password", ""));
        this.project = (shared.getString("project", "agile-android-project"));
        this.branch = (shared.getString("branch", "master"));
        this.owner = (shared.getString("owner", "paer"));

        this.client = new GitHubClient();
        this.client.setUserAgent("agile-android-project");
        this.client.setCredentials(username, password);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_github_projectinfo, container, false);

        final TextView projectNameText = (TextView)view.findViewById(R.id.github_project_info_name);
        final View lastCommitView = view.findViewById(R.id.github_project_info_last_commit_entry);
        final TextView lastCommitMessage = (TextView)lastCommitView.findViewById(R.id.commit_list_element_message);
        final TextView lastCommitName = (TextView)lastCommitView.findViewById(R.id.commit_list_element_name);
        final TextView lastCommitTime = (TextView)lastCommitView.findViewById(R.id.commit_list_element_time);
        final TextView numberOfIssues = (TextView)view.findViewById(R.id.github_project_info_issues);
        final LinearLayout branchListView = (LinearLayout)view.findViewById(R.id.github_project_info_branch_layout);

        projectNameText.setText(this.project);

        if (branchListView.getChildCount() == 0) {
            new GithubInfoAsyncTask(ProjectInfoFragment.this.getActivity()) {
                @Override
                protected void onPostExecute(GithubProjectInfoBundle githubProjectInfoBundle) {
                    super.onPostExecute(githubProjectInfoBundle);
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    lastCommitMessage.setText(githubProjectInfoBundle.commits.get(0).getCommit().getMessage());
                    lastCommitName.setText(githubProjectInfoBundle.commits.get(0).getCommit().getAuthor().getName());
                    lastCommitTime.setText(df.format(githubProjectInfoBundle.commits.get(0).getCommit().getAuthor().getDate()));
                    numberOfIssues.append(Integer.toString(githubProjectInfoBundle.issues.size()));
                    for (String branch : githubProjectInfoBundle.branches) {
                        TextView branchText = new TextView(getActivity());
                        branchText.setText(branch);
                        branchListView.addView(branchText);
                    }
                }
            }.execute(new Pair(client, new GithubProject(project, owner)));
        }

        return view;
    }
}
