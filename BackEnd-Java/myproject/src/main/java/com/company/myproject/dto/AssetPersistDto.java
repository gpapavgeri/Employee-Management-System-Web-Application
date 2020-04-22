package com.company.myproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AssetPersistDto {

    private UUID id;
    private String serialNumber;
    private String brand;
    private UUID companyId;
    private AssetTypeDto assetType;

    public AssetPersistDto() {
    }

    public AssetPersistDto(String serialNumber, String brand) {
        this.serialNumber = serialNumber;
        this.brand = brand;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public AssetTypeDto getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetTypeDto assetType) {
        this.assetType = assetType;
    }
}