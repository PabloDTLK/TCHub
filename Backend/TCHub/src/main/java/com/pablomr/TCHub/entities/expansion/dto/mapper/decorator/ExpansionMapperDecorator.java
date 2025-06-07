package com.pablomr.TCHub.entities.expansion.dto.mapper.decorator;


import com.pablomr.TCHub.entities.expansion.dto.ExpansionDTO;
import com.pablomr.TCHub.entities.expansion.dto.mapper.ExpansionMapper;
import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.entities.juego.servicio.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class ExpansionMapperDecorator implements ExpansionMapper {

    @Autowired
    private ExpansionMapper expansionMapper;

    @Autowired
    private JuegoService juegoService;

    @Override
    public Expansion toEntity(ExpansionDTO expansionDTO) {
        Expansion expansion = expansionMapper.toEntity(expansionDTO);

        expansion.setJuego(
                juegoService.findByCodigo(expansionDTO.getJuegoCodigo())
        );

        return expansion;
    }
    
}
