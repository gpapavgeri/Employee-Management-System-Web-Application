package com.company.myproject.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class AssetOfficeId implements Serializable {

    @Type(type = "uuid-char")
    @Column(name = "AssetId", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID assetId;

    @Type(type = "uuid-char")
    @Column(name = "OfficeId", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID officeId;

    private AssetOfficeId() {}

    public AssetOfficeId(UUID assetId, UUID officeId) {
        this.assetId = assetId;
        this.officeId = officeId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssetOfficeId that = (AssetOfficeId) o;
        return Objects.equals(assetId, that.assetId) &&
                Objects.equals(officeId, that.officeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId, officeId);
    }
}
