package com.pablomr.TCHub.generico.servicio;

import com.pablomr.TCHub.exception.ValidacionException;
import com.pablomr.TCHub.exception.utils.ErrorConstants;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class GenericServiceImpl<T extends IdentifiedEntity<ID>, ID> implements GenericService<T, ID> {

    @Autowired
    private GenericRepository<T, ID> repository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public T findById(ID id) {
        Optional<T> optionalEntity = repository.findById(id);

        if (optionalEntity.isEmpty())
            throw new ValidacionException((ErrorConstants.ENTITY_ID_NO_ENCONTRADO), id.toString());

        return optionalEntity.get();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public T update(T entity, ID id) {
        entity.setId(id);
        return repository.save(entity);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean deleteById(ID id) {
        boolean response = true;
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            response = false;
        }
        return response;
    }
}
