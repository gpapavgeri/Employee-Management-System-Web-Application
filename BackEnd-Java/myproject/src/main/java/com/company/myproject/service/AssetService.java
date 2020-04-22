package com.company.myproject.service;

import com.company.myproject.dto.AssetDto;
import com.company.myproject.model.Asset;
import com.company.myproject.model.AssetOffice;
import com.company.myproject.model.Pageable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AssetService {

    AssetDto findById(UUID assetId);

    List<AssetDto> findAll(Pageable pageable);

    List<AssetDto> findAssets(UUID companyId, UUID officeId, UUID assetTypeId, Pageable pageable);

    Long getTotalCount (UUID companyId, UUID officeId, UUID assetTypeId);

    AssetDto save(AssetDto assetDto);

    AssetDto update(AssetDto assetDto);

    void addOrUpdateOffice(AssetDto assetDto, Asset asset);

    void removeOffice(AssetDto assetDto, Asset asset);

    void findOrCreateAssetType(AssetDto assetDto, Asset asset);

    AssetDto deleteById(UUID assetId);

    void checkDatesForOffice(Set<AssetOffice> offices);

    void checkDatesForAsset(Set<AssetOffice> offices);

}
