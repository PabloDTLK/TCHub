package com.pablomr.TCHub.generico.controlador;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenericController<T, ID, D> {

    ResponseEntity<List<D>> getAll();
    ResponseEntity<D> getById(ID id);
    ResponseEntity<D> create(D entity);
    ResponseEntity<D> update(ID id, D entity);
}
