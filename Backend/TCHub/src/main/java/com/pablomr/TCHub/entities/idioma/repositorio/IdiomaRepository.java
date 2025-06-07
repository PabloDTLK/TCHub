package com.pablomr.TCHub.entities.idioma.repositorio;

import com.pablomr.TCHub.entities.idioma.modelo.Idioma;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IdiomaRepository extends GenericRepository<Idioma, Long> {

    @Query("""
            SELECT i FROM Idioma i
                WHERE i.codigo = ?1
    """)
    Idioma findByCodigo(String codigo);

    @Query("""
            SELECT i FROM Idioma i
                WHERE i.valor = ?1
    """)
    Idioma findByValor(String valor);
}
