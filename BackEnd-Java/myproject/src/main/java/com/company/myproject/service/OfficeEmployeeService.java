package com.company.myproject.service;

import com.company.myproject.model.Employee;
import com.company.myproject.model.OfficeEmployee;
import com.company.myproject.model.OfficeEmployeeId;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OfficeEmployeeService {

    OfficeEmployee findByCompositeId(OfficeEmployee officeEmployee);

    Set<OfficeEmployee> findOfficeEmployees(Instant dateTo);

    void save(OfficeEmployee officeEmployee);

    void update(OfficeEmployee officeEmployee);

    void deleteById(OfficeEmployeeId id);

    Set<OfficeEmployee> findOfficeEmployeesByEmployeeId(UUID employeeId);

}
