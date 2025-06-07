package com.pablomr.TCHub.entities.edicion.repositorio;

import com.pablomr.TCHub.entities.edicion.modelo.Edicion;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdicionRepository extends GenericRepository<Edicion, Long> {

    @Query("""
           SELECT e FROM Edicion e
            WHERE e.multiversalId = ?1
    """)
    Edicion findByMultiversalId(String multiversalId);

    @Query("""
           SELECT count(e) > 0 FROM Edicion e
            WHERE e.multiversalId = ?1
    """)
    Boolean existsByMultiversalId(String multiversalId);

    @Query("SELECT e.multiversalId FROM Edicion e")
    List<String> findListEdicion(Pageable pageable);

    @Query("""
        SELECT v.edicion.multiversalId FROM Variante v
            WHERE v.nombre LIKE %:nombre%
            AND v.idioma.codigo = :codigo
    """)
    List<String> findListEdicionLikeNombre(String nombre, String codigo, Pageable pageable);

}
