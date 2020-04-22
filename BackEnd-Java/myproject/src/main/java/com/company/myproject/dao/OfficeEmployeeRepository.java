package com.company.myproject.dao;

import com.company.myproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public class OfficeEmployeeRepository extends GenericJpaEntityRepository<OfficeEmployee, OfficeEmployeeId> {

    private JpaRepository<OfficeEmployee, OfficeEmployeeId> jpaRepository;
    private PagingSortingRepository<OfficeEmployee, OfficeEmployeeId> pagingSortingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OfficeEmployeeRepository(JpaRepository<OfficeEmployee, OfficeEmployeeId> theJpaRepository,
                                    PagingSortingRepository<OfficeEmployee, OfficeEmployeeId> thePagingSortingRepository) {
        super(theJpaRepository, thePagingSortingRepository);
        this.jpaRepository = theJpaRepository;
        this.jpaRepository.setClazz(OfficeEmployee.class);
        this.pagingSortingRepository = thePagingSortingRepository;
        this.pagingSortingRepository.setClazz(OfficeEmployee.class);
    }

    public OfficeEmployee findByCompositeId(OfficeEmployee officeEmployee){
        TypedQuery<OfficeEmployee> q = entityManager.createQuery("SELECT oe FROM OfficeEmployee oe WHERE oe.officeId = :officeId AND oe.employeeId = :employeeId", OfficeEmployee.class);
        q.setParameter("officeId", officeEmployee.getOfficeId());
        q.setParameter("employeeId", officeEmployee.getEmployeeId());
        try {
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<OfficeEmployee> findOfficeEmployeesByEmployeeId(UUID employeeId){
        TypedQuery<OfficeEmployee> q = entityManager.createQuery("SELECT oe FROM OfficeEmployee oe WHERE oe.employeeId = :employeeId", OfficeEmployee.class);
        q.setParameter("employeeId", employeeId);
        return q.getResultList();
    }

    public List<OfficeEmployee> findOfficeEmployees(Instant dateTo){
        TypedQuery<OfficeEmployee> q = entityManager.createQuery("SELECT oe FROM OfficeEmployee oe " +
                "WHERE (:dateTo is null or oe.dateTo = :dateTo)", OfficeEmployee.class);
        q.setParameter("dateTo", dateTo);
        return q.getResultList();
    }

}
