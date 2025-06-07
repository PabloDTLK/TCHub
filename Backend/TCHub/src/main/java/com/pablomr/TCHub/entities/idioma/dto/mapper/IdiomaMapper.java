package com.pablomr.TCHub.entities.idioma.dto.mapper;

import com.pablomr.TCHub.entities.idioma.dto.IdiomaDTO;
import com.pablomr.TCHub.entities.idioma.modelo.Idioma;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IdiomaMapper extends GenericMapper<Idioma, IdiomaDTO> {

    @Override
    IdiomaDTO toDto(Idioma entity);

    @Override
    Idioma toEntity(IdiomaDTO dto);
}
