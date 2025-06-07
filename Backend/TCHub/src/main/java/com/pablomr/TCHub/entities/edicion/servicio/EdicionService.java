package com.pablomr.TCHub.entities.edicion.servicio;

import com.pablomr.TCHub.entities.edicion.modelo.Edicion;
import com.pablomr.TCHub.entities.edicion.repositorio.EdicionRepository;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EdicionService extends GenericServiceImpl<Edicion, Long> {

    @Autowired
    private EdicionRepository edicionRepository;

    public Edicion findByMultiversalId(String multiversalId) {
        return edicionRepository.findByMultiversalId(multiversalId);
    }

    public Boolean existsByMultiversalId(String multiversalId) {
        return edicionRepository.existsByMultiversalId(multiversalId);
    }

    public List<String> findListEdicion(Long longitud, Long pagina) {
        Pageable pageable = PageRequest.of(Math.toIntExact(pagina), Math.toIntExact(longitud));
        return edicionRepository.findListEdicion(pageable);
    }

    public List<String> findListEdicioLikeNombre(String nombre, String codigo, Long longitud, Long pagina) {
        Pageable pageable = PageRequest.of(Math.toIntExact(pagina), Math.toIntExact(longitud));
        return edicionRepository.findListEdicionLikeNombre(nombre, codigo, pageable);
    }


}
