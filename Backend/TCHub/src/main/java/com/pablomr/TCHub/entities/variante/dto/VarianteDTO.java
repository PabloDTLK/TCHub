package com.pablomr.TCHub.entities.variante.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VarianteDTO {

    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precioMedio;
    private String universalId;

    private String edicionMultiversalId;
    private String idiomaCodigo;

}
