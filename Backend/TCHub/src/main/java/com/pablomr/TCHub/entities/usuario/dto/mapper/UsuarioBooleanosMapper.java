package com.pablomr.TCHub.entities.usuario.dto.mapper;

import com.pablomr.TCHub.entities.usuario.dto.UsuarioBooleanosDTO;
import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioBooleanosMapper {

    @Mapping(target = "isAdmin", expression = "java(usuario.getRol().getCodigo().equals(\"ADMIN\"))")
    UsuarioBooleanosDTO toDto(Usuario usuario);
}
