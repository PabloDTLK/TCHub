package com.pablomr.TCHub.entities.idioma.servicio.conDto;

import com.pablomr.TCHub.entities.idioma.dto.IdiomaDTO;
import com.pablomr.TCHub.entities.idioma.dto.mapper.IdiomaMapper;
import com.pablomr.TCHub.entities.idioma.modelo.Idioma;
import com.pablomr.TCHub.entities.idioma.servicio.IdiomaService;
import com.pablomr.TCHub.generico.servicio.conDTO.GenericDtoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdiomaDtoService extends GenericDtoServiceImpl<Idioma, Long, IdiomaDTO> {

    @Autowired
    private IdiomaMapper idiomaMapper;

    @Autowired
    private IdiomaService idiomaService;

    public IdiomaDTO findByCodigo(String codigo) {
        return idiomaMapper.toDto(
                idiomaService.findByCodigo(codigo)
        );
    }

    public IdiomaDTO findByValor(String valor) {
        return idiomaMapper.toDto(
                idiomaService.findByValor(valor)
        );
    }
}
