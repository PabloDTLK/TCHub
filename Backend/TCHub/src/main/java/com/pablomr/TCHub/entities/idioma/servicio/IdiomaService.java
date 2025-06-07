package com.pablomr.TCHub.entities.idioma.servicio;

import com.pablomr.TCHub.entities.idioma.modelo.Idioma;
import com.pablomr.TCHub.entities.idioma.repositorio.IdiomaRepository;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdiomaService extends GenericServiceImpl<Idioma, Long> {

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Override
    public Idioma save(Idioma idioma) {
        idioma.setCodigo(idioma.getCodigo().toUpperCase());

        return super.save(idioma);
    }

    public Idioma findByCodigo(String codigo) {
        return idiomaRepository.findByCodigo(codigo);
    }

    public Idioma findByValor(String valor) {
        return idiomaRepository.findByValor(valor);
    }

}
