package com.pablomr.TCHub.generico.servicio;

import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;

import java.util.List;

public interface GenericService<T extends IdentifiedEntity<ID>, ID> {

    T findById(ID id);
    List<T> findAll();

    T save(T entity);

    T update(T entity, ID id);

    Boolean deleteById(ID id);

}
