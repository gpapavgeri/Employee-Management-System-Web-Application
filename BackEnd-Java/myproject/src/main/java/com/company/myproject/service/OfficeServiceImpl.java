package com.company.myproject.service;

import com.company.myproject.dao.BranchRepository;
import com.company.myproject.dao.EmployeeRepository;
import com.company.myproject.dao.OfficeRepository;
import com.company.myproject.dto.OfficeDto;
import com.company.myproject.exception.EntityNotFoundException;
import com.company.myproject.exception.BadRequestException;
import com.company.myproject.model.*;
import com.company.myproject.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class OfficeServiceImpl implements OfficeService {

    private static final Logger logger = LoggerFactory.getLogger(OfficeService.class);

    private OfficeRepository officeRepository;
    private BranchRepository branchRepository;
    private EmployeeService employeeService;
    private EmployeeRepository employeeRepository;
    private AssetService assetService;
    private OfficeEmployeeService officeEmployeeService;
    private AssetOfficeService assetOfficeService;

    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository, BranchRepository branchRepository,
                             EmployeeService employeeService, EmployeeRepository employeeRepository, AssetService assetService,
                             OfficeEmployeeService officeEmployeeService, AssetOfficeService assetOfficeService) {
        this.officeRepository = officeRepository;
        this.branchRepository = branchRepository;
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.assetService = assetService;
        this.officeEmployeeService = officeEmployeeService;
        this.assetOfficeService = assetOfficeService;
    }

    @Override
    public OfficeDto findByIdWithLists(UUID id) {
        Office office = officeRepository.findByIdWithLists(id);
        if (office == null) {
            logger.warn("Office id not found - " + id);
            throw new BadRequestException("Office id not found - " + id);
        }
        return ConvertOfficeDto.convertToOfficeDto(office);
    }

    @Override
    public List<OfficeDto> findAll(Pageable pageable) {
        List<Office> offices = officeRepository.findAllPaging(pageable);
        return ConvertOfficeDto.convertToOfficeDtoPlainList(offices);
    }

    @Override
    public List<OfficeDto> findOffices(UUID companyId, UUID branchId, Pageable pageable) {
        List<Office> offices = officeRepository.findOffices(companyId, branchId, pageable);
        return ConvertOfficeDto.convertToOfficeDtoPlainList(offices);
    }

    @Override
    public Long getTotalCount(UUID companyId, UUID branchId) {
        if (companyId == null && branchId == null) {
            return officeRepository.findTotalCount();
        }
        return officeRepository.getTotalCount(companyId, branchId);
    }

    @Override
    @Transactional
    public OfficeDto save(UUID branchId, OfficeDto officeDto) {
        if (officeDto.getId() != null) {
            logger.warn("Office id should be null");
            throw new BadRequestException("Office id should be null");
        }
        Office office = ConvertOfficeDto.convertToOffice(officeDto);
        officeRepository.save(office);

        Set<AssetOffice> assetsToSave = new HashSet<>();
        if(officeDto.getAssets() != null){
            Set<OfficeDto.AssetDto> assetDtos = officeDto.getAssets();
            for(OfficeDto.AssetDto assetDto: assetDtos){
                AssetOffice assetOffice = ConvertAssetOfficeDto.convertToAssetOffice(assetDto);
                assetOffice.setOfficeId(office.getId());
                assetsToSave.add(assetOffice);
            }
        }
        assetService.checkDatesForAsset(assetsToSave);
        assetsToSave.stream().forEach(x -> assetOfficeService.save(x));

        Set<OfficeEmployee> employeesToSave= new HashSet<>();
        if(officeDto.getEmployees() != null){
            Set<OfficeDto.EmployeeDto> employeeDtos = officeDto.getEmployees();
            for(OfficeDto.EmployeeDto employeeDto: employeeDtos){
                OfficeEmployee officeEmployee = ConvertOfficeEmployeeDto.convertToOfficeEmployee(employeeDto);
                officeEmployee.setOfficeId(office.getId());
                employeesToSave.add(officeEmployee);
            }
        }
        employeeService.checkDatesForEmployee(employeesToSave);
        employeeService.checkNullDateTo(employeesToSave);
        employeesToSave.stream().forEach(x->officeEmployeeService.save(x));

        return ConvertOfficeDto.convertToOfficeDtoPlain(office);
    }

    @Override
    @Transactional
    public OfficeDto update(OfficeDto officeDto) {
        Office office = officeRepository.findByIdWithLists(officeDto.getId());
        if (office == null) {
            logger.warn("Office id not found - " + officeDto.getId());
            throw new EntityNotFoundException("Office id not found - " + officeDto.getId());
        }
        if (officeDto.getCode() != null) {
            office.setCode(officeDto.getCode());
        }
        if (officeDto.getAssets() != null) {
            addOrUpdateAsset(officeDto, office);
            removeAsset(officeDto, office);
        }
        if (officeDto.getEmployees() != null) {
            addOrUpdateEmployee(officeDto, office);
            removeEmployee(officeDto, office);
        }

        employeeService.checkDatesForEmployee(office.getEmployees());
        employeeService.checkNullDateTo(office.getEmployees());
        assetService.checkDatesForAsset(office.getAssets());
        officeRepository.update(office);
        return ConvertOfficeDto.convertToOfficeDto(office);
    }

    @Override
    @Transactional
    public void addOrUpdateAsset(OfficeDto officeDto, Office office) {
        Set<OfficeDto.AssetDto> assetDtos = officeDto.getAssets();
        Set<AssetOffice> assets = office.getAssets();
        Set<AssetOffice> toBeAddedOrUpdated = new HashSet<>();

        for (OfficeDto.AssetDto assetDto : assetDtos) {
            boolean isNew = !assets.stream().anyMatch(x -> new AssetOfficeId(assetDto.getAssetId(), x.getOfficeId())
                    .equals(x.getId()));
            AssetOffice assetOffice = null;
            if (isNew) {
                assetOffice = new AssetOffice();
                assetOffice.setAssetId(assetDto.getAssetId());
                assetOffice.setOfficeId(office.getId());
            } else {
                assetOffice = assets.stream().filter(x -> x.getId()
                        .equals(new AssetOfficeId(assetDto.getAssetId(), x.getOfficeId()))).findFirst().get();
            }
            assetOffice.setDateFrom(assetDto.getDateFrom());
            assetOffice.setDateTo(assetDto.getDateTo());
            toBeAddedOrUpdated.add(assetOffice);
        }

        if (toBeAddedOrUpdated != null) {
            assets.addAll(toBeAddedOrUpdated);
            toBeAddedOrUpdated.stream().forEach(x -> {
                if (assetOfficeService.findByCompositeId(x) == null) {
                    assetOfficeService.save(x);
                } else {
                    assetOfficeService.update(x);
                }
            });
        }
    }

    @Override
    @Transactional
    public void removeAsset(OfficeDto officeDto, Office office) {
        Set<OfficeDto.AssetDto> assetDtos = officeDto.getAssets();
        Set<AssetOffice> assets = office.getAssets();
        Set<AssetOffice> toBeRemoved = new HashSet<>();

        for (AssetOffice assetOffice : assets) {
            OfficeDto.AssetDto assetDto = ConvertAssetOfficeDto.convertToAssetDto(assetOffice);
            if (!assetDtos.contains(assetDto)) {
//            if (!officeDtos.stream().anyMatch(x -> new AssetOfficeId(assetOffice.getAssetId(), x.getOfficeId())
//                    .equals(assetOffice.getId()))) {
                toBeRemoved.add(assetOffice);
            }
        }

        if (toBeRemoved != null) {
            assets.removeAll(toBeRemoved);
            toBeRemoved.stream().forEach(x -> assetOfficeService.deleteById(x.getId()));
        }
    }

    @Override
    @Transactional
    public void addOrUpdateEmployee(OfficeDto officeDto, Office office) {
        Set<OfficeDto.EmployeeDto> employeeDtos = officeDto.getEmployees();
        Set<OfficeEmployee> employees = office.getEmployees();
        Set<OfficeEmployee> toBeAddedOrUpdated = new HashSet<>();

        for (OfficeDto.EmployeeDto employeeDto : employeeDtos) {
            boolean isNew = !employees.stream().anyMatch(x -> new OfficeEmployeeId(x.getOfficeId(), employeeDto.getEmployeeId())
                    .equals(x.getId()));
            OfficeEmployee officeEmployee = null;

            if(isNew) {
                officeEmployee = new OfficeEmployee();
                officeEmployee.setEmployeeId(employeeDto.getEmployeeId());
                officeEmployee.setOfficeId(office.getId());
            } else {
                officeEmployee = employees.stream().filter(x -> x.getId()
                        .equals(new OfficeEmployeeId(x.getOfficeId(), employeeDto.getEmployeeId()))).findFirst().get();
            }

            officeEmployee.setDateFrom(employeeDto.getDateFrom());
            officeEmployee.setDateTo(employeeDto.getDateTo());
            toBeAddedOrUpdated.add(officeEmployee);
        }

        if (toBeAddedOrUpdated!=null) {
            employees.addAll(toBeAddedOrUpdated);
            toBeAddedOrUpdated.stream().forEach(x -> {
                if (officeEmployeeService.findByCompositeId(x) == null) {
                    officeEmployeeService.save(x);
                } else {
                    officeEmployeeService.update(x);
                }
            });
        }

    }

    @Override
    @Transactional
    public void removeEmployee(OfficeDto officeDto, Office office) {
        Set<OfficeDto.EmployeeDto> employeeDtos = officeDto.getEmployees();
        Set<OfficeEmployee> employees = office.getEmployees();
        Set<OfficeEmployee> toBeRemoved = new HashSet<>();

        System.out.println("Employee Dtos: " + employeeDtos);
        for (OfficeEmployee officeEmployee : employees) {
            if (!employeeDtos.stream().anyMatch(x -> new OfficeEmployeeId(officeEmployee.getOfficeId(), x.getEmployeeId())
                    .equals(officeEmployee.getId()))) {
                toBeRemoved.add(officeEmployee);
            }
        }
        if (toBeRemoved!=null){
            employees.removeAll(toBeRemoved);
            toBeRemoved.stream().forEach(x -> officeEmployeeService.deleteById(x.getId()));
        }
    }

    @Override
    @Transactional
    public void deleteById(UUID entityId) {
        Office tempOffice = officeRepository.findById(entityId);
        if (tempOffice == null) {
            logger.warn("Office id not found - " + entityId);
            throw new EntityNotFoundException("Office id not found - " + entityId);
        }
        officeRepository.deleteById(entityId);
    }


}
