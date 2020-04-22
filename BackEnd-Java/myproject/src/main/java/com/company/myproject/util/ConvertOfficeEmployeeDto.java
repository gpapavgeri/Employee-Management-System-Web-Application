package com.company.myproject.util;

import com.company.myproject.dto.AssetDto;
import com.company.myproject.dto.EmployeeDto;
import com.company.myproject.dto.OfficeDto;
import com.company.myproject.model.OfficeEmployee;

import java.util.Set;
import java.util.stream.Collectors;


public class ConvertOfficeEmployeeDto {

    public static EmployeeDto.OfficeDto convertToOfficeDto(OfficeEmployee officeEmployee) {
        EmployeeDto.OfficeDto officeDto = new EmployeeDto.OfficeDto();

        officeDto.setOfficeId(officeEmployee.getOfficeId());
        officeDto.setDateFrom(officeEmployee.getDateFrom());
        officeDto.setDateTo(officeEmployee.getDateTo());

        return officeDto;
    }

    public static OfficeDto.EmployeeDto convertToEmployeeDto(OfficeEmployee officeEmployee) {
        OfficeDto.EmployeeDto employeeDto = new  OfficeDto.EmployeeDto();

        employeeDto.setEmployeeId(officeEmployee.getEmployeeId());
        employeeDto.setDateFrom(officeEmployee.getDateFrom());
        employeeDto.setDateTo(officeEmployee.getDateTo());

        return employeeDto;
    }

    public static OfficeEmployee convertToOfficeEmployee(EmployeeDto.OfficeDto officeDto) {
        OfficeEmployee officeEmployee = new OfficeEmployee();

        officeEmployee.setOfficeId(officeDto.getOfficeId());
        officeEmployee.setDateFrom(officeDto.getDateFrom());
        officeEmployee.setDateTo(officeDto.getDateTo());

        return officeEmployee;
    }

    public static OfficeEmployee convertToOfficeEmployee(OfficeDto.EmployeeDto employeeDto) {
        OfficeEmployee officeEmployee = new OfficeEmployee();

        officeEmployee.setEmployeeId(employeeDto.getEmployeeId());
        officeEmployee.setDateFrom(employeeDto.getDateFrom());
        officeEmployee.setDateTo(employeeDto.getDateTo());

        return officeEmployee;
    }

    public static Set<EmployeeDto.OfficeDto> convertToOfficeDtoList(Set<OfficeEmployee> officeEmployees) {
        if(officeEmployees != null){
            return officeEmployees.stream().map(ConvertOfficeEmployeeDto::convertToOfficeDto).collect(Collectors.toSet());
        }
        return null;
    }

    public static Set<OfficeDto.EmployeeDto> convertToEmployeeDtoList(Set<OfficeEmployee> officeEmployees) {
        if(officeEmployees != null){
            return officeEmployees.stream().map(ConvertOfficeEmployeeDto::convertToEmployeeDto).collect(Collectors.toSet());
        }
        return null;
    }

}
