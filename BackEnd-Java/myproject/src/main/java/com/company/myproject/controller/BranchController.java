package com.company.myproject.controller;

import com.company.myproject.dto.BranchDto;
import com.company.myproject.model.Pageable;
import com.company.myproject.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/branches")
public class BranchController {

    private BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    // Load one branch
    @CrossOrigin
    @GetMapping("/{branchId}")
    public BranchDto getBranch(@PathVariable UUID branchId) {
        return branchService.findById(branchId);
    }

    // Load one branch with offices
    @CrossOrigin
    @GetMapping("/offices/{branchId}")
    public BranchDto getBranchWithOffices(@PathVariable UUID branchId) {
        return branchService.findByIdWithList(branchId);
    }

    // Load all branches or branches for a company
    @CrossOrigin
    @GetMapping
    public List<BranchDto> getBranches(@RequestParam(required = false) UUID companyId,
                                       @RequestParam(required = false, defaultValue = "1") int pageNo,
                                       @RequestParam(required = false, defaultValue = "10") int pageSize,
                                       @RequestParam(required = false, defaultValue = "id") String sortBy) {
        Pageable pageable = new Pageable(pageNo, pageSize, sortBy);
        if (companyId == null) {
            return branchService.findAll(pageable);
        } else {
            return branchService.findBranches(companyId, pageable);
        }
    }

    // Get branches with offices
    @CrossOrigin
    @GetMapping("/offices")
    public List<BranchDto> getBranchesWithOffices(@RequestParam (required = false) UUID companyId,
                                                  @RequestParam(required = false, defaultValue = "1") int pageNo,
                                                  @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                  @RequestParam(required = false, defaultValue = "id") String sortBy) {
        Pageable pageable = new Pageable(pageNo, pageSize, sortBy);
        return branchService.findBranchesWithOffices(companyId, pageable);
    }

    // Get Total Count
    @CrossOrigin
    @GetMapping("/count")
    public Long getTotalCount(@RequestParam(required = false) UUID companyId) {
        return branchService.getTotalCount(companyId);
    }

    // Create a new branch
    @CrossOrigin
    @PostMapping
    public BranchDto createBranch(@RequestParam UUID companyId, @RequestBody BranchDto branchDto) {
        return branchService.save(companyId, branchDto);
    }

    // Update a branch
    @CrossOrigin
    @PutMapping
    public BranchDto updateBranch(@RequestBody BranchDto branchDto) {
        return branchService.update(branchDto);
    }

    // Delete a branch
    @CrossOrigin
    @DeleteMapping("/{branchId}")
    public BranchDto deleteBranch(@PathVariable UUID branchId) {
        return branchService.deleteById(branchId);
    }

}