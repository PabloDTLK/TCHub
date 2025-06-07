package com.pablomr.TCHub.entities.idioma.modelo;


import com.pablomr.TCHub.entities.variante.modelo.Variante;
import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "idioma")
@SequenceGenerator(name = "generator", sequenceName = "idioma_seq", allocationSize = 1)
@Data
public class Idioma extends IdentifiedEntity<Long> {

    private String valor;
    private String codigo;

    @OneToMany(mappedBy = "idioma")
    @EqualsAndHashCode.Exclude
    private Set<Variante> variantes;

}
