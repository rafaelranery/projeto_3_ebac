package br.com.rnery.dao.generic;

import br.com.rnery.domain.Persistent;

import java.util.Set;

public interface IGenericDAO<T extends Persistent> {
    public Integer register(T entity) throws Exception;
    public Integer update(T entity) throws Exception;
    public T getOne(Long key) throws Exception;
    public Set<T> getAll() throws Exception;
    public Integer delete(T entity) throws Exception;
}
