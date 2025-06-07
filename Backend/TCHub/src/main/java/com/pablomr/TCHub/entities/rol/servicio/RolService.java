package com.pablomr.TCHub.entities.rol.servicio;

import com.pablomr.TCHub.entities.rol.modelo.Rol;
import com.pablomr.TCHub.entities.rol.repositorio.RolReposiroty;
import com.pablomr.TCHub.generico.servicio.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService extends GenericServiceImpl<Rol, Long> {

    @Autowired
    private RolReposiroty rolReposiroty;

    public Rol findByCodigo(String codigo) {
        return rolReposiroty.findByCodigo(codigo);
    }

}
