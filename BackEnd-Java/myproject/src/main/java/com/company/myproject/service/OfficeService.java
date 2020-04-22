package com.company.myproject.service;

import com.company.myproject.dto.OfficeDto;
import com.company.myproject.model.Office;
import com.company.myproject.model.Pageable;

import java.util.List;
import java.util.UUID;

public interface OfficeService {

    OfficeDto findByIdWithLists(UUID id);

    List<OfficeDto> findAll(Pageable pageable);

    List<OfficeDto> findOffices(UUID companyId, UUID branchId, Pageable pageable);

    Long getTotalCount(UUID companyId, UUID branchId);

    OfficeDto save(UUID branchId, OfficeDto officeDto);

    OfficeDto update(OfficeDto officeDto);

    void addOrUpdateAsset(OfficeDto officeDto, Office office);

    void removeAsset(OfficeDto officeDto, Office office);

    void addOrUpdateEmployee(OfficeDto officeDto, Office office);

    void removeEmployee(OfficeDto officeDto, Office office);

    void deleteById(UUID id);



}
