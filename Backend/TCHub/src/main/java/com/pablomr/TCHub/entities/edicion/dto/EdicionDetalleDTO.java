package com.pablomr.TCHub.entities.edicion.dto;

import com.pablomr.TCHub.entities.variante.dto.VarianteDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class EdicionDetalleDTO {

    private LocalDate fechaLanzamiento;
    private String autor;
    private String multiversalId;
    private String cartaOmniversalId;
    private String juegoCodigo;
    private Set<VarianteDTO> variantes;

}
