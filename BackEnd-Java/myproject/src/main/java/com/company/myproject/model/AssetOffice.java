package com.company.myproject.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="AssetOffice")
@IdClass(AssetOfficeId.class)
public class AssetOffice{

    @EmbeddedId
    private AssetOfficeId id;

    @Id
    @Type(type = "uuid-char")
    @Column(name = "AssetId", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID assetId;

    @Id
    @Type(type = "uuid-char")
    @Column(name = "OfficeId", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID officeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assetId")
    @Type(type = "uuid-char")
    @JoinColumn(name = "AssetId", columnDefinition="uniqueidentifier", updatable = false, insertable = false, referencedColumnName = "Id")
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("officeId")
    @Type(type = "uuid-char")
    @JoinColumn(name = "OfficeId", columnDefinition="uniqueidentifier", updatable = false, insertable = false, referencedColumnName = "Id")
    private Office office;

    @Column(name = "DateFrom")
    private Instant dateFrom;

    @Column(name = "DateTo")
    private Instant dateTo;

    public AssetOffice() {}

    public AssetOffice(Asset asset, Office office) {
        this.asset = asset;
        this.office = office;
        this.id = new AssetOfficeId(asset.getId(), office.getId());
    }

    public AssetOfficeId getId() {
        return id;
    }

    public void setId(AssetOfficeId id) {
        this.id = id;
    }

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(UUID assetId) {
        this.assetId = assetId;
    }

    public UUID getOfficeId() {
        return officeId;
    }

    public void setOfficeId(UUID officeId) {
        this.officeId = officeId;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AssetOffice that = (AssetOffice) o;
//        return Objects.equals(assetId, that.assetId) &&
//                Objects.equals(officeId, that.officeId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(assetId, officeId);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssetOffice)) return false;
        AssetOffice that = (AssetOffice) o;
        return Objects.equals(getAssetId(), that.getAssetId()) &&
                Objects.equals(getOfficeId(), that.getOfficeId()) &&
                Objects.equals(getDateFrom(), that.getDateFrom()) &&
                Objects.equals(getDateTo(), that.getDateTo());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAssetId(), getOfficeId(), getDateFrom(), getDateTo());
    }
}
