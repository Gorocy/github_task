package com.example.demo.controller;

import com.example.demo.model.Repository;
import com.example.demo.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listUserRepositories(
            @PathVariable String username,
            @RequestHeader(value = "Accept", required = false) String acceptHeader) {

        if (acceptHeader == null || !acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Accept header must be 'application/json'");
        }

        List<Repository> repositories = gitHubService.listNonForkRepositories(username);
        return ResponseEntity.ok(repositories);
    }
}

