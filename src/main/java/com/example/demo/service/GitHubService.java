package com.example.demo.service;

import com.example.demo.exception.GitHubUserNotFoundException;
import com.example.demo.model.Branch;
import com.example.demo.model.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;
    private final String githubApiUrl;

    public GitHubService(@Value("${github.api.url}") String githubApiUrl) {
        this.restTemplate = new RestTemplate();
        this.githubApiUrl = githubApiUrl;
    }

    public List<Repository> listNonForkRepositories(String username) {
        try {
            String url = String.format("%s/users/%s/repos", githubApiUrl, username);
            Optional<Repository[]> optionalRepositories = Optional.ofNullable(restTemplate.getForObject(url, Repository[].class));

            return optionalRepositories
                    .map(repositories -> Arrays.stream(repositories)
                            .filter(repo -> !repo.isFork())
                            .map(repo -> {
                                List<Branch> branches = fetchBranches(repo);
                                repo.setBranches(branches);
                                return repo;
                            })
                            .collect(Collectors.toList()))
                    .orElseThrow(() -> new GitHubUserNotFoundException(username));
        } catch (HttpClientErrorException.NotFound e) {
            throw new GitHubUserNotFoundException(username);
        }
    }

    private List<Branch> fetchBranches(Repository repository) {
        String url = repository.getBranchesUrl().replace("{/branch}", "");
        Branch[] branches = restTemplate.getForObject(url, Branch[].class);
        return Arrays.asList(branches);
    }
}
