package com.pablomr.TCHub.generico.controlador;

import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericControllerImpl <T extends IdentifiedEntity<ID>, ID, D> implements GenericController<T, ID, D> {

    @Autowired
    private GenericDtoServiceImpl<T, ID, D> service;


    @Override
    @GetMapping
    @Operation(summary = "Listar todos", description = "Lista todos los datos de la tabla")
    public ResponseEntity<List<D>> getAll() {
        return new ResponseEntity<>(
                service.findAll(),
                HttpStatus.OK
        );
    }

    @Override
    @GetMapping("id/{id}")
    @Operation(summary = "Busqueda por Id", description = "Recupera el elemento de la tabla dado su Id")
    public ResponseEntity<D> getById(@PathVariable ID id) {
        return new ResponseEntity<>(
                service.findById(id),
                HttpStatus.OK
        );
    }

    @Override
    @PostMapping
    @Operation(summary = "Busqueda por Id", description = "Recupera el elemento de la tabla dado su Id")
    public ResponseEntity<D> create(@RequestBody D dto) {
        return new ResponseEntity<>(
                service.saveDto(dto),
                HttpStatus.OK
        );
    }

    @Override
    @PutMapping("id/{id}")
    @Operation(summary = "Actualización de dato", description = "Actualiza los valores de una entrada")
    public ResponseEntity<D> update(@PathVariable ID id, @RequestBody D entity) {
        return new ResponseEntity<>(
                service.updateDto(entity, id),
                HttpStatus.OK
        );
    }

}
