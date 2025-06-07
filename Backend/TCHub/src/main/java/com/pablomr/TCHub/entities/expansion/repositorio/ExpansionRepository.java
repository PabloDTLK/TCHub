package com.pablomr.TCHub.entities.expansion.repositorio;

import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpansionRepository extends GenericRepository<Expansion, Long> {

    @Query("""
        SELECT e FROM Expansion e
        WHERE e.nombre = :nombre
        AND e.juego.id = :juego
    """)
    Expansion findByNombreAndJuego(String nombre, Long juego);

    @Query("""
        SELECT e FROM Expansion e
        WHERE e.nombre = :nombre
    """)
    Expansion findByNombre(String nombre);

    @Query("""
        SELECT count(e) > 0 FROM Expansion e
            WHERE e.codigo = :codigo
    """)
    Boolean existsByCodigo(String codigo);

    @Query("""
        SELECT e FROM Expansion e
            WHERE e.codigo = :codigo
    """)
    Expansion findByCodigo(String codigo);

}
