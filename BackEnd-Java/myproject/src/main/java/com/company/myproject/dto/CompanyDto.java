package com.company.myproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyDto {

    private UUID id;
    private String name;
    private Set<AssetDto> assets;
    private Set<BranchDto> branches;

    public CompanyDto() {
    }

    public CompanyDto(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AssetDto> getAssets() {
        return assets;
    }

    public void setAssets(Set<AssetDto> assets) {
        this.assets = assets;
    }

    public Set<BranchDto> getBranches() {
        return branches;
    }

    public void setBranches(Set<BranchDto> branches) {
        this.branches = branches;
    }
}