package com.company.myproject.dao;

import com.company.myproject.model.Pageable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Repository
public class PagingSortingRepository<T, PK> {

    private Class<T> clazz;

    @PersistenceContext
    private EntityManager entityManager;

    public void setClazz(Class<T> classToSet) {
        this.clazz = classToSet;
    }

    public List<T> findAllPaging(Pageable pageable) {

        int pageNumber = pageable.getPageNo();
        int pageSize = pageable.getPageSize();
        String sortBy = pageable.getSortBy();

//        // Calculating the last page
//        Query queryTotal = entityManager.createQuery("Select count(c.id) from " + clazz.getName() + " c");
//        long countResult = (long)queryTotal.getSingleResult();
//        int lastPageNumber = (int) ((countResult / pageSize) + 1);

        Query query = entityManager.createQuery("FROM " + clazz.getName() +
                " c ORDER BY c." + sortBy + " ASC NULLS LAST");
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();

    }

    public long findTotalCount() {
        Query queryTotal = entityManager.createQuery("SELECT COUNT( DISTINCT c.id) FROM " + clazz.getName() + " c");
        return (long) queryTotal.getSingleResult();
    }


}
