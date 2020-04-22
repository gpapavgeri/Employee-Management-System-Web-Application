package com.company.myproject.dao;

import com.company.myproject.model.AssetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.UUID;

@Repository
public class AssetTypeRepository extends GenericJpaEntityRepository<AssetType, UUID> {

    private JpaRepository<AssetType, UUID> jpaRepository;
    private PagingSortingRepository<AssetType, UUID> pagingSortingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AssetTypeRepository(JpaRepository<AssetType, UUID> theJpaRepository,
                               PagingSortingRepository<AssetType, UUID> thePagingSortingRepository) {
        super(theJpaRepository, thePagingSortingRepository);
        this.jpaRepository = theJpaRepository;
        this.jpaRepository.setClazz(AssetType.class);
        this.pagingSortingRepository = thePagingSortingRepository;
        this.pagingSortingRepository.setClazz(AssetType.class);
    }

    public AssetType findByIdWithList(UUID assetTypeId){
        TypedQuery<AssetType> q = entityManager.createQuery("SELECT a FROM AssetType a LEFT JOIN FETCH a.assets WHERE a.id = :assetTypeId", AssetType.class);
        q.setParameter("assetTypeId", assetTypeId);
        try {
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
