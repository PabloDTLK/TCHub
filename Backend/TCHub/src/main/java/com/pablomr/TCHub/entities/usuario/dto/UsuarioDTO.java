package com.pablomr.TCHub.entities.usuario.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {
    private String username;
    private String password;
}
