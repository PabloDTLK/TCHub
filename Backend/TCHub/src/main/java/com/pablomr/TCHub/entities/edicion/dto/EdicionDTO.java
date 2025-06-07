package com.pablomr.TCHub.entities.edicion.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EdicionDTO {

    private LocalDate fechaLanzamiento;
    private String autor;
    private String multiversalId;
    private String cartaOmniversalId;
}
