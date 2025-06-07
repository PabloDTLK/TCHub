package com.pablomr.TCHub.entities.juego.dto;

import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class JuegoDTO {
    @NotNull
    private String nombre;
    @NotNull
    private String codigo;
    private String logo;
    private Set<String> expansiones;
}
