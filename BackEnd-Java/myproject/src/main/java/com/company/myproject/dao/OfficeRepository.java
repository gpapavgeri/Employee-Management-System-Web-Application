package com.company.myproject.dao;

import com.company.myproject.model.Office;
import com.company.myproject.model.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class OfficeRepository extends GenericJpaEntityRepository<Office, UUID> {

    @PersistenceContext
    private EntityManager entityManager;

    private JpaRepository<Office, UUID> jpaRepository;
    private PagingSortingRepository<Office, UUID> pagingSortingRepository;

    @Autowired
    public OfficeRepository(JpaRepository<Office, UUID> theJpaRepository,
                            PagingSortingRepository<Office, UUID> thePagingSortingRepository) {
        super(theJpaRepository, thePagingSortingRepository);
        this.jpaRepository = theJpaRepository;
        this.jpaRepository.setClazz(Office.class);
        this.pagingSortingRepository = thePagingSortingRepository;
        this.pagingSortingRepository.setClazz(Office.class);
    }

    public Office findByIdWithLists(UUID officeId) {
        TypedQuery<Office> q = entityManager.createQuery("SELECT o FROM Office o LEFT JOIN FETCH o.assets LEFT JOIN FETCH o.employees WHERE o.id = :officeId", Office.class);
        q.setParameter("officeId", officeId);
        try {
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Office> findOffices(UUID companyId, UUID branchId, Pageable pageable) {
        int pageNumber = pageable.getPageNo();
        int pageSize = pageable.getPageSize();
        String sortBy = pageable.getSortBy();

        TypedQuery<Office> query = entityManager.createQuery("SELECT o FROM Office o " +
                "INNER JOIN Branch b ON o.branch.id = b.id " +
                "WHERE (:companyId is null or b.company.id = :companyId) " +
                "AND (:branchId is null or b.id = :branchId) " +
                "order by o." + sortBy + " asc NULLS LAST", Office.class);
        query.setParameter("companyId", companyId);
        query.setParameter("branchId", branchId);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Long getTotalCount(UUID companyId, UUID branchId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT( DISTINCT o.id) FROM Office o " +
                "INNER JOIN Branch b ON o.branch.id = b.id " +
                "WHERE (:companyId is null or b.company.id = :companyId) " +
                "AND (:branchId is null or b.id = :branchId) ", Long.class);
        query.setParameter("companyId", companyId);
        query.setParameter("branchId", branchId);
        return query.getSingleResult();
    }


    public Office findByIdCriteriaQuery(UUID officeId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Office> criteriaQuery = criteriaBuilder.createQuery(Office.class);
        Root<Office> office = criteriaQuery.from(Office.class);
        office.fetch("employees", JoinType.INNER);
        criteriaQuery.where(criteriaBuilder.equal(office.get("id"), officeId));

        TypedQuery<Office> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    public Office findByIdEntityGraph(UUID officeId) {
        EntityGraph graph = entityManager.getEntityGraph("graph.Office.assetsEmployees");
        Map hints = new HashMap();
        hints.put("javax.persistence.fetchgraph", graph);

        return entityManager.find(Office.class, officeId, hints);
    }


}
