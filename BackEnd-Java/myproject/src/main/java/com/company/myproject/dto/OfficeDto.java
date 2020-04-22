package com.company.myproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OfficeDto {

    private UUID id;
    private Integer code;
    private BranchDto branch;
    private Set<AssetDto> assets;
    private Set<EmployeeDto> employees;


    public OfficeDto() {
    }

    public OfficeDto(Integer code) {

        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BranchDto getBranch() {
        return branch;
    }

    public void setBranch(BranchDto branch) {
        this.branch = branch;
    }

    public Set<AssetDto> getAssets() {
        return assets;
    }

    public void setAssets(Set<AssetDto> assets) {
        this.assets = assets;
    }

    public Set<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDto> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeDto)) return false;
        OfficeDto officeDto = (OfficeDto) o;
        return Objects.equals(getCode(), officeDto.getCode()) &&
                Objects.equals(getBranch(), officeDto.getBranch());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getBranch());
    }

    public static class EmployeeDto {

        private UUID employeeId;
        private Instant dateFrom;
        private Instant dateTo;

        public EmployeeDto() {
        }

        public EmployeeDto(UUID employeeId, Instant dateFrom, Instant dateTo) {
            this.employeeId = employeeId;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }

        public UUID getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(UUID employeeId) {
            this.employeeId = employeeId;
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
            if (!(o instanceof EmployeeDto)) return false;
            EmployeeDto that = (EmployeeDto) o;
            return Objects.equals(getEmployeeId(), that.getEmployeeId());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getEmployeeId());
        }
    }

    public static class AssetDto {

        private UUID assetId;
        private Instant dateFrom;
        private Instant dateTo;

        public AssetDto() {
        }

        public AssetDto(UUID assetId, Instant dateFrom, Instant dateTo) {
            this.assetId = assetId;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }

        public UUID getAssetId() {
            return assetId;
        }

        public void setAssetId(UUID assetId) {
            this.assetId = assetId;
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
            if (!(o instanceof AssetDto)) return false;
            AssetDto assetDto = (AssetDto) o;
            return Objects.equals(getAssetId(), assetDto.getAssetId());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getAssetId());
        }
    }



}
