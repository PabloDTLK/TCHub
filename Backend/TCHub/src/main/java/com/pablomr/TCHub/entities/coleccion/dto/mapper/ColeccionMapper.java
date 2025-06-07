package com.pablomr.TCHub.entities.coleccion.dto.mapper;

import com.pablomr.TCHub.entities.coleccion.dto.ColeccionDTO;
import com.pablomr.TCHub.entities.coleccion.modelo.Coleccion;
import com.pablomr.TCHub.entities.coleccion.servicio.ColeccionService;
import com.pablomr.TCHub.generico.dto.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ColeccionMapper extends GenericMapper<Coleccion, ColeccionDTO> {
    @Override
    ColeccionDTO toDto(Coleccion entity);

    @Override
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "coleccionVariantes", ignore = true)
    Coleccion toEntity(ColeccionDTO dto);
}
