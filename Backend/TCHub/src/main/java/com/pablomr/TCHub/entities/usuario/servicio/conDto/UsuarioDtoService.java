package com.pablomr.TCHub.entities.usuario.servicio.conDto;

import com.pablomr.TCHub.entities.rol.servicio.RolService;
import com.pablomr.TCHub.entities.usuario.dto.UsuarioBooleanosDTO;
import com.pablomr.TCHub.entities.usuario.dto.UsuarioDTO;
import com.pablomr.TCHub.entities.usuario.dto.UsuarioNombreDTO;
import com.pablomr.TCHub.entities.usuario.dto.mapper.UsuarioBooleanosMapper;
import com.pablomr.TCHub.entities.usuario.dto.mapper.UsuarioMapper;
import com.pablomr.TCHub.entities.usuario.dto.mapper.UsuarioNombreMapper;
import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.entities.usuario.servicio.UsuarioService;
import com.pablomr.TCHub.exception.ValidacionException;
import com.pablomr.TCHub.exception.utils.ErrorConstants;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UsuarioDtoService extends GenericDtoServiceImpl<Usuario, Long, UsuarioDTO> {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private UsuarioNombreMapper usuarioNombreMapper;
    @Autowired
    private UsuarioBooleanosMapper usuarioBooleanosMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public ArrayList<UsuarioNombreDTO> findUsernames(){
        return usuarioService.findAll().stream().map((Usuario usuario) ->{
            return usuarioNombreMapper.toDto(usuario);
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public UsuarioNombreDTO registrar(UsuarioDTO usuarioDTO) {

        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

        if (usuarioService.existsByUsername(usuarioDTO.getUsername()))
            throw new ValidacionException((ErrorConstants.USUARIO_USERNAME_EXISTS), usuario.getUsername());

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(rolService.findByCodigo("USER"));
        usuario.setActivo(true);

        return UsuarioNombreDTO.builder()
                .username(super.save(usuario).getUsername())
                .build();
    }

    @Transactional
    public UsuarioBooleanosDTO updateUserStatus(UsuarioBooleanosDTO usuarioBooleanosDTO) {
        Usuario usuario = usuarioService.findByUsername(usuarioBooleanosDTO.getUsername());

        usuario.setActivo(usuarioBooleanosDTO.isActivo());

        if(usuarioBooleanosDTO.isAdmin()) {
            usuario.setRol(rolService.findByCodigo("ADMIN"));
        } else {
            usuario.setRol(rolService.findByCodigo("USER"));
        }

        Usuario usuarioActualizado = usuarioService.update(usuario, usuario.getId());
        return usuarioBooleanosMapper.toDto(usuarioActualizado);
    }

    @Transactional(readOnly = true)
    public ArrayList<UsuarioBooleanosDTO> searchUsernames(String term) {
        return usuarioService.searchByUsername(term).stream()
                .map(usuarioBooleanosMapper::toDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}