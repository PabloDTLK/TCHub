package com.pablomr.TCHub.entities.carta.servicio;

import com.pablomr.TCHub.entities.carta.modelo.Carta;
import com.pablomr.TCHub.entities.carta.repositorio.CartaRepository;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaService extends GenericServiceImpl<Carta, Long> {

    @Autowired
    private CartaRepository cartaRepository;

    public Carta findByOmniversalId(String omniversalId) {
        return cartaRepository.findByOmniversalId(omniversalId);
    }

    public Boolean existsByOmniversalId(String omniversalId) {
        return cartaRepository.existsByOmniversalId(omniversalId);
    }

}
