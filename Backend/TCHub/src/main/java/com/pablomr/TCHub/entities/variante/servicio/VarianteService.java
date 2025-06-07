package com.pablomr.TCHub.entities.variante.servicio;

import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.entities.variante.repositorio.VarianteRepository;
import com.pablomr.TCHub.exception.ValidacionException;
import com.pablomr.TCHub.exception.utils.ErrorConstants;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VarianteService extends GenericServiceImpl<Variante, Long> {

    @Autowired
    private VarianteRepository varianteRepository;

    public Variante findByUniversalId(String universalId) {

        if (!existsByUniversalId(universalId))
            throw new ValidacionException(ErrorConstants.VARIANTE_UNIVERSAL_ID_NOT_FOUND, universalId);

        return varianteRepository.findByUniversalId(universalId);
    }

    public Boolean existsByUniversalId(String universalId) {
        return varianteRepository.existsByUniversalId(universalId);
    }

}
