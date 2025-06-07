package com.pablomr.TCHub.entities.edicion.controlador;

import com.pablomr.TCHub.entities.edicion.dto.EdicionDTO;
import com.pablomr.TCHub.entities.edicion.dto.EdicionDetalleDTO;
import com.pablomr.TCHub.entities.edicion.dto.EdicionPreviewDTO;
import com.pablomr.TCHub.entities.edicion.modelo.Edicion;
import com.pablomr.TCHub.entities.edicion.servicio.conDto.EdicionDtoService;
import com.pablomr.TCHub.generico.controlador.GenericControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("edicion")
public class EdicionController extends GenericControllerImpl<Edicion, Long, EdicionDTO> {

    @Autowired
    private EdicionDtoService edicionDtoService;

    @GetMapping("/lista")
    public ResponseEntity<List<String>> getLista(@RequestParam Long longitud, @RequestParam Long pagina) {
        return new ResponseEntity<>(
                edicionDtoService.findListEdicion(longitud, pagina),
                HttpStatus.OK
        );
    }

    @GetMapping("/nombre/{idioma}")
    public ResponseEntity<List<String>> getListEdicioLikeNombre(@RequestParam String nombre, @PathVariable String idioma, @RequestParam Long longitud, @RequestParam Long pagina) {
        return new ResponseEntity<>(
                edicionDtoService.findListEdicioLikeNombre(nombre, idioma, longitud, pagina),
                HttpStatus.OK
        );
    }

    @GetMapping("/detalle/{multiversalId}")
    public ResponseEntity<EdicionDetalleDTO> getDetalleByMultiversalId(@PathVariable String multiversalId) {
        return new ResponseEntity<>(
                edicionDtoService.findDetalleByMultiversalId(multiversalId),
                HttpStatus.OK
        );
    }

    @GetMapping("/preview/{multiversalId}/idioma/{idiomaCodigo}")
    public ResponseEntity<EdicionPreviewDTO> getPreviewByMultiversalIdAndIdioma(@PathVariable String multiversalId, @PathVariable String idiomaCodigo) {
        return new ResponseEntity<>(
                edicionDtoService.findPreviewByMultiversalIdAndIdioma(multiversalId, idiomaCodigo),
                HttpStatus.OK
        );
    }
}
