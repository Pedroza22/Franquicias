package com.nequi.prueba.franquicias.infrastructure.persistence.mapper;

import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.infrastructure.persistence.entity.FranchiseEntity;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir entre el modelo de dominio de franquicia y la entidad de persistencia.
 */
@Mapper(componentModel = "spring")
public interface FranchiseMapper {
    /**
     * Convierte una entidad de franquicia a un modelo de dominio.
     *
     * @param entity la entidad de franquicia.
     * @return el modelo de dominio de franquicia.
     */
    Franchise toDomain(FranchiseEntity entity);

    /**
     * Convierte un modelo de dominio de franquicia a una entidad.
     *
     * @param domain el modelo de dominio de franquicia.
     * @return la entidad de franquicia.
     */
    FranchiseEntity toEntity(Franchise domain);
}