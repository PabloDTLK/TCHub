package com.pablomr.TCHub.entities.variante.servicio.conDto;

import com.pablomr.TCHub.entities.variante.dto.VarianteDTO;
import com.pablomr.TCHub.entities.variante.dto.mapper.VarianteMapper;
import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.entities.variante.servicio.VarianteService;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VarianteDtoService extends GenericDtoServiceImpl<Variante, Long, VarianteDTO> {

    @Autowired
    private VarianteService varianteService;

    @Autowired
    private VarianteMapper varianteMapper;

    public VarianteDTO findVarianteByUniversalId(String universalId) {
        return varianteMapper.toDto(varianteService.findByUniversalId(universalId));
    }
}
