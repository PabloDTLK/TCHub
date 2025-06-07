package com.pablomr.TCHub.entities.expansion.servicio.conDto;

import com.pablomr.TCHub.entities.expansion.dto.ExpansionDTO;
import com.pablomr.TCHub.entities.expansion.dto.mapper.ExpansionMapper;
import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.entities.expansion.servicio.ExpansionService;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpansionDtoService extends GenericDtoServiceImpl<Expansion, Long, ExpansionDTO> {

    @Autowired
    private ExpansionService expansionService;

    @Autowired
    private ExpansionMapper expansionMapper;

    public ExpansionDTO findByCodigo(String codigo) {
        return expansionMapper.toDto(
                expansionService.findByCodigo(codigo)
        );
    }

    public Boolean existsByCodigo(String codigo) {
        return expansionService.existsByCodigo(codigo);
    }

}
