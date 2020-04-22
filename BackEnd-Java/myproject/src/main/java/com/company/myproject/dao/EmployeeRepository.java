package com.company.myproject.dao;

import com.company.myproject.model.Employee;
import com.company.myproject.model.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public class EmployeeRepository extends GenericJpaEntityRepository<Employee, UUID> {

    @PersistenceContext
    private EntityManager entityManager;

    private JpaRepository<Employee, UUID> jpaRepository;
    private PagingSortingRepository<Employee, UUID> pagingSortingRepository;

    @Autowired
    public EmployeeRepository(JpaRepository<Employee, UUID> theJpaRepository,
                              PagingSortingRepository<Employee, UUID> thePagingSortingRepository) {
        super(theJpaRepository, thePagingSortingRepository);
        this.jpaRepository = theJpaRepository;
        this.jpaRepository.setClazz(Employee.class);
        this.pagingSortingRepository = thePagingSortingRepository;
        this.pagingSortingRepository.setClazz(Employee.class);
    }

    public Employee findByIdWithList(UUID employeeId) {
        TypedQuery<Employee> q = entityManager.createQuery("SELECT e FROM Employee e LEFT JOIN FETCH e.offices WHERE e.id = :employeeId", Employee.class);
        q.setParameter("employeeId", employeeId);
        try {
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Employee> findEmployees(UUID companyId, UUID branchId, UUID officeId, Pageable pageable) {
        int pageNumber = pageable.getPageNo();
        int pageSize = pageable.getPageSize();
        String sortBy = pageable.getSortBy();

        TypedQuery<Employee> query = entityManager.createQuery("SELECT DISTINCT e FROM Employee e " +
                "LEFT JOIN OfficeEmployee oe ON e.id = oe.employeeId " +
                "INNER JOIN Office o ON oe.officeId = o.id " +
                "INNER JOIN Branch b ON o.branch.id = b.id " +
                "WHERE (:companyId is null or b.company.id = :companyId) " +
                "AND (:branchId is null or b.id = :branchId) " +
                "AND (:officeId is null or o.id = :officeId)" +
                "ORDER BY e." + sortBy, Employee.class);
        query.setParameter("companyId", companyId);
        query.setParameter("branchId", branchId);
        query.setParameter("officeId", officeId);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Long getTotalCount(UUID companyId, UUID branchId, UUID officeId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT( DISTINCT e.id) FROM Employee e " +
                "LEFT JOIN OfficeEmployee oe ON e.id = oe.employeeId " +
                "LEFT JOIN Office o ON oe.officeId = o.id " +
                "LEFT JOIN Branch b ON o.branch.id = b.id " +
                "WHERE (:companyId is null or b.company.id = :companyId) " +
                "AND (:branchId is null or b.id = :branchId) " +
                "AND (:officeId is null or o.id = :officeId)", Long.class);
        query.setParameter("companyId", companyId);
        query.setParameter("branchId", branchId);
        query.setParameter("officeId", officeId);
        return query.getSingleResult();
    }

}
