package com.ifkusyoba.itam_harkan_pal.core;

public interface BaseMapper<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}

