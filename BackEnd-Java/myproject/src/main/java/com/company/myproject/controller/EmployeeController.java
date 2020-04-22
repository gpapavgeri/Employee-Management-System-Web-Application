package com.company.myproject.controller;

import com.company.myproject.dto.EmployeeDto;
import com.company.myproject.model.OfficeEmployee;
import com.company.myproject.model.Pageable;
import com.company.myproject.service.EmployeeService;
import com.company.myproject.service.OfficeEmployeeService;
import com.company.myproject.util.ConvertOfficeEmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;
    private OfficeEmployeeService officeEmployeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, OfficeEmployeeService officeEmployeeService) {
        this.employeeService = employeeService;
        this.officeEmployeeService = officeEmployeeService;
    }

    // Load one employee
    @CrossOrigin
    @GetMapping("/{employeeId}")
    public EmployeeDto getEmployee(@PathVariable UUID employeeId) throws JsonProcessingException {
        EmployeeDto employeeDto = employeeService.findByIdWithList(employeeId);

        System.out.println(objectMapper.writeValueAsString(employeeDto));
        return employeeDto;
    }

    // Load all employees
    @CrossOrigin
    @GetMapping
    public List<EmployeeDto> getEmployees(@RequestParam(required = false) UUID companyId,
                                          @RequestParam(required = false) UUID branchId,
                                          @RequestParam(required = false) UUID officeId,
                                          @RequestParam(required = false, defaultValue = "1") int pageNo,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                                          @RequestParam(required = false, defaultValue = "id") String sortBy) {
        Pageable pageable = new Pageable(pageNo, pageSize, sortBy);
        if(companyId == null && branchId == null && officeId == null){
            return employeeService.findAll(pageable);
        }
        return employeeService.findEmployees(companyId, branchId, officeId, pageable);
    }

    // Get Total Count
    @CrossOrigin
    @GetMapping("/count")
    public Long getTotalCount(@RequestParam(required = false) UUID companyId,
                              @RequestParam(required = false) UUID branchId,
                              @RequestParam(required = false) UUID officeId) {
        return employeeService.getTotalCount(companyId, branchId, officeId);
    }

    // Create a new employee
    @CrossOrigin
    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.save(employeeDto);
    }

    // Update an Employee
    @CrossOrigin
    @PutMapping
    public EmployeeDto updateEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.update(employeeDto);
    }

    // Delete an Employee
    @CrossOrigin
    @DeleteMapping("/{employeeId}")
    public EmployeeDto deleteEmployee(@PathVariable UUID employeeId) {
        return employeeService.deleteById(employeeId);
    }

    // Find offices for employee
    @CrossOrigin
    @GetMapping("/offices")
    public Set<EmployeeDto.OfficeDto> getOfficesForEmployee(@RequestParam UUID employeeId) {
        Set<OfficeEmployee> offices = officeEmployeeService.findOfficeEmployeesByEmployeeId(employeeId);
        return ConvertOfficeEmployeeDto.convertToOfficeDtoList(offices);
    }

    // Find officeEmployees
    @CrossOrigin
    @GetMapping("/date")
    public Set<EmployeeDto.OfficeDto> getOfficeEmployees(@RequestParam(required = false) Instant dateTo) {
        Set<OfficeEmployee> officeEmployees = officeEmployeeService.findOfficeEmployees(dateTo);
        return ConvertOfficeEmployeeDto.convertToOfficeDtoList(officeEmployees);
    }

}
