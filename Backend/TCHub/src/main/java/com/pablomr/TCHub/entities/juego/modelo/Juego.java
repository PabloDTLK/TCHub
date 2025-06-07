package com.pablomr.TCHub.entities.juego.modelo;

import com.pablomr.TCHub.entities.expansion.modelo.Expansion;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "juego")
@SequenceGenerator(name = "generator", sequenceName = "juego_seq", allocationSize = 1)

@Data
@EqualsAndHashCode(callSuper = true)
public class Juego extends IdentifiedEntity<Long> {
    @NotNull
    private String nombre;
    @NotNull
    private String codigo;
    private String logo;

    @OneToMany(mappedBy = "juego")
    @EqualsAndHashCode.Exclude
    private Set<Expansion> expansiones;

}
