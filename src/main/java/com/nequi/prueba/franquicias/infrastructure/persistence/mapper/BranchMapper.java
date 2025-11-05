package com.nequi.prueba.franquicias.infrastructure.persistence.mapper;

import com.nequi.prueba.franquicias.domain.model.Branch;
import com.nequi.prueba.franquicias.infrastructure.persistence.entity.BranchEntity;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir entre el modelo de dominio de sucursal y la entidad de persistencia.
 */
@Mapper(componentModel = "spring")
public interface BranchMapper {
    /**
     * Convierte una entidad de sucursal a un modelo de dominio.
     *
     * @param entity la entidad de sucursal.
     * @return el modelo de dominio de sucursal.
     */
    Branch toDomain(BranchEntity entity);

    /**
     * Convierte un modelo de dominio de sucursal a una entidad.
     *
     * @param domain el modelo de dominio de sucursal.
     * @return la entidad de sucursal.
     */
    BranchEntity toEntity(Branch domain);
}