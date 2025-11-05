package com.nequi.prueba.franquicias.presentation.dto.mapper;

import com.nequi.prueba.franquicias.domain.model.Product;
import com.nequi.prueba.franquicias.presentation.dto.ProductRequest;
import com.nequi.prueba.franquicias.presentation.dto.ProductResponse;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir entre DTOs de producto y el modelo de dominio.
 */
@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    Product toDomain(ProductRequest request);
    ProductResponse toResponse(Product product);
}