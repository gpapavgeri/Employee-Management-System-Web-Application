package com.company.myproject.dao;

import com.company.myproject.model.Branch;
import com.company.myproject.model.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Repository
public class BranchRepository extends GenericJpaEntityRepository<Branch, UUID> {

    @PersistenceContext
    private EntityManager entityManager;

    private JpaRepository<Branch, UUID> jpaRepository;
    private PagingSortingRepository<Branch, UUID> pagingSortingRepository;

    @Autowired
    public BranchRepository(JpaRepository<Branch, UUID> theJpaRepository,
                            PagingSortingRepository<Branch, UUID> thePagingSortingRepository) {
        super(theJpaRepository, thePagingSortingRepository);
        this.jpaRepository = theJpaRepository;
        this.jpaRepository.setClazz(Branch.class);
        this.pagingSortingRepository = thePagingSortingRepository;
        this.pagingSortingRepository.setClazz(Branch.class);
    }

    public Branch findByIdWithList(UUID branchId) {
        TypedQuery<Branch> q = entityManager.createQuery("SELECT b FROM Branch b JOIN FETCH b.offices WHERE b.id = :branchId", Branch.class);
        q.setParameter("branchId", branchId);
        try {
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Branch> findBranches(UUID companyId, Pageable pageable) {
        int pageNumber = pageable.getPageNo();
        int pageSize = pageable.getPageSize();
        String sortBy = pageable.getSortBy();

        TypedQuery<Branch> query = entityManager.createQuery("SELECT b FROM Branch b " +
                "WHERE b.company.id = :companyId " +
                "ORDER BY b." + sortBy + " ASC NULLS LAST", Branch.class);
        query.setParameter("companyId", companyId);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<Branch> findBranchesWithOffices(UUID companyId, Pageable pageable) {
        int pageNumber = pageable.getPageNo();
        int pageSize = pageable.getPageSize();
        String sortBy = pageable.getSortBy();

        TypedQuery<Branch> query = entityManager.createQuery("SELECT b FROM Branch b " +
                "LEFT JOIN FETCH b.offices " +
                "WHERE (:companyId IS NULL OR b.company.id = :companyId) " +
                "ORDER BY b." + sortBy + " ASC NULLS LAST", Branch.class);
        query.setParameter("companyId", companyId);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Long getTotalCount(UUID companyId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT( DISTINCT b.id) FROM Branch b " +
                "WHERE b.company.id = :companyId ", Long.class);
        query.setParameter("companyId", companyId);
        return query.getSingleResult();
    }

}
