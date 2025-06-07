package com.pablomr.TCHub.entities.edicion.servicio.conDto;


import com.pablomr.TCHub.entities.edicion.dto.EdicionDTO;
import com.pablomr.TCHub.entities.edicion.dto.EdicionDetalleDTO;
import com.pablomr.TCHub.entities.edicion.dto.EdicionPreviewDTO;
import com.pablomr.TCHub.entities.edicion.modelo.Edicion;
import com.pablomr.TCHub.entities.edicion.servicio.EdicionService;
import com.pablomr.TCHub.entities.variante.dto.mapper.VarianteMapper;
import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.exception.ValidacionException;
import com.pablomr.TCHub.exception.utils.ErrorConstants;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EdicionDtoService extends GenericDtoServiceImpl<Edicion, Long, EdicionDTO> {

    @Autowired
    private EdicionService edicionService;

    @Autowired
    private VarianteMapper varianteMapper;

    public List<String> findListEdicion(Long longitud, Long pagina) {
        return edicionService.findListEdicion(longitud, pagina);
    }

    public List<String> findListEdicioLikeNombre(String nombre, String codigo, Long longitud, Long pagina) {
        return edicionService.findListEdicioLikeNombre(nombre, codigo, longitud, pagina);
    }

    public EdicionDetalleDTO findDetalleByMultiversalId(String multiversalId) {
        if (!edicionService.existsByMultiversalId(multiversalId))
            throw new ValidacionException(ErrorConstants.EDICION_MULTIVERSAL_ID_NOT_FOUND, multiversalId);

        Edicion edicion = edicionService.findByMultiversalId(multiversalId);

        return EdicionDetalleDTO.builder()
                .fechaLanzamiento(edicion.getFechaLanzamiento())
                .autor(edicion.getAutor())
                .multiversalId(edicion.getMultiversalId())
                .cartaOmniversalId(edicion.getCarta().getOmniversalId())
                .juegoCodigo(edicion.getCarta().getExpansion().getJuego().getCodigo())
                .variantes(

                        edicion.getVariantes().stream().map(variante -> {
                                    return varianteMapper.toDto(variante);
                                })
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public EdicionPreviewDTO findPreviewByMultiversalIdAndIdioma(String multiversalId, String idiomaCodigo) {
        if (!edicionService.existsByMultiversalId(multiversalId))
            throw new ValidacionException(ErrorConstants.EDICION_MULTIVERSAL_ID_NOT_FOUND, multiversalId);

        Edicion edicion = edicionService.findByMultiversalId(multiversalId);


        Variante varianteIdioma = edicion.getVariantes().stream()
                .filter(variante -> Objects.equals(
                        variante.getIdioma().getCodigo(),
                        (
                                existsIdiomaInSet(edicion.getVariantes(), idiomaCodigo)
                                        ? idiomaCodigo
                                        : "EN"
                        )
                ))
                .findFirst()
                .get();

        return EdicionPreviewDTO.builder()
                .nombre(varianteIdioma.getNombre())
                .fechaLanzamiento(edicion.getFechaLanzamiento())
                .autor(edicion.getAutor())
                .multiversalId(edicion.getMultiversalId())
                .precio(varianteIdioma.getPrecioMedio())

                .juegoCodigo(edicion.getCarta().getExpansion().getJuego().getCodigo())
                .varianteImagen(varianteIdioma.getImagen() )
                .build();
    }

    private Boolean existsIdiomaInSet(Set<Variante> variantes, String idiomaCodigo) {
        return variantes.stream().anyMatch((variante ->
                Objects.equals(
                        variante.getIdioma().getCodigo(),
                        idiomaCodigo
                ))
        );
    }
}
