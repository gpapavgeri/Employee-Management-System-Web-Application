package com.company.myproject.util;

import com.company.myproject.dto.AssetDto;
import com.company.myproject.dto.BranchDto;
import com.company.myproject.dto.CompanyDto;
import com.company.myproject.model.Asset;
import com.company.myproject.model.Company;
import com.company.myproject.model.Branch;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ConvertCompanyDto {

    public static Company convertToCompanyPlain(CompanyDto companyDto) {
        Company company = new Company();

        company.setId(companyDto.getId());
        company.setName(companyDto.getName());

        return company;
    }

    public static CompanyDto convertToCompanyDtoPlain(Company company) {
        CompanyDto companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setName(company.getName());

        return companyDto;
    }

    public static CompanyDto convertToCompanyDtoWithBranches(Company company) {
        CompanyDto companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setName(company.getName());

        // get Branches
        Set<Branch> branches = company.getBranches();
        List<Branch> branchList = branches.stream().collect(Collectors.toList());
        List<BranchDto> branchDtoList = ConvertBranchDto.convertToBranchDtoPlainList(branchList);

        Set<BranchDto> branchDtos = branchDtoList.stream().collect(Collectors.toSet());
        companyDto.setBranches(branchDtos);

        return companyDto;
    }

    public static CompanyDto convertToCompanyDtoWithAssets(Company company) {
        CompanyDto companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        //get Assets
        Set<Asset> assets = company.getAssets();
        List<Asset> assetList = assets.stream().collect(Collectors.toList());
        List<AssetDto> assetDtoList = ConvertAssetDto.convertToAssetDtoPlainList(assetList);

        Set<AssetDto> assetDtos = assetDtoList.stream().collect(Collectors.toSet());
        companyDto.setAssets(assetDtos);

        return companyDto;
    }

    public static CompanyDto convertToCompanyDtoWithLists(Company company) {
        CompanyDto companyDto = new CompanyDto();

        companyDto.setId(company.getId());
        companyDto.setName(company.getName());
        //get Assets
        Set<Asset> assets = company.getAssets();
        List<Asset> assetList = assets.stream().collect(Collectors.toList());
        List<AssetDto> assetDtoList = ConvertAssetDto.convertToAssetDtoPlainList(assetList);

        Set<AssetDto> assetDtos = assetDtoList.stream().collect(Collectors.toSet());
        companyDto.setAssets(assetDtos);

        // get Branches
        Set<Branch> branches = company.getBranches();
        List<Branch> branchList = branches.stream().collect(Collectors.toList());
        List<BranchDto> branchDtoList = ConvertBranchDto.convertToBranchDtoPlainList(branchList);

        Set<BranchDto> branchDtos = branchDtoList.stream().collect(Collectors.toSet());
        companyDto.setBranches(branchDtos);

        return companyDto;
    }


    public static List<Company> convertToCompanyPlainList(List<CompanyDto> companyDtos) {
        return companyDtos.stream().map(ConvertCompanyDto::convertToCompanyPlain).collect(Collectors.toList());
    }

    public static List<CompanyDto> convertToCompanyDtoPlainList(List<Company> companies) {
        return companies.stream().map(ConvertCompanyDto::convertToCompanyDtoPlain).collect(Collectors.toList());
    }

    public static List<CompanyDto> convertToCompanyDtoListWithBranches(List<Company> companies) {
        return companies.stream().map(ConvertCompanyDto::convertToCompanyDtoWithBranches).collect(Collectors.toList());
    }

    public static List<CompanyDto> convertToCompanyDtoListWithAssets(List<Company> companies) {
        return companies.stream().map(ConvertCompanyDto::convertToCompanyDtoWithAssets).collect(Collectors.toList());
    }

    public static List<CompanyDto> convertToCompanyDtoListWithLists(List<Company> companies) {
        return companies.stream().map(ConvertCompanyDto::convertToCompanyDtoWithLists).collect(Collectors.toList());
    }







}
