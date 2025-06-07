package com.pablomr.TCHub.entities.variante.controlador;

import com.pablomr.TCHub.entities.variante.dto.VarianteDTO;
import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.entities.variante.servicio.conDto.VarianteDtoService;
import com.pablomr.TCHub.generico.controlador.GenericControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("variante")
public class VarianteController extends GenericControllerImpl<Variante, Long, VarianteDTO> {

    @Autowired
    private VarianteDtoService varianteDtoService;

    @GetMapping("universalId/{universalId}")
    public ResponseEntity<VarianteDTO> getByUniversalId(@PathVariable String universalId) {
        return new ResponseEntity<>(
                varianteDtoService.findVarianteByUniversalId(universalId),
                HttpStatus.OK
        );
    }
}
