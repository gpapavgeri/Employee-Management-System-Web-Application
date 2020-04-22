package com.company.myproject.util;

import com.company.myproject.dto.EmployeeDto;
import com.company.myproject.model.Employee;
import com.company.myproject.model.OfficeEmployee;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ConvertEmployeeDto {

    public static EmployeeDto convertToEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());

        Set<OfficeEmployee> officeEmployees = employee.getOffices();
        Set<EmployeeDto.OfficeDto> offices = ConvertOfficeEmployeeDto.convertToOfficeDtoList(officeEmployees);
        employeeDto.setOffices(offices);

        return  employeeDto;
    }

    public static EmployeeDto convertToEmployeeDtoPlain(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());

        return  employeeDto;
    }

    public static Employee convertToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();

        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());

        return employee;
    }

    public static List<EmployeeDto> convertToEmployeeDtoList(List<Employee> employees) {
        return employees.stream().map(ConvertEmployeeDto::convertToEmployeeDto).collect(Collectors.toList());
    }

    public static List<EmployeeDto> convertToEmployeeDtoPlainList(List<Employee> employees) {
        return employees.stream().map(ConvertEmployeeDto::convertToEmployeeDtoPlain).collect(Collectors.toList());
    }



}
