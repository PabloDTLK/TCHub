package com.pablomr.TCHub.entities.carta.controlador;

import com.pablomr.TCHub.entities.carta.dto.CartaDTO;
import com.pablomr.TCHub.entities.carta.modelo.Carta;
import com.pablomr.TCHub.entities.carta.servicio.conDto.CartaDtoService;
import com.pablomr.TCHub.entities.idioma.dto.IdiomaDTO;
import com.pablomr.TCHub.generico.controlador.GenericControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carta")
public class CartaController extends GenericControllerImpl<Carta, Long, CartaDTO> {

    @Autowired
    private CartaDtoService cartaDtoService;

    @GetMapping("/exists/{omniversalId}")
    public ResponseEntity<Boolean> existsByOmniversalId(@PathVariable String omniversalId) {
        return new ResponseEntity<>(
                cartaDtoService.existsByOmniversalId(omniversalId),
                HttpStatus.OK
        );
    }
}
