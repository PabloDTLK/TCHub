package com.pablomr.TCHub.entities.coleccionVariante.repositorio;

import com.pablomr.TCHub.entities.coleccionVariante.modelo.ColeccionVariante;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ColeccionVarianteRepository extends GenericRepository<ColeccionVariante, Long> {

    @Query("SELECT cv FROM ColeccionVariante cv WHERE cv.coleccion.id = ?1")
    Set<ColeccionVariante> findAllByColeccion(Long coleccionId);

    @Query("""
        SELECT cv FROM ColeccionVariante cv
            WHERE cv.coleccion.id = :coleccionId
            AND cv.variante.universalId = :universalId
    """)
    ColeccionVariante findByColeccionYVariante(Long coleccionId, String universalId);

    @Query("""
        SELECT count(cv)>0 FROM ColeccionVariante cv
            WHERE cv.coleccion.id = :coleccionId
            AND cv.variante.universalId = :universalId
    """)
    Boolean existsByColeccionYVariante(Long coleccionId, String universalId);
}
