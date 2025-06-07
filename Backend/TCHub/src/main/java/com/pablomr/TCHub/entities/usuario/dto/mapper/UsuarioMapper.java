package com.pablomr.TCHub.entities.usuario.dto.mapper;

import com.pablomr.TCHub.entities.usuario.dto.UsuarioDTO;
import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends GenericMapper<Usuario, UsuarioDTO> {

    @Override
    UsuarioDTO toDto(Usuario entity);

    @Override
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "colecciones", ignore = true)
    Usuario toEntity(UsuarioDTO dto);
}
