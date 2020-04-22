package com.company.myproject.dao;

import com.company.myproject.model.AssetOffice;
import com.company.myproject.model.AssetOfficeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class AssetOfficeRepository extends GenericJpaEntityRepository<AssetOffice, AssetOfficeId> {

    private JpaRepository<AssetOffice, AssetOfficeId> jpaRepository;
    private PagingSortingRepository<AssetOffice, AssetOfficeId> pagingSortingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AssetOfficeRepository(JpaRepository<AssetOffice, AssetOfficeId> theJpaRepository,
                                 PagingSortingRepository<AssetOffice, AssetOfficeId> thePagingSortingRepository) {
        super(theJpaRepository, thePagingSortingRepository);
        this.jpaRepository = theJpaRepository;
        this.jpaRepository.setClazz(AssetOffice.class);
        this.pagingSortingRepository = thePagingSortingRepository;
        this.pagingSortingRepository.setClazz(AssetOffice.class);
    }

    public AssetOffice findByCompositeId(AssetOffice assetOffice){
        TypedQuery<AssetOffice> q = entityManager.createQuery("SELECT ao FROM AssetOffice ao WHERE ao.officeId = :officeId AND ao.assetId = :assetId", AssetOffice.class);
        q.setParameter("officeId", assetOffice.getOfficeId());
        q.setParameter("assetId", assetOffice.getAssetId());
        try {
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
