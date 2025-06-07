package com.pablomr.TCHub.entities.carta.dto.mapper.decorator;

import com.pablomr.TCHub.entities.carta.dto.CartaDTO;
import com.pablomr.TCHub.entities.carta.dto.mapper.CartaMapper;
import com.pablomr.TCHub.entities.carta.modelo.Carta;
import com.pablomr.TCHub.entities.expansion.servicio.ExpansionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class CartaDecorator implements CartaMapper {

    @Autowired
    private CartaMapper cartaMapper;

    @Autowired
    private ExpansionService expansionService;

    @Override
    public Carta toEntity(CartaDTO dto) {
        Carta carta = cartaMapper.toEntity(dto);

        carta.setExpansion(
            expansionService.findByNombre(
                    dto.getExpansionNombre()
            )
        );

        return carta;
    }
}
