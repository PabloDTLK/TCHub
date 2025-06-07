package com.pablomr.TCHub.entities.variante.dto.mapper.decorator;

import com.pablomr.TCHub.entities.edicion.servicio.EdicionService;
import com.pablomr.TCHub.entities.idioma.servicio.IdiomaService;
import com.pablomr.TCHub.entities.variante.dto.VarianteDTO;
import com.pablomr.TCHub.entities.variante.dto.mapper.VarianteMapper;
import com.pablomr.TCHub.entities.variante.modelo.Variante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class VarianteMapperDecorator implements VarianteMapper {

    @Autowired
    private VarianteMapper varianteMapper;

    @Autowired
    private IdiomaService idiomaService;

    @Autowired
    private EdicionService edicionService;

    @Override
    public Variante toEntity(VarianteDTO dto) {
        Variante variante = varianteMapper.toEntity(dto);

        variante.setIdioma(
                idiomaService.findByCodigo(dto.getIdiomaCodigo())
        );

        variante.setEdicion(
                edicionService.findByMultiversalId(dto.getEdicionMultiversalId())
        );

        return variante;
    }
}
