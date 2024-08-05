package com.example.demo.service;

import com.example.demo.exception.GitHubUserNotFoundException;
import com.example.demo.model.Branch;
import com.example.demo.model.Repository;
import com.example.demo.response.BranchResponse;
import com.example.demo.response.RepositoryResponse;
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

    public List<RepositoryResponse> listNonForkRepositories(String username) {
        try {
            String url = String.format("%s/users/%s/repos", githubApiUrl, username);
            Optional<Repository[]> optionalRepositories = Optional.ofNullable(restTemplate.getForObject(url, Repository[].class));

            return optionalRepositories
                    .map(repositories -> Arrays.stream(repositories)
                            .filter(repo -> !repo.isFork())
                            .map(repo -> {
                                List<BranchResponse> branches = fetchBranches(repo);
                                return mapToRepositoryResponse(repo, branches);
                            })
                            .collect(Collectors.toList()))
                    .orElseThrow(() -> new GitHubUserNotFoundException(username));
        } catch (HttpClientErrorException.NotFound e) {
            throw new GitHubUserNotFoundException(username);
        }
    }

    private List<BranchResponse> fetchBranches(Repository repository) {
        String url = repository.getBranchesUrl().replace("{/branch}", "");
        Branch[] branches = restTemplate.getForObject(url, Branch[].class);
        return Arrays.stream(branches)
                .map(this::mapToBranchResponse)
                .collect(Collectors.toList());
    }

    private RepositoryResponse mapToRepositoryResponse(Repository repository, List<BranchResponse> branches) {
        RepositoryResponse response = new RepositoryResponse();
        response.setName(repository.getName());
        response.setOwner(repository.getOwner().getLogin());
        response.setBranches(branches);
        return response;
    }

    private BranchResponse mapToBranchResponse(Branch branch) {
        BranchResponse response = new BranchResponse();
        response.setName(branch.getName());
        response.setCommitSha(branch.getCommit().getSha());
        return response;
    }
}
