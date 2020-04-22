package com.company.myproject.service;

import com.company.myproject.model.AssetOffice;
import com.company.myproject.model.AssetOfficeId;

import java.util.Set;

public interface AssetOfficeService {

    AssetOffice findByCompositeId(AssetOffice assetOffice);

    Set<AssetOffice> findAll();

    void save(AssetOffice assetOffice);

    void update(AssetOffice assetOffice);

    void deleteById(AssetOfficeId id);

}
