package com.pablomr.TCHub.entities.usuario.repositorio;

import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.generico.repositorio.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);

    List<Usuario> findByUsernameContainingIgnoreCase(String username);

}
