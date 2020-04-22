package com.company.myproject.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Repository
public class JpaRepository<T, PK> {

    private Class<T> clazz;

    @PersistenceContext
    private EntityManager entityManager;

    public void setClazz(Class<T> classToSet) {
        this.clazz = classToSet;
    }


    public T findById( PK id ){

        return entityManager.find(clazz, id );
    }

    public List< T > findAll() {

        return entityManager.createQuery( "from " + clazz.getName() ).getResultList();
    }

    public T save( T entity ){

        entityManager.persist( entity );
        return entity;
    }

    public T update( T entity ){

        return entityManager.merge( entity );
    }

    public void delete( T entity ){

        entityManager.remove( entity );
    }

    public void deleteById( PK entityId ){
        T entity = findById( entityId );
        delete( entity );
    }



}
