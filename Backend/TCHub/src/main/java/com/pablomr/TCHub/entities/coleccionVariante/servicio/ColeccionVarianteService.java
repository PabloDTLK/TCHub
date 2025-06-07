package com.pablomr.TCHub.entities.coleccionVariante.servicio;

import com.pablomr.TCHub.entities.coleccion.servicio.ColeccionService;
import com.pablomr.TCHub.entities.coleccionVariante.dto.ColeccionVariantesListDTO;
import com.pablomr.TCHub.entities.coleccionVariante.modelo.ColeccionVariante;
import com.pablomr.TCHub.entities.coleccionVariante.repositorio.ColeccionVarianteRepository;
import com.pablomr.TCHub.entities.variante.servicio.VarianteService;
import com.pablomr.TCHub.exception.ValidacionException;
import com.pablomr.TCHub.exception.utils.ErrorConstants;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ColeccionVarianteService extends GenericServiceImpl<ColeccionVariante, Long> {

    @Autowired
    private ColeccionVarianteRepository coleccionVarianteRepository;

    @Autowired
    private VarianteService varianteService;
    @Autowired
    private ColeccionService coleccionService;

    public ColeccionVariante findByColeccionYVariante(Long coleccionId, String universalId) {
        return coleccionVarianteRepository.findByColeccionYVariante(coleccionId, universalId);
    }

    public Boolean existsByColeccionYVariante(Long coleccionId, String universalId) {
        return coleccionVarianteRepository.existsByColeccionYVariante(coleccionId, universalId);
    }

    public Set<ColeccionVariante> findAllByColeccion(Long coleccionId) {
        Set<ColeccionVariante> coleccionVariantes = coleccionVarianteRepository.findAllByColeccion(coleccionId);

        if (coleccionVariantes.isEmpty())
            throw new ValidacionException(ErrorConstants.ENTITY_ID_NO_ENCONTRADO, coleccionId.toString());

        return coleccionVariantes;
    }

    public List<ColeccionVariantesListDTO> saveDtoList(List<ColeccionVariantesListDTO> lista, Long coleccionId) {
        List<ColeccionVariantesListDTO> response = new ArrayList<>();

        for (ColeccionVariantesListDTO dto: lista) {
            if (varianteService.existsByUniversalId(dto.getVarianteUniversalId()))
                response.add(saveDto(dto, coleccionId));
        }

        return response;
    }

    public ColeccionVariantesListDTO saveDto(ColeccionVariantesListDTO dto, Long coleccionId) {
        ColeccionVariante coleccionVariante = new ColeccionVariante();

        coleccionVariante.setVariante(
                varianteService.findByUniversalId(dto.getVarianteUniversalId())
        );
        coleccionVariante.setCantidad(dto.getCantidad());
        coleccionVariante.setColeccion(coleccionService.findById(coleccionId));

        coleccionVariante = super.save(coleccionVariante);

        return ColeccionVariantesListDTO
                .builder()
                .varianteUniversalId(coleccionVariante.getVariante().getUniversalId())
                .cantidad(coleccionVariante.getCantidad())
                .build();

    }

}
