package com.pablomr.TCHub.entities.usuario.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioNombreDTO {
    @NotNull
    private String username;
}
