package com.company.myproject.service;

import com.company.myproject.dto.CompanyDto;
import com.company.myproject.dto.EmployeeDto;
import com.company.myproject.exception.EntityNotFoundException;
import com.company.myproject.dao.CompanyRepository;
import com.company.myproject.exception.BadRequestException;
import com.company.myproject.model.Company;
import com.company.myproject.model.Employee;
import com.company.myproject.model.Pageable;
import com.company.myproject.util.ConvertCompanyDto;
import com.company.myproject.util.ConvertEmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    CompanyRepository companyRepository;
    BranchService branchService;
    AssetService assetService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, BranchService branchService, AssetService assetService) {
        this.companyRepository = companyRepository;
        this.branchService = branchService;
        this.assetService = assetService;
    }

    @Override
    public CompanyDto findById(UUID companyId) {
        Company theCompany = companyRepository.findById(companyId);
        if (theCompany == null) {
            logger.warn("Company id not found - " + companyId);
            throw new EntityNotFoundException("Company id not found - " + companyId);
        }
        return ConvertCompanyDto.convertToCompanyDtoPlain(theCompany);
    }

    @Override
    public List<CompanyDto> findAll(Pageable pageable) {
        List<Company> companies = companyRepository.findAllPaging(pageable);
        return ConvertCompanyDto.convertToCompanyDtoPlainList(companies);
    }

    @Override
    public CompanyDto findCompanyForOffice(UUID officeId) {
        Company company = companyRepository.findCompanyForOffice(officeId);
        if (company == null) {
            logger.debug("Company not found");
            throw new EntityNotFoundException("Company not found");
        }
        return ConvertCompanyDto.convertToCompanyDtoPlain(company);
    }

    @Override
    public CompanyDto findCompanyForEmployee(UUID employeeId) {
        Company company = companyRepository.findCompanyForEmployee(employeeId);
        if (company == null) {
            logger.debug("Company not found");
            throw new EntityNotFoundException("Company not found");
        }
        return ConvertCompanyDto.convertToCompanyDtoPlain(company);
    }

    @Override
    public Long getTotalCount() {
        Long totalCount = companyRepository.findTotalCount();
        if (totalCount == null) {
            logger.warn("Total count could not be found");
            throw new BadRequestException("Total count could not be found");
        }
        return totalCount;
    }


    @Override
    @Transactional
    public CompanyDto save(CompanyDto companyDto) {
        if (companyDto.getId() != null) {
            logger.warn("Company id should be null");
            throw new BadRequestException("Company id should be null");
        }
        Company company = ConvertCompanyDto.convertToCompanyPlain(companyDto);
        companyRepository.save(company);
        return ConvertCompanyDto.convertToCompanyDtoPlain(company);
    }

    @Override
    @Transactional
    public CompanyDto update(CompanyDto companyDto) {
        Company company = companyRepository.findById(companyDto.getId());
        if (company == null) {
            logger.warn("Company id not found - " + companyDto.getId());
            throw new EntityNotFoundException("Company id not found - " + companyDto.getId());
        }
        if (companyDto.getName() != null && !(companyDto.getName().isBlank())) {
            company.setName(companyDto.getName());
        }
        companyRepository.update(company);
        return ConvertCompanyDto.convertToCompanyDtoPlain(company);
    }

    @Override
    @Transactional
    public String deleteById(UUID companyId) {
        Company tempCompany = companyRepository.findById(companyId);
        if (tempCompany == null) {
            logger.warn("Company id not found - " + companyId);
            throw new EntityNotFoundException("Company id not found - " + companyId);
        }
        companyRepository.deleteById(companyId);
        return "DELETED SUCCESSFULLY!!!";
//        return ConvertCompanyDto.convertToCompanyDtoPlain(tempCompany);
    }

}
