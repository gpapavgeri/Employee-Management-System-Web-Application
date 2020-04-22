package com.company.myproject.dao;

import com.company.myproject.model.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GenericJpaEntityRepository<T, PK> implements EntityRepository<T, PK> {

    private JpaRepository<T, PK> jpaRepository;
    private PagingSortingRepository<T, PK> pagingSortingRepository;

    public GenericJpaEntityRepository(JpaRepository<T, PK> theJpaRepository,
                                      PagingSortingRepository<T, PK> thePagingSortingRepository) {

        this.jpaRepository = theJpaRepository;
        this.pagingSortingRepository = thePagingSortingRepository;
    }

    @Override
    public T findById(PK id) {
        return (T) jpaRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<T> findAllPaging(Pageable pageable) {
        return pagingSortingRepository.findAllPaging(pageable);
    }

    @Override
    public Long findTotalCount() {
        return pagingSortingRepository.findTotalCount();
    }

    @Override
    public T save(T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public T update(T entity) {
        return (T) jpaRepository.update(entity);
    }

    @Override
    public void delete(T entity) {
        jpaRepository.delete(entity);
    }

    @Override
    public void deleteById(PK entityId) {
        jpaRepository.deleteById(entityId);
    }
}
