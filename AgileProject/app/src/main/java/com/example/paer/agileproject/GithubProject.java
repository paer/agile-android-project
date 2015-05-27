package com.example.paer.agileproject;

/**
 * Created by alex on 27.05.15.
 */
public class GithubProject {
    private String name;
    private String owner;

    public GithubProject(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return name;
    }
}
