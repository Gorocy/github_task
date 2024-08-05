package com.example.demo.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryResponse {
    private String name;
    private String owner;
    private List<BranchResponse> branches;
}