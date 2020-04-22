package com.company.myproject.dao;

import com.company.myproject.model.Pageable;

import java.util.List;

public interface EntityRepository<T, PK> {

    T findById(final PK id);

    List<T> findAll();

    List<T> findAllPaging(Pageable pageable);

    Long findTotalCount();

    T save(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final PK entityId);

}
