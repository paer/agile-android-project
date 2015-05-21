package com.example.paer.agileproject;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryCommit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 21.05.15.
 */
public class GithubProjectInfoBundle {
    public List<RepositoryCommit> commits;
    public List<Issue> issues;
    public ArrayList<String> branches;

    public GithubProjectInfoBundle() {
        commits = new ArrayList<RepositoryCommit>();
        issues = new ArrayList<Issue>();
        branches = new ArrayList<String>();
    }

    @Override
    public String toString() {
        return commits.toString() + " " + issues.toString() + " " + branches.toString();
    }
}
