package com.pablomr.TCHub.entities.juego.repositorio;

import com.pablomr.TCHub.entities.juego.modelo.Juego;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JuegoRepository extends GenericRepository<Juego, Long>{

    @Query("""
        SELECT j FROM Juego j
            WHERE j.codigo = :codigo
    """)
    Juego findByCodigo(String codigo);

}
