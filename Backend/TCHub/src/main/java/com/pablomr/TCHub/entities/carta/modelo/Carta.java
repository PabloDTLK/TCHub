package com.pablomr.TCHub.entities.carta.modelo;

import com.pablomr.TCHub.entities.edicion.modelo.Edicion;
import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "carta")
@SequenceGenerator(name = "generator", sequenceName = "carta_seq", allocationSize = 1)
@Data
public class Carta extends IdentifiedEntity<Long> {

    @Column(name = "omniversal_id")
    private String omniversalId;

    @ManyToOne
    @JoinColumn(name = "expansion_id")
    private Expansion expansion;

    @OneToMany(mappedBy = "carta")
    @EqualsAndHashCode.Exclude
    private Set<Edicion> ediciones;

}
