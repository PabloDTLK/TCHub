package com.pablomr.TCHub.entities.juego.dto.mapper.decorator;

import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.entities.expansion.servicio.ExpansionService;
import com.pablomr.TCHub.entities.juego.dto.JuegoDTO;
import com.pablomr.TCHub.entities.juego.dto.mapper.JuegoMapper;
import com.pablomr.TCHub.entities.juego.modelo.Juego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public abstract class JuegoMapperDecorator implements JuegoMapper {

    @Autowired
    private  JuegoMapper juegoMapper;

    @Autowired
    private ExpansionService expansionService;

    @Override
    public JuegoDTO toDto(Juego juego) {
        JuegoDTO juegoDTO = juegoMapper.toDto(juego);

        Set<String> expansionesNombre = new HashSet<>();

        if (juego.getExpansiones() != null) {
            for (Expansion expansion: juego.getExpansiones()) {
                expansionesNombre.add(
                        expansion.getNombre()
                );
            }

            juegoDTO.setExpansiones(expansionesNombre);

        }

        return juegoDTO;
    }

    @Override
    public Juego toEntity(JuegoDTO juegoDTO) {
        Juego juego = juegoMapper.toEntity(juegoDTO);

        if (juego.getExpansiones() != null) {
            juego.setExpansiones(
                    expansionService.findAllByNombreAndJuego(juegoDTO.getExpansiones(), juego.getId())
            );
        }

        return juego;
    }
}
