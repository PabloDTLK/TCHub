package com.pablomr.TCHub.entities.edicion.dto.mapper;

import com.pablomr.TCHub.entities.edicion.dto.EdicionDTO;
import com.pablomr.TCHub.entities.edicion.dto.mapper.decorator.EdicionMapperDecorator;
import com.pablomr.TCHub.entities.edicion.modelo.Edicion;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(EdicionMapperDecorator.class)
public interface EdicionMapper extends GenericMapper<Edicion, EdicionDTO> {

    @Override
    @Mapping(target = "cartaOmniversalId", source = "entity.carta.omniversalId")
    EdicionDTO toDto(Edicion entity);

    @Override
    @Mapping(target = "carta", ignore = true)
    Edicion toEntity(EdicionDTO dto);
}
