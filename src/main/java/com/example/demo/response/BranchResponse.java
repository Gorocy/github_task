package com.example.demo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchResponse {
    private String name;

    private String commitSha;

}