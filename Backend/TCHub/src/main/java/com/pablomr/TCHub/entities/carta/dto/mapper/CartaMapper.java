package com.pablomr.TCHub.entities.carta.dto.mapper;

import com.pablomr.TCHub.entities.carta.dto.CartaDTO;
import com.pablomr.TCHub.entities.carta.dto.mapper.decorator.CartaDecorator;
import com.pablomr.TCHub.entities.carta.modelo.Carta;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(CartaDecorator.class)
public interface CartaMapper extends GenericMapper<Carta, CartaDTO> {

    @Override
    @Mapping(target = "expansionNombre", source = "entity.expansion.nombre")
    CartaDTO toDto(Carta entity);

    @Override
    @Mapping(target = "expansion", ignore = true)
    Carta toEntity(CartaDTO dto);
}
