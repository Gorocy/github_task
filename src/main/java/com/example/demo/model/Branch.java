package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Branch {
    private String name;

    @JsonProperty("commit")
    private Commit commit;

    @Data
    public static class Commit {
        private String sha;
    }
}