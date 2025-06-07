package com.pablomr.TCHub.entities.usuario.dto.mapper;


import com.pablomr.TCHub.entities.usuario.dto.UsuarioNombreDTO;
import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioNombreMapper extends GenericMapper<Usuario, UsuarioNombreDTO> {
}
