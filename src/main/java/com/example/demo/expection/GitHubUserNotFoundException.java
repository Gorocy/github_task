package com.example.demo.expection;

public class GitHubUserNotFoundException extends RuntimeException {

    public GitHubUserNotFoundException(String username) {
        super("GitHub user not found: " + username);
    }
}
