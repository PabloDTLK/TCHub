package com.pablomr.TCHub.entities.coleccion.modelo;

import com.pablomr.TCHub.entities.coleccionVariante.modelo.ColeccionVariante;
import com.pablomr.TCHub.entities.usuario.modelo.Usuario;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "coleccion")
@SequenceGenerator(name = "generator", sequenceName = "coleccion_seq", allocationSize = 1)
@Data
public class Coleccion extends IdentifiedEntity<Long> {
    private String nombre;
    @Column(name = "descripcion", length = 4096)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "coleccion")
    @EqualsAndHashCode.Exclude
    private Set<ColeccionVariante> coleccionVariantes;

}
