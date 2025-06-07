package com.pablomr.TCHub.entities.juego.controlador;

import com.pablomr.TCHub.entities.juego.dto.JuegoDTO;
import com.pablomr.TCHub.entities.juego.modelo.Juego;
import com.pablomr.TCHub.generico.controlador.GenericControllerImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/juego")
public class JuegoController extends GenericControllerImpl<Juego, Long, JuegoDTO> {


}
