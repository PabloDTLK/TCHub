package com.pablomr.TCHub.entities.edicion.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EdicionPreviewDTO {

    private String nombre;
    private LocalDate fechaLanzamiento;
    private String autor;
    private String multiversalId;
    private Double precio;

    private String juegoCodigo;
    private String varianteImagen;

}
