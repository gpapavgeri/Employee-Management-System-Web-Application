package com.company.myproject.dao;

import com.company.myproject.model.Asset;
import com.company.myproject.model.Company;
import com.company.myproject.model.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AssetRepository extends GenericJpaEntityRepository<Asset, UUID> {

    @PersistenceContext
    private EntityManager entityManager;

    private JpaRepository<Asset, UUID> jpaRepository;
    private PagingSortingRepository<Asset, UUID> pagingSortingRepository;

    @Autowired
    public AssetRepository(JpaRepository<Asset, UUID> theJpaRepository,
                           PagingSortingRepository<Asset, UUID> thePagingSortingRepository) {
        super(theJpaRepository, thePagingSortingRepository);
        this.jpaRepository = theJpaRepository;
        this.jpaRepository.setClazz(Asset.class);
        this.pagingSortingRepository = thePagingSortingRepository;
        this.pagingSortingRepository.setClazz(Asset.class);
    }

    public Asset findByIdWithList(UUID assetId) {
        TypedQuery<Asset> q = entityManager.createQuery("SELECT a FROM Asset a LEFT JOIN FETCH a.offices WHERE a.id = :assetId", Asset.class);
        q.setParameter("assetId", assetId);
        try {
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Asset> findAssets(UUID companyId, UUID officeId, UUID assetTypeId, Pageable pageable) {
        int pageNumber = pageable.getPageNo();
        int pageSize = pageable.getPageSize();
        String sortBy = pageable.getSortBy();

        TypedQuery<Asset> query = entityManager.createQuery("SELECT DISTINCT a FROM Asset a " +
                "LEFT JOIN AssetOffice ae ON a.id = ae.asset.id " +
                "WHERE (:companyId is null or a.company.id = :companyId) " +
                "AND (:officeId is null or ae.office.id = :officeId) " +
                "AND (:assetTypeId is null or a.assetType.id = :assetTypeId)" +
                "ORDER BY a." + sortBy, Asset.class);
        query.setParameter("companyId", companyId);
        query.setParameter("officeId", officeId);
        query.setParameter("assetTypeId", assetTypeId);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Long getTotalCount(UUID companyId, UUID officeId, UUID assetTypeId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT( DISTINCT a.id) FROM Asset a " +
                "LEFT JOIN AssetOffice ae ON a.id = ae.asset.id " +
                "WHERE (:companyId is null or a.company.id = :companyId) " +
                "AND (:officeId is null or ae.office.id = :officeId) " +
                "AND (:assetTypeId is null or a.assetType.id = :assetTypeId)", Long.class);
        query.setParameter("companyId", companyId);
        query.setParameter("officeId", officeId);
        query.setParameter("assetTypeId", assetTypeId);
        return query.getSingleResult();
    }

}
