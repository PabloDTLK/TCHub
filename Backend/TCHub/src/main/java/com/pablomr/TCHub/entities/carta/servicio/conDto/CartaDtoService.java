package com.pablomr.TCHub.entities.carta.servicio.conDto;

import com.pablomr.TCHub.entities.carta.dto.CartaDTO;
import com.pablomr.TCHub.entities.carta.modelo.Carta;
import com.pablomr.TCHub.entities.carta.servicio.CartaService;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaDtoService extends GenericDtoServiceImpl<Carta, Long, CartaDTO> {

    @Autowired
    private CartaService cartaService;

    public Boolean existsByOmniversalId(String omniversalId) {
        return cartaService.existsByOmniversalId(omniversalId);
    }
}
