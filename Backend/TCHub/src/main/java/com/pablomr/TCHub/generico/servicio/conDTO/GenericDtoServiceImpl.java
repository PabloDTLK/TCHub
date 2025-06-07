package com.pablomr.TCHub.generico.servicio.conDTO;

import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericDtoServiceImpl<T extends IdentifiedEntity<ID>, ID, D> implements GenericDtoService<T, ID, D> {

    @Autowired
    private GenericServiceImpl<T, ID> service;

    @Autowired
    private GenericMapper<T, D> mapper;

    @Override
    public D findById(ID id) {
        return mapper.toDto(
                service.findById(id)
        );
    }

    @Override
    public List<D> findAll() {
        return listToDto(
                service.findAll()
        );
    }

    @Override
    public D save(T entity) {
        return mapper.toDto(
                service.save(entity)
        );
    }

    @Override
    public D saveDto(D dto) {
        return mapper.toDto(
                service.save(
                        mapper.toEntity(dto)
                )
        );
    }

    @Override
    public D update(T entity, ID id) {
        return mapper.toDto(
                service.update(entity, id)
        );
    }

    @Override
    public D updateDto(D dto, ID id) {
        return mapper.toDto(
                service.update(
                        mapper.toEntity(
                                dto
                        ),
                        id
                )
        );
    }

    protected List<D> listToDto(List<T> entidades) {
        List<D> dtos = new ArrayList<>();

        for (T entidad: entidades) {
            dtos.add(
                    mapper.toDto(entidad)
            );
        }

        return dtos;
    }
}
