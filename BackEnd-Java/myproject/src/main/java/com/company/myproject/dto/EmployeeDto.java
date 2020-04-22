package com.company.myproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmployeeDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private Set<OfficeDto> offices;

    public EmployeeDto() {
    }

    public EmployeeDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

        public OfficeDto(UUID officeId, Instant dateFrom, Instant dateTo) {
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
