package com.pablomr.TCHub.entities.coleccionVariante.modelo;


import com.pablomr.TCHub.entities.coleccion.modelo.Coleccion;
import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true, exclude = {"coleccion", "variante"})
@Entity
@Table(name = "coleccion_variante")
@SequenceGenerator(name = "generator", sequenceName = "coleccion_variante_seq", allocationSize = 1)
@Data
public class ColeccionVariante extends IdentifiedEntity<Long> {

    private Long cantidad;

    @ManyToOne
    @JoinColumn(name = "coleccion_id", nullable = false)
//    @EqualsAndHashCode.Exclude
    private Coleccion coleccion;

    @ManyToOne
    @JoinColumn(name = "variante_id", nullable = false)
//    @EqualsAndHashCode.Exclude
    private Variante variante;

}
