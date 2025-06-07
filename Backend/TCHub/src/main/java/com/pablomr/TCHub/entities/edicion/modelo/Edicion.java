package com.pablomr.TCHub.entities.edicion.modelo;

import com.pablomr.TCHub.entities.carta.modelo.Carta;
import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDate;
import java.util.Set;

@DynamicInsert
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "edicion")
@SequenceGenerator(name = "generator", sequenceName = "edicion_seq", allocationSize = 1)
@Data
public class Edicion extends IdentifiedEntity<Long> {

    private LocalDate fechaLanzamiento;
    private String autor;

    @Column(insertable = false, updatable = false, name = "multiversal_id")
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    private String multiversalId;

    @ManyToOne
    @JoinColumn(name = "carta_id")
    private Carta carta;

    @OneToMany(mappedBy = "edicion")
    @EqualsAndHashCode.Exclude
    private Set<Variante> variantes;

}
