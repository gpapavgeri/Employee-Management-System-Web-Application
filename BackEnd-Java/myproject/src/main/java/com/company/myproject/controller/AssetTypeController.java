package com.company.myproject.controller;

import com.company.myproject.dto.AssetTypeDto;
import com.company.myproject.model.Pageable;
import com.company.myproject.service.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assetTypes")
public class AssetTypeController {

    private AssetTypeService assetTypeService;

    @Autowired
    public AssetTypeController(AssetTypeService assetTypeService){
        this.assetTypeService = assetTypeService;
    }

    // Load one Asset Type
    @CrossOrigin
    @GetMapping("/{assetTypeId}")
    public AssetTypeDto getAssetType(@PathVariable UUID assetTypeId) {
        return assetTypeService.findById(assetTypeId);
    }

    // Load all Asset Types
    @CrossOrigin
    @GetMapping
    public List<AssetTypeDto> getAssetTypes(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                            @RequestParam(required = false, defaultValue = "10") int pageSize,
                                            @RequestParam(required = false, defaultValue = "id") String sortBy){
        Pageable pageable = new Pageable(pageNo, pageSize, sortBy);
        return assetTypeService.findAll(pageable);
    }

    // Get Total Count
    @CrossOrigin
    @GetMapping("/count")
    public Long getTotalCount(@RequestParam(required = false, defaultValue = "1") int pageNo,
                              @RequestParam(required = false, defaultValue = "10") int pageSize,
                              @RequestParam(required = false, defaultValue = "id") String sortBy){
        Pageable pageable = new Pageable(pageNo, pageSize, sortBy);
        return assetTypeService.getTotalCount();
    }

    // Create a new Asset Type
    @PostMapping
    @CrossOrigin
    public AssetTypeDto createAssetType(@RequestBody AssetTypeDto assetTypeDto){
        return assetTypeService.save(assetTypeDto);
    }

    // Update an Asset Type
    @CrossOrigin
    @PutMapping
    public AssetTypeDto AssetTypeDtoPlain(@RequestBody AssetTypeDto assetTypeDto){
        return assetTypeService.update(assetTypeDto);
    }

    // Delete a company
    @CrossOrigin
    @DeleteMapping("/{assetTypeId}")
    public AssetTypeDto deleteAssetType(@PathVariable UUID assetTypeId){
        return assetTypeService.deleteById(assetTypeId);
    }

}