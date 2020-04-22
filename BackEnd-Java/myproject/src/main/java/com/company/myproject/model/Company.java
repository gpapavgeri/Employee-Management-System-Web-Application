package com.company.myproject.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name="Company")
public class Company {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "Id", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "Name", columnDefinition = "nvarchar")
    private String name;

    @OneToMany(mappedBy = "company")
    private Set<Asset> assets = new HashSet();

    @OneToMany(mappedBy = "company")
    private Set<Branch> branches = new HashSet();

    public Company(){

    }

    public Company(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }

    public Set<Branch> getBranches() {
        return branches;
    }

    public void setBranches(Set<Branch> branches) {
        this.branches = branches;
    }

//     add convenience methods for bi-directional relationship

    public void add(Asset tempAsset) {
        if(assets == null) {
            assets = new HashSet<>();
        }

        assets.add(tempAsset);

        tempAsset.setCompany(this);
    }

    public void add(Branch tempBranch) {
        if(branches == null) {
            branches = new HashSet<>();
        }

        branches.add(tempBranch);

        tempBranch.setCompany(this);
    }

}
