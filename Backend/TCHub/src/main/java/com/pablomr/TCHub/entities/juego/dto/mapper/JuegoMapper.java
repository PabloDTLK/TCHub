package com.pablomr.TCHub.entities.juego.dto.mapper;

import com.pablomr.TCHub.entities.juego.dto.JuegoDTO;
import com.pablomr.TCHub.entities.juego.dto.mapper.decorator.JuegoMapperDecorator;
import com.pablomr.TCHub.entities.juego.modelo.Juego;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(JuegoMapperDecorator.class)
public interface JuegoMapper extends GenericMapper<Juego, JuegoDTO> {

    @Override
    @Mapping(target = "expansiones", ignore = true)
    JuegoDTO toDto(Juego juego);

    @Override
    @Mapping(target = "expansiones", ignore = true)
    Juego toEntity(JuegoDTO juegoDTO);
}
