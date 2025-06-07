package com.pablomr.TCHub.generico.servicio.conDTO;

import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;

import java.util.List;

public interface GenericDtoService<T extends IdentifiedEntity<ID>, ID, D> {

    D findById(ID id);
    List<D> findAll();

    D save(T entity);

    D saveDto(D entity);

    D update(T entity, ID id);

    D updateDto(D entity, ID id);
}
