package com.company.myproject.service;

import com.company.myproject.dao.EmployeeRepository;
import com.company.myproject.dao.OfficeEmployeeRepository;
import com.company.myproject.dao.OfficeRepository;
import com.company.myproject.exception.EntityNotFoundException;
import com.company.myproject.exception.BadRequestException;
import com.company.myproject.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class OfficeEmployeeServiceImpl implements OfficeEmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(OfficeEmployeeService.class);

    private OfficeEmployeeRepository officeEmployeeRepository;
    private OfficeRepository officeRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public OfficeEmployeeServiceImpl(OfficeEmployeeRepository officeEmployeeRepository, OfficeRepository officeRepository, EmployeeRepository employeeRepository) {
        this.officeEmployeeRepository = officeEmployeeRepository;
        this.officeRepository = officeRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public OfficeEmployee findByCompositeId(OfficeEmployee officeEmployee) {
        return officeEmployeeRepository.findByCompositeId(officeEmployee);
    }

    @Override
    public Set<OfficeEmployee> findOfficeEmployees(Instant dateTo) {
        List<OfficeEmployee> list = officeEmployeeRepository.findOfficeEmployees(dateTo);
        return list.stream().collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void save(OfficeEmployee officeEmployee) {
        if (officeEmployee.getEmployeeId() == null || officeEmployee.getOfficeId() == null) {
            logger.warn("EmployeeId and OfficeId are required to save the OfficeEmployee record!");
            throw new BadRequestException("EmployeeId and OfficeId are required to save the OfficeEmployee record!");
        } else {
            OfficeEmployeeId id = new OfficeEmployeeId(officeEmployee.getOfficeId(), officeEmployee.getEmployeeId());
            officeEmployee.setId(id);
            officeEmployeeRepository.save(officeEmployee);
        }
    }

    @Override
    @Transactional
    public void update(OfficeEmployee officeEmployee) {
        OfficeEmployee existedRecord = officeEmployeeRepository.findByCompositeId(officeEmployee);
        if (existedRecord == null) {
            logger.warn("OfficeEmployee record not found!");
            throw new BadRequestException("OfficeEmployee record not found!");
        }
        existedRecord.setDateFrom(officeEmployee.getDateFrom());
        existedRecord.setDateTo(officeEmployee.getDateTo());
        officeEmployeeRepository.update(existedRecord);
    }

    @Override
    @Transactional
    public void deleteById(OfficeEmployeeId id) {
        OfficeEmployee officeEmployee = officeEmployeeRepository.findById(id);
        if (officeEmployee == null) {
            logger.warn("OfficeEmployee id not found - " + id);
            throw new EntityNotFoundException("OfficeEmployee id not found - " + id);
        }
        officeEmployeeRepository.deleteById(id);
    }

    @Override
    public Set<OfficeEmployee> findOfficeEmployeesByEmployeeId(UUID employeeId) {
        List<OfficeEmployee> list = officeEmployeeRepository.findOfficeEmployeesByEmployeeId(employeeId);
        return list.stream().collect(Collectors.toSet());
    }

}
