package com.company.myproject.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="AssetType")
public class AssetType {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "Id", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "Type", columnDefinition = "nvarchar")
    private String type;

    @OneToMany(mappedBy = "assetType")
    private List<Asset> assets;

    public AssetType() {
    }

    public AssetType(String type) {
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

    @Override
    public String toString() {
        return "AssetType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    // add convenience method for bi-directional relationship

    public void add(Asset tempAsset) {
        if(assets == null) {
            assets = new ArrayList<>();
        }

        assets.add(tempAsset);

        tempAsset.setAssetType(this);
    }
}
