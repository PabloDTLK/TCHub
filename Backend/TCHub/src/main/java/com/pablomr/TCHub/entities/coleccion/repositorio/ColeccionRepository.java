package com.pablomr.TCHub.entities.coleccion.repositorio;

import com.pablomr.TCHub.entities.coleccion.modelo.Coleccion;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColeccionRepository extends GenericRepository<Coleccion, Long> {

    @Query("""
        SELECT COUNT(c)>0 FROM Coleccion c
            WHERE c.nombre = :coleccionNombre
            AND c.usuario.username = :username
    """)
    Boolean existsColeccionNombreByUsuario(String username, String coleccionNombre);

    @Query("""
        SELECT c FROM Coleccion c
            WHERE c.nombre = :coleccionNombre
            AND c.usuario.username = :username
    """)
    Coleccion findColeccionNombreByUsuario(String username, String coleccionNombre);

    @Query("""
        SELECT c FROM Coleccion c
            WHERE c.usuario.username = :username
    """)
    List<Coleccion> findColeccionByUsuario(String username);

    @Query("SELECT c FROM Coleccion c LEFT JOIN FETCH c.coleccionVariantes cv LEFT JOIN FETCH cv.variante WHERE c.id = :id")
    Optional<Coleccion> findByIdWithVariantes(@Param("id") Long id);

}
