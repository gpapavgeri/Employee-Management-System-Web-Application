package com.company.myproject.util;

import com.company.myproject.dto.AssetDto;
import com.company.myproject.dto.AssetPersistDto;
import com.company.myproject.dto.AssetTypeDto;
import com.company.myproject.dto.CompanyDto;
import com.company.myproject.model.Asset;
import com.company.myproject.model.AssetOffice;
import com.company.myproject.model.AssetType;
import com.company.myproject.model.Company;

import javax.persistence.Convert;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ConvertAssetDto {

    public static AssetDto convertToAssetDto(Asset asset) {
        AssetDto assetDto = new AssetDto();

        assetDto.setId(asset.getId());
        assetDto.setSerialNumber(asset.getSerialNumber());
        assetDto.setBrand(asset.getBrand());

        Company company = asset.getCompany();
        CompanyDto companyDto = ConvertCompanyDto.convertToCompanyDtoPlain(company);
        assetDto.setCompany(companyDto);

        AssetType assetType = asset.getAssetType();
        AssetTypeDto assetTypeDto = ConvertAssetTypeDto.convertToAssetTypeDtoPlain(assetType);
        assetDto.setAssetType(assetTypeDto);

        Set<AssetOffice> assetOffices = asset.getOffices();
        Set<AssetDto.OfficeDto> offices = ConvertAssetOfficeDto.convertToOfficeDtoList(assetOffices);
        assetDto.setOffices(offices);

        return assetDto;
    }

    public static AssetDto convertToAssetDtoPlain(Asset asset) {
        AssetDto assetDto = new AssetDto();

        assetDto.setId(asset.getId());
        assetDto.setSerialNumber(asset.getSerialNumber());
        assetDto.setBrand(asset.getBrand());

        Company company = asset.getCompany();
        CompanyDto companyDto = ConvertCompanyDto.convertToCompanyDtoPlain(company);
        assetDto.setCompany(companyDto);

        AssetType assetType = asset.getAssetType();
        AssetTypeDto assetTypeDto = ConvertAssetTypeDto.convertToAssetTypeDtoPlain(assetType);
        assetDto.setAssetType(assetTypeDto);

        return assetDto;
    }

    public static Asset convertToAsset(AssetDto assetDto) {
        Asset asset = new Asset();

        asset.setId(assetDto.getId());
        asset.setSerialNumber(assetDto.getSerialNumber());
        asset.setBrand(assetDto.getBrand());

        Company company = ConvertCompanyDto.convertToCompanyPlain(assetDto.getCompany());
        asset.setCompany(company);

        AssetType assetType = ConvertAssetTypeDto.convertToAssetType(assetDto.getAssetType());
        asset.setAssetType(assetType);

        return asset;
    }

    public static Asset convertToAssetPlain(AssetDto assetDto) {
        Asset asset = new Asset();

        asset.setId(assetDto.getId());
        asset.setSerialNumber(assetDto.getSerialNumber());
        asset.setBrand(assetDto.getBrand());

        Company company = ConvertCompanyDto.convertToCompanyPlain(assetDto.getCompany());
        asset.setCompany(company);

        return asset;
    }


    public static List<AssetDto> convertToAssetDtoList(List<Asset> assets) {
        if (assets != null) {
            return assets.stream().map(ConvertAssetDto::convertToAssetDto).collect(Collectors.toList());
        }
        return null;
    }

    public static List<AssetDto> convertToAssetDtoPlainList(List<Asset> assets) {
        if (assets != null) {
            return assets.stream().map(ConvertAssetDto::convertToAssetDtoPlain).collect(Collectors.toList());
        }
        return null;
    }

    public static List<Asset> convertToAssetList(List<AssetDto> assetDtos) {
        if (assetDtos != null) {
            return assetDtos.stream().map(ConvertAssetDto::convertToAsset).collect(Collectors.toList());
        }
        return null;
    }


}
