package com.pablomr.TCHub.entities.variante.dto.mapper;

import com.pablomr.TCHub.entities.variante.dto.VarianteDTO;
import com.pablomr.TCHub.entities.variante.dto.mapper.decorator.VarianteMapperDecorator;
import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(VarianteMapperDecorator.class)
public interface VarianteMapper extends GenericMapper<Variante, VarianteDTO> {

    @Override
    @Mapping(target = "edicionMultiversalId", source = "entity.edicion.multiversalId")
    @Mapping(target = "idiomaCodigo", source = "entity.idioma.codigo")
    VarianteDTO toDto(Variante entity);

    @Override
    @Mapping(target = "edicion", ignore = true)
    @Mapping(target = "idioma", ignore = true)
    Variante toEntity(VarianteDTO dto);
}
