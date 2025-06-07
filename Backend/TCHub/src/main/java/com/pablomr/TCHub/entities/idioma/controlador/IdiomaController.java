package com.pablomr.TCHub.entities.idioma.controlador;

import com.pablomr.TCHub.entities.idioma.dto.IdiomaDTO;
import com.pablomr.TCHub.entities.idioma.modelo.Idioma;
import com.pablomr.TCHub.entities.idioma.servicio.conDto.IdiomaDtoService;
import com.pablomr.TCHub.generico.controlador.GenericControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idioma")
public class IdiomaController extends GenericControllerImpl<Idioma, Long, IdiomaDTO> {

    @Autowired
    private IdiomaDtoService idiomaDtoService;

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<IdiomaDTO>  getByCodigo(@PathVariable String codigo) {
        return new ResponseEntity<>(
                idiomaDtoService.findByCodigo(codigo),
                HttpStatus.OK
        );
    }

    @GetMapping("/valor/{valor}")
    public ResponseEntity<IdiomaDTO>  getByValor(@PathVariable String valor) {
        return new ResponseEntity<>(
                idiomaDtoService.findByValor(valor),
                HttpStatus.OK
        );
    }
}
