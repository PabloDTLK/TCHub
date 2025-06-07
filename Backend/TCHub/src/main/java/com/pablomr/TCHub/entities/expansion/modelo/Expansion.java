package com.pablomr.TCHub.entities.expansion.modelo;

import com.pablomr.TCHub.entities.carta.modelo.Carta;
import com.pablomr.TCHub.entities.juego.modelo.Juego;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "expansion")
@SequenceGenerator(name = "generator", sequenceName = "expansion_seq", allocationSize = 1)
@Data
public class Expansion extends IdentifiedEntity<Long> {

    private String codigo;
    private String nombre;
    private LocalDate fechaLanzamiento;
    private String logo;

    @ManyToOne
    @JoinColumn(name = "juego_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Juego juego;

    @OneToMany(mappedBy = "expansion")
    @EqualsAndHashCode.Exclude
    private Set<Carta> cartas;

}
