package com.company.myproject.service;

import com.company.myproject.dto.AssetTypeDto;
import com.company.myproject.model.Pageable;

import java.util.List;
import java.util.UUID;

public interface AssetTypeService {

    AssetTypeDto findById(UUID assetTypeId);

    List<AssetTypeDto> findAll(Pageable pageable);

    Long getTotalCount();

    AssetTypeDto save(AssetTypeDto assetTypeDto);

    AssetTypeDto update(AssetTypeDto assetTypeDto);

    AssetTypeDto deleteById(UUID assetTypeId);


}
