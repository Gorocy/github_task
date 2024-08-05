package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Repository {
    private String name;

    @JsonProperty("owner")
    private Owner owner;

    private boolean fork;

    @JsonProperty("branches_url")
    private String branchesUrl;

    private List<Branch> branches;

    @Data
    public static class Owner {
        private String login;
    }
}

