package com.pablomr.TCHub.entities.coleccion.dto;

import com.pablomr.TCHub.entities.coleccionVariante.dto.ColeccionVariantesListDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ColeccionConVariantesDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private List<ColeccionVariantesListDTO> listaVariantes;
}
