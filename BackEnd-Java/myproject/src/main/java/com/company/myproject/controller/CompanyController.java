package com.company.myproject.controller;

import com.company.myproject.dto.CompanyDto;
import com.company.myproject.dto.EmployeeDto;
import com.company.myproject.model.Pageable;
import com.company.myproject.service.BranchService;
import com.company.myproject.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;
    private BranchService branchService;

    @Autowired
    public CompanyController(CompanyService companyService, BranchService branchService){

        this.companyService = companyService;
        this.branchService = branchService;
    }

    // Load one company
    @CrossOrigin
    @GetMapping("/{companyId}")
    public CompanyDto getCompany(@PathVariable UUID companyId) {
        return companyService.findById(companyId);
    }

    // Load all companies
    @CrossOrigin
    @GetMapping
    public List<CompanyDto> getCompanies(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                         @RequestParam(required = false, defaultValue = "id") String sortBy) {
        Pageable pageable = new Pageable(pageNo, pageSize, sortBy);
        return companyService.findAll(pageable);
    }

    // Load company for office
    @CrossOrigin
    @GetMapping("/office")
    public CompanyDto getCompanyForOffice(@RequestParam UUID officeId) {
        return companyService.findCompanyForOffice(officeId);
    }

    // Load company for employee
    @CrossOrigin
    @GetMapping("/employee")
    public CompanyDto getCompanyForEmployee(@RequestParam UUID employeeId) {
        return companyService.findCompanyForEmployee(employeeId);
    }

    // Get Total Count
    @CrossOrigin
    @GetMapping("/count")
    public Long getTotalCount(){
        return companyService.getTotalCount();
    }


    // Create a new company
    @CrossOrigin
    @PostMapping
    public CompanyDto createCompany(@RequestBody CompanyDto theCompany){
        return companyService.save(theCompany);
    }

    // Update a company
    @CrossOrigin
    @PutMapping
    public CompanyDto updateCompany(@RequestBody CompanyDto theCompany){
        return companyService.update(theCompany);
    }

    // Delete a company
    @CrossOrigin
    @DeleteMapping("/{companyId}")
    public String deleteCompany(@PathVariable UUID companyId){
        return companyService.deleteById(companyId);
    }

}