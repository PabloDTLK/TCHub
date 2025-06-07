package com.pablomr.TCHub.entities.expansion.controlador;

import com.pablomr.TCHub.entities.expansion.dto.ExpansionDTO;
import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.entities.expansion.servicio.conDto.ExpansionDtoService;
import com.pablomr.TCHub.generico.controlador.GenericControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("expansion")
public class ExpansionController extends GenericControllerImpl<Expansion, Long, ExpansionDTO> {

    @Autowired
    private ExpansionDtoService expansionDtoService;

    @GetMapping("codigo/{codigo}")
    public ResponseEntity<ExpansionDTO> getByCodigo(@PathVariable String codigo) {
        return new ResponseEntity<>(
                expansionDtoService.findByCodigo(codigo),
                HttpStatus.OK
        );
    }

    @GetMapping("exists/{codigo}")
    public ResponseEntity<Boolean> existsByCodigo(@PathVariable String codigo) {
        return new ResponseEntity<>(
                expansionDtoService.existsByCodigo(codigo),
                HttpStatus.OK
        );
    }

}
