package com.pablomr.TCHub.entities.juego.servicio;

import com.pablomr.TCHub.entities.juego.dto.JuegoDTO;
import com.pablomr.TCHub.entities.juego.modelo.Juego;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class JuegoDtoService extends GenericDtoServiceImpl<Juego, Long, JuegoDTO> {

}
