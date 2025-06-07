package com.pablomr.TCHub.entities.juego.servicio;

import com.pablomr.TCHub.entities.juego.modelo.Juego;
import com.pablomr.TCHub.entities.juego.repositorio.JuegoRepository;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JuegoService extends GenericServiceImpl<Juego, Long> {

    @Autowired
    private JuegoRepository juegoRepository;

    public Juego findByCodigo(String codigo) {
        return juegoRepository.findByCodigo(codigo);
    }

}
