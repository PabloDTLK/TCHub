package com.pablomr.TCHub.entities.variante.repositorio;

import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VarianteRepository extends GenericRepository<Variante, Long> {

    @Query("""
        SELECT v FROM Variante v WHERE v.universalId = ?1
    """)
    Variante findByUniversalId(String universalId);

    @Query("""
        SELECT count(v)>0 FROM Variante v WHERE v.universalId = ?1
    """)
    Boolean existsByUniversalId(String universalId);

}
