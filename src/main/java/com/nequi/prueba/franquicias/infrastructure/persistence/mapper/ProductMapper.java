package com.nequi.prueba.franquicias.infrastructure.persistence.mapper;

import com.nequi.prueba.franquicias.domain.model.Product;
import com.nequi.prueba.franquicias.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir between el modelo de dominio de producto y la entidad de persistencia.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    /**
     * Convierte una entidad de producto a un modelo de dominio.
     *
     * @param entity la entidad de producto.
     * @return el modelo de dominio de producto.
     */
    Product toDomain(ProductEntity entity);

    /**
     * Convierte un modelo de dominio de producto a una entidad.
     *
     * @param domain el modelo de dominio de producto.
     * @return la entidad de producto.
     */
    ProductEntity toEntity(Product domain);
}