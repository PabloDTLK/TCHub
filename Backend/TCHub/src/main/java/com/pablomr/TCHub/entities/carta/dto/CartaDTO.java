package com.pablomr.TCHub.entities.carta.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartaDTO {
    private String omniversalId;
    private String expansionNombre;
}
