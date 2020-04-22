package com.company.myproject.service;

import com.company.myproject.dao.EmployeeRepository;
import com.company.myproject.dao.OfficeRepository;
import com.company.myproject.dto.EmployeeDto;
import com.company.myproject.exception.EntityNotFoundException;
import com.company.myproject.exception.BadRequestException;
import com.company.myproject.model.*;
import com.company.myproject.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private EmployeeRepository employeeRepository;
    private OfficeEmployeeService officeEmployeeService;
    private OfficeRepository officeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               OfficeEmployeeService officeEmployeeService,
                               OfficeRepository officeRepository) {
        this.employeeRepository = employeeRepository;
        this.officeEmployeeService = officeEmployeeService;
        this.officeRepository = officeRepository;
    }

    @Override
    public EmployeeDto findByIdWithList(UUID id) {
        Employee employeeWithList = employeeRepository.findByIdWithList(id);
        if (employeeWithList == null) {
            logger.debug("Employee id not found - " + id);
            throw new EntityNotFoundException("Employee id not found - " + id);
        }
        return ConvertEmployeeDto.convertToEmployeeDto(employeeWithList);
    }

    @Override
    public List<EmployeeDto> findAll(Pageable pageable) {
        List<Employee> employees;
        if (pageable == null) {
            employees = employeeRepository.findAll();
        } else {
            employees = employeeRepository.findAllPaging(pageable);
        }
        return ConvertEmployeeDto.convertToEmployeeDtoPlainList(employees);
    }

    @Override
    public List<EmployeeDto> findEmployees(UUID companyId, UUID branchId, UUID officeId, Pageable pageable) {
        List<Employee> employees = employeeRepository.findEmployees(companyId, branchId, officeId, pageable);
        return ConvertEmployeeDto.convertToEmployeeDtoPlainList(employees);
    }

    @Override
    public Long getTotalCount(UUID companyId, UUID branchId, UUID officeId) {
        if (companyId == null && branchId == null && officeId == null) {
            return employeeRepository.findTotalCount();
        }
        return employeeRepository.getTotalCount(companyId, branchId, officeId);
    }

    @Override
    @Transactional
    public EmployeeDto save(EmployeeDto employeeDto) {
        if (employeeDto.getId() != null) {
            logger.warn("Employee id should be null");
            throw new BadRequestException("Employee id should be null");
        }
        Employee employee = ConvertEmployeeDto.convertToEmployee(employeeDto);
        employeeRepository.save(employee);

        Set<EmployeeDto.OfficeDto> officeDtos = employeeDto.getOffices();
        Set<OfficeEmployee> officesToSave= new HashSet<>();

        for (EmployeeDto.OfficeDto officeDto : officeDtos) {
            OfficeEmployee officeEmployee = ConvertOfficeEmployeeDto.convertToOfficeEmployee(officeDto);
            officeEmployee.setEmployeeId(employee.getId());
            officesToSave.add(officeEmployee);
        }
        checkDatesForOffice(officesToSave);
        checkNullDateTo(officesToSave);
        officesToSave.stream().forEach(x->officeEmployeeService.save(x));

        return ConvertEmployeeDto.convertToEmployeeDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto update(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findByIdWithList(employeeDto.getId());
        if (employee == null) {
            logger.warn("Employee id not found - " + employeeDto.getId());
            throw new EntityNotFoundException("Employee id not found - " + employeeDto.getId());
        }
        if (employeeDto.getFirstName() != null && !(employeeDto.getFirstName().isBlank())) {
            employee.setFirstName(employeeDto.getFirstName());
        }
        if (employeeDto.getLastName() != null && !(employeeDto.getLastName().isBlank())) {
            employee.setLastName(employeeDto.getLastName());
        }

        if (employeeDto.getOffices() != null) {
            addOrUpdateOffice(employeeDto, employee);
            removeOffice(employeeDto, employee);
        }
        checkDatesForOffice(employee.getOffices());
        checkNullDateTo(employee.getOffices());
        employeeRepository.update(employee);
        return ConvertEmployeeDto.convertToEmployeeDto(employee);
    }

    @Override
    @Transactional
    public void addOrUpdateOffice(EmployeeDto employeeDto, Employee employee) {
        Set<EmployeeDto.OfficeDto> officeDtos = employeeDto.getOffices();
        Set<OfficeEmployee> offices = employee.getOffices();
        Set<OfficeEmployee> toBeAddedOrUpdated = new HashSet<>();

        for (EmployeeDto.OfficeDto officeDto : officeDtos) {
            boolean isNew = !offices.stream().anyMatch(x -> new OfficeEmployeeId(officeDto.getOfficeId(), x.getEmployeeId())
                    .equals(x.getId()));
            OfficeEmployee officeEmployee = null;
            if (isNew) {
                officeEmployee = new OfficeEmployee();
                officeEmployee.setEmployeeId(employee.getId());
                officeEmployee.setOfficeId(officeDto.getOfficeId());
            } else {
                officeEmployee = offices.stream().filter(x -> x.getId()
                        .equals(new OfficeEmployeeId(officeDto.getOfficeId(), x.getEmployeeId()))).findFirst().get();
            }

            officeEmployee.setDateFrom(officeDto.getDateFrom());
            officeEmployee.setDateTo(officeDto.getDateTo());
            toBeAddedOrUpdated.add(officeEmployee);
        }

        if (toBeAddedOrUpdated != null) {
            offices.addAll(toBeAddedOrUpdated);
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
    public void removeOffice(EmployeeDto employeeDto, Employee employee) {
        Set<EmployeeDto.OfficeDto> officeDtos = employeeDto.getOffices();
        Set<OfficeEmployee> offices = employee.getOffices();
        Set<OfficeEmployee> toBeRemoved = new HashSet<>();

        for (OfficeEmployee officeEmployee : offices) {
            if (!officeDtos.stream().anyMatch(x -> new OfficeEmployeeId(x.getOfficeId(), officeEmployee.getEmployeeId())
                    .equals(officeEmployee.getId()))) {
                toBeRemoved.add(officeEmployee);
            }
        }
        if (toBeRemoved != null) {
            offices.removeAll(toBeRemoved);
            toBeRemoved.stream().forEach(x -> officeEmployeeService.deleteById(x.getId()));
        }
    }

    @Override
    @Transactional
    public EmployeeDto deleteById(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employee == null) {
            logger.warn("Employee id not found - " + employeeId);
            throw new EntityNotFoundException("Employee id not found - " + employeeId);
        }
        Set<OfficeEmployee> offices = employee.getOffices();
        offices.stream().forEach(office -> officeEmployeeService.deleteById(office.getId()));

        employeeRepository.deleteById(employeeId);
        return ConvertEmployeeDto.convertToEmployeeDto(employee);
    }

    @Override
    public void checkDatesForOffice(Set<OfficeEmployee> offices) {
        offices.stream().forEach(x -> {
            Office office = officeRepository.findById(x.getOfficeId());
            if (x.getDateFrom() == null) {
                logger.warn("'DateFrom' cannot be null for Office: " + office.getCode());
                throw new BadRequestException("'DateFrom' cannot be null for Office: " + office.getCode());
            }
            if (x.getDateTo() != null && x.getDateFrom().isAfter(x.getDateTo())) {
                logger.warn("'DateTo' should be later than 'DateFrom' for Office: " + office.getCode());
                throw new BadRequestException("'DateTo' should be later than 'DateFrom' for Office: " + office.getCode());
            }
        });
    }

    @Override
    public void checkDatesForEmployee(Set<OfficeEmployee> offices) {
        offices.stream().forEach(x -> {
            Employee employee = employeeRepository.findById(x.getEmployeeId());
            if (x.getDateFrom() == null) {
                logger.warn("'DateFrom' cannot be null for Employee: " + employee.getFirstName() + " " + employee.getLastName());
                throw new BadRequestException("'DateFrom' cannot be null for Employee: " + employee.getFirstName() + " " + employee.getLastName());
            }
            if (x.getDateTo() != null && x.getDateFrom().isAfter(x.getDateTo())) {
                logger.warn("'DateTo' should be later than 'DateFrom' for Employee: " + employee.getFirstName() + " " + employee.getLastName());
                throw new BadRequestException("'DateTo' should be later than 'DateFrom' for Employee: " + employee.getFirstName() + " " + employee.getLastName());
            }
        });
    }

    @Override
    public void checkNullDateTo(Set<OfficeEmployee> offices) {

        for (OfficeEmployee officeEmployee : offices) {
            if (officeEmployee.getDateTo() == null) {

                Office office = officeRepository.findById(officeEmployee.getOfficeId());
                Branch branch = office.getBranch();

                // Check if employee is already assigned to one office per branch
                if (offices.stream().anyMatch(x -> !x.getOfficeId().equals(officeEmployee.getOfficeId()) &&
                        x.getDateTo() == (officeEmployee.getDateTo()) &&
                        branch.equals(officeRepository.findById(x.getOfficeId()).getBranch()))) {
                    logger.warn("DateTo should be null for ONE office only, per branch per employee!");
                    throw new BadRequestException("DateTo should be null for ONE office only, per branch per employee!");
                }
                // Check if another employee is already assigned to office (dateTo=null)
                if (office.getEmployees().stream().anyMatch(x -> !x.getEmployeeId().equals(officeEmployee.getEmployeeId()) &&
                        x.getDateTo() == officeEmployee.getDateTo())) {
                    logger.warn("Another employee is currently assigned to office: " + office.getCode() + " (DateTo is null)");
                    throw new BadRequestException("Another employee is currently assigned to office: " + office.getCode() + " (DateTo is null)");
                }

            }
        }
    }


//    public void checkDateForOfficeList(OfficeEmployee updatedEmployee) {
//        Office office = officeRepository.findByIdWithLists(updatedEmployee.getOfficeId());
//        if (office == null) {
//            logger.warn("Office id not found - " + updatedEmployee.getOfficeId());
//            throw new BadRequestException("Office id not found - " + updatedEmployee.getOfficeId());
//        }
//        Set<OfficeEmployee> employees = office.getEmployees();
//        if (employees != null) {
//            for (OfficeEmployee officeEmployee : employees) {
//                checkDateIfNullForOfficeList(officeEmployee, updatedEmployee);
//                checkDateIfNotNullForOfficeList(officeEmployee, updatedEmployee);
//            }
//        }
//    }
//
//    public void checkDateIfNullForOfficeList(OfficeEmployee officeEmployee, OfficeEmployee updatedEmployee) {
//        if (officeEmployee.getDateTo() != null && updatedEmployee.getDateFrom().before(officeEmployee.getDateTo())) {
//            logger.warn("DateFrom should be after DateTo of existing employee!");
//            throw new BadRequestException("DateFrom should be after DateTo of existing employee!");
//        }
//    }
//
//    public void checkDateIfNotNullForOfficeList(OfficeEmployee officeEmployee, OfficeEmployee updatedEmployee) {
//        if (officeEmployee.getDateTo() == null) {
//            if (updatedEmployee.getDateTo() == null) {
//                logger.warn("Null dateTo is already set for office id - " + updatedEmployee.getOfficeId());
//                throw new BadRequestException("Null dateTo is already set for office id - " + updatedEmployee.getOfficeId());
//            } else if (!updatedEmployee.getEmployeeId().equals(officeEmployee.getEmployeeId())) {
//                logger.warn("Employee cannot be assigned to Office with id - " +
//                        updatedEmployee.getOfficeId() + " - Another employee is currently assigned to the office.");
//                throw new BadRequestException("Employee cannot be assigned to Office with id - " +
//                        updatedEmployee.getOfficeId() + " - Another employee is currently assigned to the office.");
//            }
//        }
//    }
//
//
//    public void checkDateForEmployeeList(OfficeEmployee updatedOffice) {
//        EmployeeDto employeeDto = findByIdWithList(updatedOffice.getEmployeeId());
//        Set<EmployeeDto.OfficeDto> offices = employeeDto.getOffices();
//        if (offices != null) {
//            for (EmployeeDto.OfficeDto officeEmployee : offices) {
//                checkDateIfNullForEmployeeList(officeEmployee, updatedOffice);
//                checkDateIfNotNullForEmployeeList(officeEmployee, updatedOffice);
//            }
//        }
//    }
//
//    public void checkDateIfNullForEmployeeList(EmployeeDto.OfficeDto officeEmployee, OfficeEmployee updatedOffice) {
//        if (officeEmployee.getDateTo() != null && updatedOffice.getDateFrom().before(officeEmployee.getDateTo())) {
//            logger.warn("DateFrom should be after DateTo of other offices that employee has already been assigned to!");
//            throw new BadRequestException("DateFrom should be after DateTo of all the other offices that employee has already been assigned to!");
//        }
//    }
//
//    public void checkDateIfNotNullForEmployeeList(EmployeeDto.OfficeDto officeEmployee, OfficeEmployee updatedOffice) {
//        if (officeEmployee.getDateTo() == null) {
//            if (updatedOffice.getDateTo() == null) {
//                logger.warn("Null dateTo is already set for employee id - " + updatedOffice.getEmployeeId());
//                throw new BadRequestException("Null dateTo is already set for employee id - " + updatedOffice.getEmployeeId());
//            } else if (!updatedOffice.getOfficeId().equals(officeEmployee.getOfficeId())) {
//                logger.warn("Employee cannot be assigned to Office with id - " +
//                        updatedOffice.getOfficeId() + " - Employee is currently assigned to another office.");
//                throw new BadRequestException("Employee cannot be assigned to Office with id - " +
//                        updatedOffice.getOfficeId() + " - Employee is currently assigned to another office.");
//            }
//        }
//    }

}
