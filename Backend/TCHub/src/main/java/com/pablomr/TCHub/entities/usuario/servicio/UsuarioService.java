package com.pablomr.TCHub.entities.usuario.servicio;

import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.entities.usuario.repositorio.UsuarioRepository;
import com.pablomr.TCHub.exception.ValidacionException;
import com.pablomr.TCHub.exception.utils.ErrorConstants;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService extends GenericServiceImpl<Usuario, Long> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    public Usuario findByUsername(String username) {
        Optional<Usuario> optionalUsuario =  usuarioRepository.findByUsername(username);

        if (optionalUsuario.isEmpty())
            throw new ValidacionException(ErrorConstants.USUARIO_USERNAME_NOT_EXISTS, username);

        return optionalUsuario.get();
    }

    public List<Usuario> searchByUsername(String term) {
        return usuarioRepository.findByUsernameContainingIgnoreCase(term);
    }
}
