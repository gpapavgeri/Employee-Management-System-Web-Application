package com.company.myproject.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name="Asset")
public class Asset {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "Id", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "SerialNumber", columnDefinition = "nvarchar")
    private String serialNumber;

    @NotNull
    @Column(name ="Brand", columnDefinition = "nvarchar")
    private String brand;

    @ManyToOne
    @JoinColumn(name="CompanyId")
    private Company company;

    @ManyToOne
    @JoinColumn(name="AssetTypeId")
    private AssetType assetType;


    @OneToMany(mappedBy = "asset")
    private Set<AssetOffice> offices = new HashSet<>();

    public Asset() {
    }

    public Asset(String serialNumber, String brand) {
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

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }

    public Company getCompany() {
        return company;
    }

    public String getCompanyName() {
        return company.getName();
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public String getAssetTypeName() {
        return assetType.getType();
    }
    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Set<AssetOffice> getOffices() {
        return offices;
    }

    public void setOffices(Set<AssetOffice> offices) {
        this.offices = offices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Objects.equals(serialNumber, asset.serialNumber) &&
                Objects.equals(brand, asset.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, brand);
    }

}
