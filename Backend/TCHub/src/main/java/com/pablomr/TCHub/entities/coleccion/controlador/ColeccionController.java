package com.pablomr.TCHub.entities.coleccion.controlador;

import com.pablomr.TCHub.entities.coleccion.dto.ColeccionConVariantesDTO;
import com.pablomr.TCHub.entities.coleccion.dto.ColeccionDTO;
import com.pablomr.TCHub.entities.coleccion.modelo.Coleccion;
import com.pablomr.TCHub.entities.coleccion.servicio.conDto.ColeccionDtoService;
import com.pablomr.TCHub.generico.controlador.GenericControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("coleccion")
public class ColeccionController extends GenericControllerImpl<Coleccion, Long, ColeccionDTO> {

    @Autowired
    private ColeccionDtoService coleccionDtoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("variantes/{id}")
    public ResponseEntity<ColeccionConVariantesDTO> findColeccion(@PathVariable Long id) {
        return new ResponseEntity<>(
                coleccionDtoService.findByIdDto(id),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("variantes/usuario")
    public ResponseEntity<List<ColeccionConVariantesDTO>> findColeccionesByUsuario() {
        return new ResponseEntity<>(
                coleccionDtoService.findByUsuarioToken(),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("variantes/usuario/{username}")
    public ResponseEntity<List<ColeccionConVariantesDTO>> findColeccionesByUsuario(@PathVariable String username) {
        return new ResponseEntity<>(
                coleccionDtoService.findByUsername(username),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("create")
    public ResponseEntity<ColeccionDTO> createColeccion(@RequestBody ColeccionDTO coleccionDTO) {
        return new ResponseEntity<>(
                coleccionDtoService.saveByToken(coleccionDTO),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("variantes")
    public ResponseEntity<ColeccionConVariantesDTO> createConVariantes(@RequestBody ColeccionConVariantesDTO coleccionConVariantesDTO) {
        return new ResponseEntity<>(
                coleccionDtoService.saveConVariantes(coleccionConVariantesDTO),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("variantes/{id}")
    public ResponseEntity<ColeccionConVariantesDTO> updateConVariantes(@PathVariable Long id, @RequestBody ColeccionConVariantesDTO coleccionConVariantesDTO) {
        return new ResponseEntity<>(
                coleccionDtoService.updateConVariantes(id, coleccionConVariantesDTO),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("variantes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coleccionDtoService.delete(id);
        return new ResponseEntity<>(
                HttpStatus.CREATED
        );
    }

}
