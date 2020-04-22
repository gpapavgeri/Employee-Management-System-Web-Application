package com.company.myproject.util;

import com.company.myproject.dto.AssetDto;
import com.company.myproject.dto.OfficeDto;
import com.company.myproject.model.AssetOffice;

import java.util.Set;
import java.util.stream.Collectors;


public class ConvertAssetOfficeDto {

    public static AssetDto.OfficeDto convertToOfficeDto(AssetOffice assetOffice) {
        AssetDto.OfficeDto officeDto = new AssetDto.OfficeDto();

        officeDto.setOfficeId(assetOffice.getOfficeId());
        officeDto.setDateFrom(assetOffice.getDateFrom());
        officeDto.setDateTo(assetOffice.getDateTo());

        return officeDto;
    }

    public static OfficeDto.AssetDto convertToAssetDto(AssetOffice assetOffice) {
        OfficeDto.AssetDto assetDto = new OfficeDto.AssetDto();

        assetDto.setAssetId(assetOffice.getAssetId());
        assetDto.setDateFrom(assetOffice.getDateFrom());
        assetDto.setDateTo(assetOffice.getDateTo());

        return assetDto;
    }

    public static AssetOffice convertToAssetOffice(AssetDto.OfficeDto officeDto) {
        AssetOffice assetOffice = new AssetOffice();

        assetOffice.setOfficeId(officeDto.getOfficeId());
        assetOffice.setDateFrom(officeDto.getDateFrom());
        assetOffice.setDateTo(officeDto.getDateTo());

        return assetOffice;
    }

    public static AssetOffice convertToAssetOffice(OfficeDto.AssetDto assetDto) {
        AssetOffice assetOffice = new AssetOffice();

        assetOffice.setAssetId(assetDto.getAssetId());
        assetOffice.setDateFrom(assetDto.getDateFrom());
        assetOffice.setDateTo(assetDto.getDateTo());

        return assetOffice;
    }

    public static Set<AssetDto.OfficeDto> convertToOfficeDtoList(Set<AssetOffice> assetOffices){
        if (assetOffices != null){
            return assetOffices.stream().map(ConvertAssetOfficeDto::convertToOfficeDto).collect(Collectors.toSet());
        }
        return null;
    }

    public static Set<OfficeDto.AssetDto> convertToAssetDtoList(Set<AssetOffice> assetOffices){
        if (assetOffices != null){
            return assetOffices.stream().map(ConvertAssetOfficeDto::convertToAssetDto).collect(Collectors.toSet());
        }
        return null;
    }

}
