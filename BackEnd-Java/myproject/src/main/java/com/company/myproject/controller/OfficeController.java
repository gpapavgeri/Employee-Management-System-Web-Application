package com.company.myproject.controller;

import com.company.myproject.dao.OfficeRepository;
import com.company.myproject.dto.OfficeDto;
import com.company.myproject.model.Pageable;
import com.company.myproject.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    private OfficeService officeService;
    private OfficeRepository officeRepository;

    @Autowired
    public OfficeController(OfficeService officeService, OfficeRepository officeRepository){

        this.officeService = officeService;
        this.officeRepository = officeRepository;
    }

    // Load one office
    @CrossOrigin
    @GetMapping("/{officeId}")
    public OfficeDto getOffice(@PathVariable UUID officeId) {
        return officeService.findByIdWithLists(officeId);
    }

    // Load all offices or offices for branch
    @CrossOrigin
    @GetMapping
    public List<OfficeDto> getOffices(@RequestParam(required = false) UUID companyId,
                                      @RequestParam(required = false) UUID branchId,
                                      @RequestParam(required = false, defaultValue = "1") int pageNo,
                                      @RequestParam(required = false, defaultValue = "10") int pageSize,
                                      @RequestParam(required = false, defaultValue = "id") String sortBy){
        Pageable pageable = new Pageable(pageNo, pageSize, sortBy);
        if(companyId == null && branchId == null){
            return officeService.findAll(pageable);
        } else {
            return officeService.findOffices(companyId, branchId, pageable);
        }
    }

    // Get Total Count
    @CrossOrigin
    @GetMapping("/count")
    public Long getTotalCount(@RequestParam(required = false) UUID companyId,
                              @RequestParam(required = false) UUID branchId){
        return officeService.getTotalCount(companyId, branchId);
    }

    // Create a new office
    @CrossOrigin
    @PostMapping
    public OfficeDto createOffice(@RequestParam UUID branchId, @RequestBody OfficeDto officeDto){
        return officeService.save(branchId, officeDto);
    }

    // Update an office
    @CrossOrigin
    @PutMapping
    public OfficeDto updateOffice(@RequestBody OfficeDto officeDto){
        return officeService.update(officeDto);
    }

    // Delete an office
    @CrossOrigin
    @DeleteMapping("/{officeId}")
    public String deleteOffice(@PathVariable UUID officeId){
        officeService.deleteById(officeId);
        return "Deleted office id - " + officeId;
    }


}