package com.company.myproject.util;

import com.company.myproject.dto.AssetDto;
import com.company.myproject.dto.AssetTypeDto;
import com.company.myproject.model.Asset;
import com.company.myproject.model.AssetType;

import java.util.List;
import java.util.stream.Collectors;


public class ConvertAssetTypeDto {

    public static AssetTypeDto convertToAssetTypeDto(AssetType assetType) {
        AssetTypeDto assetTypeDto = new AssetTypeDto();

        if(assetType.getId() != null){
            assetTypeDto.setId(assetType.getId());
        }
        assetTypeDto.setType(assetType.getType());
        List<Asset> assets = assetType.getAssets();
        List<AssetDto> assetDtos = ConvertAssetDto.convertToAssetDtoPlainList(assets);
        assetTypeDto.setAssets(assetDtos);

        return assetTypeDto;
    }

    public static AssetTypeDto convertToAssetTypeDtoPlain(AssetType assetType) {
        AssetTypeDto assetTypeDto = new AssetTypeDto();

        assetTypeDto.setId(assetType.getId());
        assetTypeDto.setType(assetType.getType());

        return assetTypeDto;
    }

    public static AssetType convertToAssetType(AssetTypeDto assetTypeDto) {
        AssetType assetType = new AssetType();

        if(assetTypeDto.getId() != null) {
            assetType.setId(assetTypeDto.getId());
        }
        assetType.setType(assetTypeDto.getType());

        return assetType;
    }

    public static List<AssetTypeDto> convertToAssetTypeDtoList(List<AssetType> assetTypes){
        if (assetTypes != null){
            return assetTypes.stream().map(ConvertAssetTypeDto::convertToAssetTypeDto).collect(Collectors.toList());
        }
        return null;
    }

    public static List<AssetTypeDto> convertToAssetTypeDtoPlainList(List<AssetType> assetTypes){
        if (assetTypes != null){
            return assetTypes.stream().map(ConvertAssetTypeDto::convertToAssetTypeDtoPlain).collect(Collectors.toList());
        }
        return null;
    }



}
