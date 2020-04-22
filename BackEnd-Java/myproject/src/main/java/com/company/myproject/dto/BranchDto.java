package com.company.myproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BranchDto {

    private UUID id;
    private String name;
    private CompanyDto company;
    List<OfficeDto> offices;

    public BranchDto() {
    }

    public BranchDto(String name) {
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

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public List<OfficeDto> getOffices() {
        return offices;
    }

    public void setOffices(List<OfficeDto> offices) {
        this.offices = offices;
    }

}
