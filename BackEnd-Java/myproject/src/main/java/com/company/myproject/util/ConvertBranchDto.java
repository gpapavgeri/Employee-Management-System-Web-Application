package com.company.myproject.util;

import com.company.myproject.dto.BranchDto;
import com.company.myproject.dto.CompanyDto;
import com.company.myproject.dto.OfficeDto;
import com.company.myproject.model.Branch;
import com.company.myproject.model.Company;
import com.company.myproject.model.Office;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertBranchDto {

    public static BranchDto convertToBranchDto(Branch branch) {
        BranchDto branchDto = new BranchDto();

        branchDto.setId(branch.getId());
        branchDto.setName(branch.getName());

        Company company = branch.getCompany();
        CompanyDto companyDto = ConvertCompanyDto.convertToCompanyDtoPlain(company);
        branchDto.setCompany(companyDto);

        List<Office> offices = branch.getOffices();
        List<OfficeDto> officeDtos = ConvertOfficeDto.convertToOfficeDtoPlainList(offices);
        branchDto.setOffices(officeDtos);

        return  branchDto;
    }

    public static BranchDto convertToBranchDtoPlain(Branch branch) {
        BranchDto branchDto = new BranchDto();

        branchDto.setId(branch.getId());
        branchDto.setName(branch.getName());

        if (branch.getCompany()!=null){
            Company company = branch.getCompany();
            CompanyDto companyDto = ConvertCompanyDto.convertToCompanyDtoPlain(company);
            branchDto.setCompany(companyDto);
        }
        return  branchDto;
    }

    public static BranchDto convertToBranchDtoCompany(Branch branch) {
        BranchDto branchDto = new BranchDto();

        branchDto.setId(branch.getId());
        branchDto.setName(branch.getName());

        return  branchDto;
    }

    public static Branch convertToBranch(BranchDto branchDto) {
        Branch branch = new Branch();

        branch.setId(branchDto.getId());
        branch.setName(branchDto.getName());

        return branch;
    }

    public static List<BranchDto> convertToBranchDtoList(List<Branch> branches) {
        return branches.stream().map(ConvertBranchDto::convertToBranchDto).collect(Collectors.toList());
    }

    public static List<BranchDto> convertToBranchDtoPlainList(List<Branch> branches) {
        return branches.stream().map(ConvertBranchDto::convertToBranchDtoPlain).collect(Collectors.toList());
    }

    public static List<BranchDto> convertToBranchDtoCompanyList(List<Branch> branches) {
        return branches.stream().map(ConvertBranchDto::convertToBranchDtoCompany).collect(Collectors.toList());
    }

    public static List<Branch> convertToBranchList(List<BranchDto> branchesDto) {
        return branchesDto.stream().map(ConvertBranchDto::convertToBranch).collect(Collectors.toList());
    }




}
