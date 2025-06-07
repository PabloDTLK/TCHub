package com.pablomr.TCHub.entities.coleccion.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColeccionDTO {
    private Long id;
    private String nombre;
    private String descripcion;
}
