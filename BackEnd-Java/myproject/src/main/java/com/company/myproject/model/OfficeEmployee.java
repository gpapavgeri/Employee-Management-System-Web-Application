package com.company.myproject.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="OfficeEmployee")
@IdClass(OfficeEmployeeId.class)

public class OfficeEmployee {

    @EmbeddedId
    private OfficeEmployeeId id;

    @Id
    @Type(type = "uuid-char")
    @Column(name = "OfficeId", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID officeId;

    @Id
    @Type(type = "uuid-char")
    @Column(name = "EmployeeId", columnDefinition="uniqueidentifier", nullable = false, updatable = false)
    private UUID employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("officeId")
    @Type(type = "uuid-char")
    @JoinColumn(name = "OfficeId", columnDefinition="uniqueidentifier", updatable = false, insertable = false, referencedColumnName = "Id")
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @Type(type = "uuid-char")
    @JoinColumn(name = "EmployeeId", columnDefinition="uniqueidentifier", updatable = false, insertable = false, referencedColumnName = "Id")
    private Employee employee;

    @Column(name = "DateFrom")
    private Instant dateFrom;

    @Column(name = "DateTo")
    private Instant dateTo;

    public OfficeEmployee() {}

    public OfficeEmployee(Office office, Employee employee) {
        this.office = office;
        this.employee = employee;
        this.id = new OfficeEmployeeId(office.getId(), employee.getId());
    }

    public OfficeEmployeeId getId() {
        return id;
    }

    public void setId(OfficeEmployeeId id) {
        this.id = id;
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

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
//        OfficeEmployee that = (OfficeEmployee) o;
//        return Objects.equals(officeId, that.officeId) &&
//                Objects.equals(employeeId, that.employeeId);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(officeId, employeeId);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeEmployee)) return false;
        OfficeEmployee that = (OfficeEmployee) o;
        return Objects.equals(getOfficeId(), that.getOfficeId()) &&
                Objects.equals(getEmployeeId(), that.getEmployeeId()) &&
                Objects.equals(getDateFrom(), that.getDateFrom()) &&
                Objects.equals(getDateTo(), that.getDateTo());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getOfficeId(), getEmployeeId(), getDateFrom(), getDateTo());
    }
}
