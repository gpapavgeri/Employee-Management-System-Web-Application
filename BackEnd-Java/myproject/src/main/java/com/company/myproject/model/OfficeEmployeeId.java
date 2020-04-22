package com.company.myproject.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class OfficeEmployeeId implements Serializable {

    @Type(type = "uuid-char")
    @Column(name = "OfficeId", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID officeId;

    @Type(type = "uuid-char")
    @Column(name = "EmployeeId", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID employeeId;

    public OfficeEmployeeId() {}

    public OfficeEmployeeId(UUID officeId, UUID employeeId) {
        this.officeId = officeId;
        this.employeeId = employeeId;
    }

    public UUID getOfficeId() {
        return officeId;
    }

    public void setOfficeId(UUID officeId) {
        this.officeId = officeId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeEmployeeId that = (OfficeEmployeeId) o;
        return Objects.equals(officeId, that.officeId) &&
                Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(officeId, employeeId);
    }



    @Override
    public String toString() {
        return "OfficeEmployeeId{" +
                "officeId=" + officeId +
                ", employeeId=" + employeeId +
                '}';
    }
}
