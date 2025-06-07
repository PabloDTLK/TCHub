package com.pablomr.TCHub.entities.edicion.dto.mapper.decorator;

import com.pablomr.TCHub.entities.carta.servicio.CartaService;
import com.pablomr.TCHub.entities.edicion.dto.EdicionDTO;
import com.pablomr.TCHub.entities.edicion.dto.mapper.EdicionMapper;
import com.pablomr.TCHub.entities.edicion.modelo.Edicion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class EdicionMapperDecorator implements EdicionMapper {

    @Autowired
    private EdicionMapper edicionMapper;

    @Autowired
    private CartaService cartaService;

    @Override
    public Edicion toEntity(EdicionDTO dto) {
        Edicion edicion = edicionMapper.toEntity(dto);

        edicion.setCarta(
                cartaService.findByOmniversalId(dto.getCartaOmniversalId())
        );

        return edicion;
    }
}
