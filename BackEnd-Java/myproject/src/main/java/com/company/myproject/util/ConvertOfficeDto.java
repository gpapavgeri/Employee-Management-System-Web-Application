package com.company.myproject.util;

import com.company.myproject.dto.BranchDto;
import com.company.myproject.dto.OfficeDto;
import com.company.myproject.model.AssetOffice;
import com.company.myproject.model.Branch;
import com.company.myproject.model.Office;
import com.company.myproject.model.OfficeEmployee;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ConvertOfficeDto {

    public static Office convertToOffice(OfficeDto officeDto) {
        Office office = new Office();

        office.setId(officeDto.getId());
        office.setCode(officeDto.getCode());
        Branch branch = ConvertBranchDto.convertToBranch(officeDto.getBranch());
        office.setBranch(branch);

        return office;
    }

    public static OfficeDto convertToOfficeDto(Office office) {
        OfficeDto officeDto = new OfficeDto();

        officeDto.setId(office.getId());
        officeDto.setCode(office.getCode());
        BranchDto branchDto = ConvertBranchDto.convertToBranchDtoPlain(office.getBranch());
        officeDto.setBranch(branchDto);

        Set<OfficeEmployee> officeEmployees = office.getEmployees();
        Set<OfficeDto.EmployeeDto> employees = ConvertOfficeEmployeeDto.convertToEmployeeDtoList(officeEmployees);
        officeDto.setEmployees(employees);

        Set<AssetOffice> assetOffices = office.getAssets();
        Set<OfficeDto.AssetDto> assets = ConvertAssetOfficeDto.convertToAssetDtoList(assetOffices);
        officeDto.setAssets(assets);

        return officeDto;
    }

    public static OfficeDto convertToOfficeDtoPlain(Office office) {
        OfficeDto officeDto = new OfficeDto();

        officeDto.setId(office.getId());
        officeDto.setCode(office.getCode());
        BranchDto branchDto = ConvertBranchDto.convertToBranchDtoPlain(office.getBranch());
        officeDto.setBranch(branchDto);

        return officeDto;
    }

    public static OfficeDto convertToOfficeDtoWithEmployees(Office office) {
        OfficeDto officeDto = new OfficeDto();

        officeDto.setId(office.getId());
        officeDto.setCode(office.getCode());
        BranchDto branchDto = ConvertBranchDto.convertToBranchDto(office.getBranch());
        officeDto.setBranch(branchDto);

        Set<OfficeEmployee> officeEmployees = office.getEmployees();
        Set<OfficeDto.EmployeeDto> employees = ConvertOfficeEmployeeDto.convertToEmployeeDtoList(officeEmployees);
        officeDto.setEmployees(employees);

        return officeDto;
    }

    public static OfficeDto convertToOfficeDtoWithAssets(Office office) {
        OfficeDto officeDto = new OfficeDto();

        officeDto.setId(office.getId());
        officeDto.setCode(office.getCode());
        BranchDto branchDto = ConvertBranchDto.convertToBranchDto(office.getBranch());
        officeDto.setBranch(branchDto);

        Set<AssetOffice> assetOffices = office.getAssets();
        Set<OfficeDto.AssetDto> assets = ConvertAssetOfficeDto.convertToAssetDtoList(assetOffices);
        officeDto.setAssets(assets);

        return officeDto;
    }


    public static List<Office> convertToOfficeList(List<OfficeDto> officeDtos) {
        return officeDtos.stream().map(ConvertOfficeDto::convertToOffice).collect(Collectors.toList());
    }

    public static List<OfficeDto> convertToOfficeDtoList(List<Office> offices) {
        return offices.stream().map(ConvertOfficeDto::convertToOfficeDto).collect(Collectors.toList());
    }

    public static List<OfficeDto> convertToOfficeDtoPlainList(List<Office> offices) {
        return offices.stream().map(ConvertOfficeDto::convertToOfficeDtoPlain).collect(Collectors.toList());
    }

    public static Set<OfficeDto> convertToOfficeDtoWithEmployeesList(Set<Office> offices) {
        return offices.stream().map(ConvertOfficeDto::convertToOfficeDtoWithEmployees).collect(Collectors.toSet());
    }

    public static Set<OfficeDto> convertToOfficeDtoWithAssetsList(Set<Office> offices) {
        return offices.stream().map(ConvertOfficeDto::convertToOfficeDtoWithAssets).collect(Collectors.toSet());
    }


}
