package com.company.myproject.service;

import com.company.myproject.dto.EmployeeDto;
import com.company.myproject.model.Employee;
import com.company.myproject.model.OfficeEmployee;
import com.company.myproject.model.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EmployeeService {

    EmployeeDto findByIdWithList(UUID id);

    List<EmployeeDto> findAll(Pageable pageable);

    List<EmployeeDto> findEmployees(UUID companyId, UUID branchId, UUID officeId, Pageable pageable);

    Long getTotalCount (UUID companyId, UUID branchId, UUID officeId);

    EmployeeDto save(EmployeeDto employeeDto);

    EmployeeDto update(EmployeeDto employeeDto);

    void addOrUpdateOffice(EmployeeDto employeeDto, Employee employee);

    void removeOffice(EmployeeDto employeeDto, Employee employee);

    EmployeeDto deleteById(UUID employeeId);

    void checkDatesForOffice(Set<OfficeEmployee> offices);

    void checkDatesForEmployee(Set<OfficeEmployee> offices);

    void checkNullDateTo(Set<OfficeEmployee> offices);

}
