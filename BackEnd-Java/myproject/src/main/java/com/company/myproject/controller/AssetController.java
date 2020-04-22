package com.company.myproject.controller;

import com.company.myproject.dto.AssetDto;
import com.company.myproject.dto.AssetPersistDto;
import com.company.myproject.model.Pageable;
import com.company.myproject.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    // Load one asset
    @CrossOrigin
    @GetMapping("/{assetId}")
    public AssetDto getAsset(@PathVariable UUID assetId) {
        return assetService.findById(assetId);
    }

    // Load assets
    @CrossOrigin
    @GetMapping
    public List<AssetDto> getAssets(@RequestParam(required = false) UUID companyId,
                                    @RequestParam(required = false) UUID officeId,
                                    @RequestParam(required = false) UUID assetTypeId,
                                    @RequestParam(required = false, defaultValue = "1") int pageNo,
                                    @RequestParam(required = false, defaultValue = "10") int pageSize,
                                    @RequestParam(required = false, defaultValue = "id") String sortBy) {
        Pageable pageable = new Pageable(pageNo, pageSize, sortBy);
        if (companyId == null && officeId == null && assetTypeId == null) {
            return assetService.findAll(pageable);
        }
        return assetService.findAssets(companyId, officeId, assetTypeId, pageable);
    }

    // Get Total Count
    @CrossOrigin
    @GetMapping("/count")
    public Long getTotalCount(@RequestParam(required = false) UUID companyId,
                              @RequestParam(required = false) UUID officeId,
                              @RequestParam(required = false) UUID assetTypeId) {
        return assetService.getTotalCount(companyId, officeId, assetTypeId);
    }

    // Create a new asset
    @CrossOrigin
    @PostMapping
    public AssetDto createAsset(@RequestBody AssetDto assetDto) {
        return assetService.save(assetDto);
    }

    // Update an asset
    @CrossOrigin
    @PutMapping
    public AssetDto updateAsset(@RequestBody AssetDto assetDto) {
        return assetService.update(assetDto);
    }

    // Delete an asset
    @CrossOrigin
    @DeleteMapping("/{assetId}")
    public AssetDto deleteAsset(@PathVariable UUID assetId) {
        return assetService.deleteById(assetId);
    }


}