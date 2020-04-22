package com.company.myproject.dao;

import com.company.myproject.model.Company;
import com.company.myproject.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.UUID;

@Repository
public class CompanyRepository extends GenericJpaEntityRepository<Company, UUID> {

    @PersistenceContext
    private EntityManager entityManager;

    private JpaRepository<Company, UUID> jpaRepository;
    private PagingSortingRepository<Company, UUID> pagingSortingRepository;

    @Autowired
    public CompanyRepository(JpaRepository<Company, UUID> theJpaRepository,
                             PagingSortingRepository<Company, UUID> thePagingSortingRepository) {
        super(theJpaRepository, thePagingSortingRepository);
        this.jpaRepository = theJpaRepository;
        this.jpaRepository.setClazz(Company.class);
        this.pagingSortingRepository = thePagingSortingRepository;
        this.pagingSortingRepository.setClazz(Company.class);
    }

    public Company findCompanyForOffice(UUID officeId) {
        TypedQuery<Company> query = entityManager.createQuery("SELECT DISTINCT b.company\n" +
                "FROM Branch b\n" +
                "inner join Office o on o.branch.id = b.id\n" +
                "WHERE o.id = :officeId", Company.class);
        query.setParameter("officeId", officeId);
        return query.getSingleResult();
    }

    public Company findCompanyForEmployee(UUID employeeId) {
        TypedQuery<Company> query = entityManager.createQuery("SELECT DISTINCT b.company\n" +
                "FROM Branch b\n" +
                "left join Office o on o.branch.id = b.id\n" +
                "left join OfficeEmployee oe on o.id = oe.officeId\n" +
                "WHERE oe.employeeId = :employeeId", Company.class);
        query.setParameter("employeeId", employeeId);
        return query.getSingleResult();
    }


}
