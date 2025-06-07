package com.pablomr.TCHub.entities.expansion.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExpansionDTO {

    private String codigo;
    private String nombre;
    private LocalDate fechaLanzamiento;
    private String logo;

    private String juegoCodigo;
}
