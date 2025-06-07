package com.pablomr.TCHub.entities.usuario.modelo;

import com.pablomr.TCHub.entities.coleccion.modelo.Coleccion;
import com.pablomr.TCHub.entities.rol.modelo.Rol;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Set;


@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "generator", sequenceName = "usuario_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(callSuper = true)
public class Usuario extends IdentifiedEntity<Long> {

    @NotNull
    private String username;
    @NotNull
    private String password;
    private boolean activo;

    @Transient
    private boolean admin;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    @EqualsAndHashCode.Exclude
    private Set<Coleccion> colecciones;

}

