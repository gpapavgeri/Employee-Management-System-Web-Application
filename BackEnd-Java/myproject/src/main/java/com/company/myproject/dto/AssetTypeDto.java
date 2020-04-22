package com.company.myproject.dto;

import java.util.List;
import java.util.UUID;

public class AssetTypeDto {

    private UUID id;
    private String type;
    private List<AssetDto> assets;

    public AssetTypeDto() {
    }

    public AssetTypeDto(String type) {
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AssetDto> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetDto> assets) {
        this.assets = assets;
    }
}
