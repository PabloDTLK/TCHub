package com.pablomr.TCHub.entities.rol.repositorio;

import com.pablomr.TCHub.entities.rol.modelo.Rol;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolReposiroty extends GenericRepository<Rol, Long> {

    @Query("SELECT r FROM Rol r WHERE r.codigo = ?1")
    Rol findByCodigo(String codigo);
}
