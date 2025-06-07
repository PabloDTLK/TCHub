package com.pablomr.TCHub.generico.repositorio;

import com.pablomr.TCHub.generico.modelo.IdentifiedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends IdentifiedEntity<ID>, ID> extends JpaRepository<T, ID> {

}
