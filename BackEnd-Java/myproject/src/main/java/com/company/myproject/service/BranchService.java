package com.company.myproject.service;

import com.company.myproject.dto.BranchDto;
import com.company.myproject.model.Pageable;

import java.util.List;
import java.util.UUID;

public interface BranchService {

    BranchDto findById(UUID id);

    BranchDto findByIdWithList(UUID id);

    List<BranchDto> findAll(Pageable pageable);

    List<BranchDto> findBranches(UUID companyId, Pageable pageable);

    List<BranchDto> findBranchesWithOffices(UUID companyId, Pageable pageable);

    Long getTotalCount(UUID companyId);

    BranchDto save(UUID companyId, BranchDto branchDto);

    BranchDto update(BranchDto branchDto);

    BranchDto deleteById(UUID entityId);

}
