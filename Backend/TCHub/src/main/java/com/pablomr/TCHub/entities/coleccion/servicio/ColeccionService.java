package com.pablomr.TCHub.entities.coleccion.servicio;


import com.pablomr.TCHub.entities.coleccion.modelo.Coleccion;
import com.pablomr.TCHub.entities.coleccion.repositorio.ColeccionRepository;
import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.entities.usuario.servicio.UsuarioService;
import com.pablomr.TCHub.exception.ValidacionException;
import com.pablomr.TCHub.exception.utils.ErrorConstants;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColeccionService extends GenericServiceImpl<Coleccion, Long> {

    @Autowired
    private ColeccionRepository coleccionRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Coleccion> findByToken() {
        String tokenUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        return findByUsername(tokenUsername);
    }

    public List<Coleccion> findByUsername(String username) {

        List<Coleccion> lista = coleccionRepository.findColeccionByUsuario(username);

        if (lista.isEmpty())
            throw new ValidacionException(ErrorConstants.COLECCION_USUARIO_NONE, username);

        return lista;
    }

    public Coleccion saveByToken(Coleccion coleccion) {
        String tokenUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (coleccionRepository.existsColeccionNombreByUsuario(tokenUsername, coleccion.getNombre()))
            throw new ValidacionException(ErrorConstants.COLECCION_NOMBRE_USUARIO_EXISTS, coleccion.getNombre());

        Usuario usuario = usuarioService.findByUsername(tokenUsername);

        coleccion.setUsuario(usuario);
        return super.save(coleccion);
    }

    public Coleccion findColeccionNombreByUsuario(String username, String coleccionNombre) {
        return coleccionRepository.findColeccionNombreByUsuario(username, coleccionNombre);
    }

    public Coleccion findByIdWithVariantes(Long id) {
        Optional<Coleccion> optionalColeccion = coleccionRepository.findByIdWithVariantes(id);

        if (optionalColeccion.isEmpty())
            throw new ValidacionException(ErrorConstants.ENTITY_ID_NO_ENCONTRADO, id.toString());

        return optionalColeccion.get();
    }

    public Coleccion updateByToken(Coleccion coleccion, Long id) {

        return super.save(coleccion);
    }

}
