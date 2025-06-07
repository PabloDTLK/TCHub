package com.pablomr.TCHub.generico.dto.mapper;

public interface GenericMapper<T, D> {

    D toDto(T entity);

    T toEntity(D dto);

}
