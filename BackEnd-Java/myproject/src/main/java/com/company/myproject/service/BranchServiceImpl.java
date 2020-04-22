package com.company.myproject.service;

import com.company.myproject.dao.BranchRepository;
import com.company.myproject.dao.CompanyRepository;
import com.company.myproject.dto.BranchDto;
import com.company.myproject.exception.EntityNotFoundException;
import com.company.myproject.exception.BadRequestException;
import com.company.myproject.model.Branch;
import com.company.myproject.model.Company;
import com.company.myproject.model.Pageable;
import com.company.myproject.util.ConvertBranchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Convert;
import java.util.List;
import java.util.UUID;

@Service
public class BranchServiceImpl implements BranchService {

    private static final Logger logger = LoggerFactory.getLogger(BranchService.class);

    BranchRepository branchRepository;
    CompanyRepository companyRepository;
    OfficeService officeService;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, CompanyRepository companyRepository, OfficeService officeService) {
        this.branchRepository = branchRepository;
        this.companyRepository = companyRepository;
        this.officeService = officeService;
    }

    @Override
    public BranchDto findById(UUID id) {
        Branch branch = branchRepository.findById(id);
        if(branch == null) {
            logger.warn("Branch id not found - " + id);
            throw new EntityNotFoundException("Branch id not found - " + id);
        }
        System.out.println(branch);
        return ConvertBranchDto.convertToBranchDtoPlain(branch);
    }

    @Override
    public BranchDto findByIdWithList(UUID id) {
        Branch branch = branchRepository.findByIdWithList(id);
        if(branch == null) {
            logger.warn("Branch id not found - " + id);
            throw new EntityNotFoundException("Branch id not found - " + id);
        }
        System.out.println(branch);
        return ConvertBranchDto.convertToBranchDto(branch);
    }

    @Override
    public List<BranchDto> findAll(Pageable pageable) {
        List<Branch> branches = branchRepository.findAllPaging(pageable);
        return ConvertBranchDto.convertToBranchDtoPlainList(branches);
    }

    @Override
    public List<BranchDto> findBranches(UUID companyId, Pageable pageable) {
        List<Branch> branches = branchRepository.findBranches(companyId, pageable);
        return ConvertBranchDto.convertToBranchDtoPlainList(branches);
    }

    @Override
    public List<BranchDto> findBranchesWithOffices(UUID companyId, Pageable pageable) {
        List<Branch> branches = branchRepository.findBranchesWithOffices(companyId, pageable);
        return ConvertBranchDto.convertToBranchDtoList(branches);
    }

    @Override
    public Long getTotalCount(UUID companyId) {
        if (companyId != null) {
            return branchRepository.getTotalCount(companyId);
        }
        return branchRepository.findTotalCount();

    }

    @Override
    @Transactional
    public BranchDto save(UUID companyId, BranchDto branchDto) {
        if(branchDto.getId() != null) {
            logger.warn("Branch id should be null");
            throw new BadRequestException("Branch id should be null");
        }
        Branch branch = ConvertBranchDto.convertToBranch(branchDto);
        Company company = companyRepository.findById(companyId);
        if(company != null){
            branch.setCompany(company);
        }
        branchRepository.save(branch);
        return ConvertBranchDto.convertToBranchDtoPlain(branch);
    }

    @Override
    @Transactional
    public BranchDto update(BranchDto branchDto) {

        Branch branch = branchRepository.findById(branchDto.getId());
        if(branch == null) {
            logger.warn("Branch id not found - " + branchDto.getId());
            throw new EntityNotFoundException("Branch id not found - " + branchDto.getId());
        }
        if(branchDto.getName() != null && !(branchDto.getName().isBlank())){
            branch.setName(branchDto.getName());
        }
        branchRepository.update(branch);

        return ConvertBranchDto.convertToBranchDtoPlain(branch);
    }

    @Override
    @Transactional
    public BranchDto deleteById(UUID entityId) {
        Branch tempBranch = branchRepository.findById(entityId);
        if(tempBranch == null) {
            logger.warn("Branch id not found - " + entityId);
            throw new EntityNotFoundException("Branch id not found - " + entityId);
        }
        branchRepository.deleteById(entityId);
        return ConvertBranchDto.convertToBranchDtoPlain(tempBranch);
    }

}