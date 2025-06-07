package com.pablomr.TCHub.entities.rol.modelo;

import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rol")
@SequenceGenerator(name = "generator", sequenceName = "rol_seq", allocationSize = 1)
@Data
public class Rol extends IdentifiedEntity<Long> {

    private String valor;
    private String codigo;

    @OneToMany(mappedBy = "rol")
    @EqualsAndHashCode.Exclude
    private Set<Usuario> usuarios;

}
