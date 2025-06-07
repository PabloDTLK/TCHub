package com.pablomr.TCHub.entities.usuario.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioBooleanosDTO {
    @NotNull
    private String username;
    private boolean isAdmin;
    private boolean activo;
}
