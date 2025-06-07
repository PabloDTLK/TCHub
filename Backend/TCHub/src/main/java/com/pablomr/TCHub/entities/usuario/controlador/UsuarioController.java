package com.pablomr.TCHub.entities.usuario.controlador;

import com.pablomr.TCHub.entities.usuario.dto.UsuarioBooleanosDTO;
import com.pablomr.TCHub.entities.usuario.dto.UsuarioDTO;
import com.pablomr.TCHub.entities.usuario.dto.UsuarioNombreDTO;
import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.entities.usuario.servicio.conDto.UsuarioDtoService;
import com.pablomr.TCHub.generico.controlador.GenericControllerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioController extends GenericControllerImpl<Usuario, Long, UsuarioDTO> {

    @Autowired
    private UsuarioDtoService usuarioDtoService;

    @GetMapping("usernames")
    public ResponseEntity<List<UsuarioNombreDTO>> getUsernames() {
        return new ResponseEntity<>(
                usuarioDtoService.findUsernames(),
                HttpStatus.OK
        );
    }

    @PostMapping("registrar")
    public ResponseEntity<UsuarioNombreDTO> registrar(@RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(
                usuarioDtoService.registrar(usuarioDTO),
                HttpStatus.CREATED
        );
    }


    @PutMapping("status")
    public ResponseEntity<UsuarioBooleanosDTO> updateUserStatus(@RequestBody UsuarioBooleanosDTO usuarioBooleanosDTO) {
        return new ResponseEntity<>(
                usuarioDtoService.updateUserStatus(usuarioBooleanosDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<UsuarioBooleanosDTO>> searchUsernames(@RequestParam("term") String term) {
        return new ResponseEntity<>(
                usuarioDtoService.searchUsernames(term),
                HttpStatus.OK
        );
    }
}
