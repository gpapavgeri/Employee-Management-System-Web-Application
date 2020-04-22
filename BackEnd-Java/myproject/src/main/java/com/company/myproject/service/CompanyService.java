package com.company.myproject.service;

import com.company.myproject.dto.CompanyDto;
import com.company.myproject.dto.EmployeeDto;
import com.company.myproject.model.Pageable;

import java.util.List;
import java.util.UUID;

public interface CompanyService {

    CompanyDto findById(UUID companyId);

    List<CompanyDto> findAll(Pageable pageable);

    CompanyDto findCompanyForOffice(UUID officeId);

    CompanyDto findCompanyForEmployee(UUID employeeId);

    Long getTotalCount();

    CompanyDto save(CompanyDto companyDto);

    CompanyDto update(CompanyDto companyDto);

    String deleteById(UUID companyId);

}
