package com.pablomr.TCHub.entities.carta.repositorio;


import com.pablomr.TCHub.entities.carta.modelo.Carta;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaRepository extends GenericRepository<Carta, Long> {

    @Query("""
           SELECT c FROM Carta c
            WHERE c.omniversalId = ?1
    """)
    Carta findByOmniversalId(String omniversalId);

    @Query("""
           SELECT count(c) > 0 FROM Carta c
            WHERE c.omniversalId = ?1
    """)
    Boolean existsByOmniversalId(String omniversalId);

}
