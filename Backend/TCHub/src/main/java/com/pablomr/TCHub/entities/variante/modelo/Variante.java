package com.pablomr.TCHub.entities.variante.modelo;

import com.pablomr.TCHub.entities.coleccionVariante.modelo.ColeccionVariante;
import com.pablomr.TCHub.entities.edicion.modelo.Edicion;
import com.pablomr.TCHub.entities.idioma.modelo.Idioma;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "variante")
@SequenceGenerator(name = "generator", sequenceName = "variante_seq", allocationSize = 1)
@Data
public class Variante extends IdentifiedEntity<Long> {

    private String nombre;
    @Column(name = "descripcion", length = 1024)
    private String descripcion;
    private String imagen;
    private Double precioMedio;
    private String universalId;

    @ManyToOne
    @JoinColumn(name = "edicion_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Edicion edicion;

    @ManyToOne
    @JoinColumn(name = "idioma_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Idioma idioma;

    @OneToMany(mappedBy = "variante")
    @EqualsAndHashCode.Exclude
    private Set<ColeccionVariante> coleccionVariantes;

}
