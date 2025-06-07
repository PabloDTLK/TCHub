package com.pablomr.TCHub.entities.expansion.dto.mapper;

import com.pablomr.TCHub.entities.expansion.dto.ExpansionDTO;
import com.pablomr.TCHub.entities.expansion.dto.mapper.decorator.ExpansionMapperDecorator;
import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(ExpansionMapperDecorator.class)
public interface ExpansionMapper extends GenericMapper<Expansion, ExpansionDTO> {

    @Override
    @Mapping(target = "juegoCodigo", source = "entity.juego.codigo")
    ExpansionDTO toDto(Expansion entity);

    @Override
    @Mapping(target = "juego", ignore = true)
    Expansion toEntity(ExpansionDTO dto);
}
