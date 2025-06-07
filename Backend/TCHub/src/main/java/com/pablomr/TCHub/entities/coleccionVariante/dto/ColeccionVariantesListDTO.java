package com.pablomr.TCHub.entities.coleccionVariante.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColeccionVariantesListDTO {
    private String varianteUniversalId;
    private Long cantidad;
}
