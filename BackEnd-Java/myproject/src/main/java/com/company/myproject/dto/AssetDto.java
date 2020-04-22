package com.company.myproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AssetDto {

    private UUID id;
    private String serialNumber;
    private String brand;
    private CompanyDto company;
    private AssetTypeDto assetType;
    private Set<OfficeDto> offices;

    public AssetDto() {
    }

    public AssetDto(String serialNumber, String brand) {
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

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public AssetTypeDto getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetTypeDto assetType) {
        this.assetType = assetType;
    }

    public Set<OfficeDto> getOffices() {
        return offices;
    }

    public void setOffices(Set<OfficeDto> offices) {
        this.offices = offices;
    }

    public static class OfficeDto {

        private UUID officeId;
        private Instant dateFrom;
        private Instant dateTo;

        public OfficeDto() {
        }

        public OfficeDto(UUID assetId, UUID officeId, Instant dateFrom, Instant dateTo) {
            this.officeId = officeId;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }

        public UUID getOfficeId() {
            return officeId;
        }

        public void setOfficeId(UUID officeId) {
            this.officeId = officeId;
        }

        public Instant getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(Instant dateFrom) {
            this.dateFrom = dateFrom;
        }

        public Instant getDateTo() {
            return dateTo;
        }

        public void setDateTo(Instant dateTo) {
            this.dateTo = dateTo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfficeDto)) return false;
            OfficeDto officeDto = (OfficeDto) o;
            return Objects.equals(getOfficeId(), officeDto.getOfficeId());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getOfficeId());
        }
    }
}