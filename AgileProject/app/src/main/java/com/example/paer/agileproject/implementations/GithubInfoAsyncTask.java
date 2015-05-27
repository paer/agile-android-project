package com.example.paer.agileproject.implementations;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.example.paer.agileproject.GithubProject;
import com.example.paer.agileproject.GithubProjectInfoBundle;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alex on 21.05.15.
 */
public class GithubInfoAsyncTask extends LoadingAsyncTask<Pair<GitHubClient, GithubProject>, Void, GithubProjectInfoBundle> {

    public GithubInfoAsyncTask(Context context) {
        super(context, "Loading data", "Please wait, loading all data...");
    }

    @Override
    protected GithubProjectInfoBundle doInBackground(Pair<GitHubClient, GithubProject>... githubPair) {
        GithubProjectInfoBundle bundle = new GithubProjectInfoBundle();
        GitHubClient client = githubPair[0].first;
        GithubProject project = githubPair[0].second;
        try {
            RepositoryService repositoryService = new RepositoryService(client);
            CommitService commitService = new CommitService(client);
            IssueService issueService = new IssueService(client);
            RepositoryId repo = RepositoryId.create(project.getOwner(), project.getName());
            PageIterator<RepositoryCommit> commits = commitService.pageCommits(repo);

            for (Collection<RepositoryCommit> page : commits) {
                for (RepositoryCommit commit : page) {
                    bundle.commits.add(commit);
                }
            }

            bundle.issues = issueService.getIssues(repo, null);

            List<RepositoryBranch> branches = repositoryService.getBranches(repo);
            for (RepositoryBranch branch : branches) {
                bundle.branches.add(branch.getName());
            }

        } catch (Exception e) {
            Log.e("GithubProjectAsyncTask", e.getMessage());
            return null;
        }
        return bundle;
    }
}
