package com.pablomr.TCHub.entities.expansion.servicio;

import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.entities.expansion.repositorio.ExpansionRepository;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ExpansionService extends GenericServiceImpl<Expansion, Long> {

    @Autowired
    private ExpansionRepository expansionRepository;

    public Expansion getByNombreAndJuego(String nombre, Long juegoId) {
        return expansionRepository.findByNombreAndJuego(nombre, juegoId);
    }

    public Expansion findByNombre(String nombre) {
        return expansionRepository.findByNombre(nombre);
    }

    public Boolean existsByCodigo(String codigo) {
        return expansionRepository.existsByCodigo(codigo);
    }

    public Expansion findByCodigo(String codigo) {
        return expansionRepository.findByCodigo(codigo);
    }

    public Set<Expansion> findAllByNombreAndJuego(Set<String> nombres, Long juegoId) {

        Set<Expansion> expansiones = new HashSet<>();

        for(String nombre: nombres) {
            expansiones.add(
                    getByNombreAndJuego(nombre, juegoId)
            );
        }

        return expansiones;
    }

}
