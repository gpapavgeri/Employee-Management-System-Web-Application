package com.company.myproject.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name="Office")
@NamedEntityGraph(name = "graph.Office.assetsEmployees", attributeNodes = {@NamedAttributeNode("assets"), @NamedAttributeNode("employees")})
public class Office {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "Id", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @Column(name = "Code", columnDefinition = "int")
    private Integer code;

//    @JsonIgnore
    @NotNull
    @ManyToOne
    @JoinColumn(name="BranchId")
    private Branch branch;

//    @JsonIgnore
    @OneToMany(mappedBy = "office")
    private Set<AssetOffice> assets = new HashSet<>();


    @OneToMany(mappedBy = "office")
    private Set<OfficeEmployee> employees = new HashSet<>();


    public Office() {
    }

    public Office(Integer code) {
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

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Set<AssetOffice> getAssets() {
        return assets;
    }

    public void setAssets(Set<AssetOffice> assets) {
        this.assets = assets;
    }

    public Set<OfficeEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<OfficeEmployee> employees) {
        this.employees = employees;
    }

    public void addAsset(Asset asset) {
        AssetOffice assetOffice = new AssetOffice(asset, this);
        assets.add(assetOffice);
        asset.getOffices().add(assetOffice);
    }

    public void removeAsset(Asset asset) {
        for (Iterator<AssetOffice> iterator = assets.iterator();
            iterator.hasNext(); ) {
            AssetOffice assetOffice = iterator.next();

            if (assetOffice.getOffice().equals(this) &&
               assetOffice.getAsset().equals(asset)) {
                iterator.remove();
                assetOffice.getAsset().getOffices().remove(assetOffice);
                assetOffice.setOffice(null);
                assetOffice.setAsset(null);
            }
        }
    }

    public void addEmployee(Employee employee) {
        OfficeEmployee officeEmployee = new OfficeEmployee(this, employee);
        employees.add(officeEmployee);
        employee.getOffices().add(officeEmployee);
    }

    public void removeEmployee(Employee employee) {
        for (Iterator<OfficeEmployee> iterator = employees.iterator();
             iterator.hasNext(); ) {
            OfficeEmployee officeEmployee = iterator.next();

            if (officeEmployee.getOffice().equals(this) &&
                    officeEmployee.getEmployee().equals(employee)) {
                iterator.remove();
                officeEmployee.getEmployee().getOffices().remove(officeEmployee);
                officeEmployee.setOffice(null);
                officeEmployee.setEmployee(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return Objects.equals(code, office.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
