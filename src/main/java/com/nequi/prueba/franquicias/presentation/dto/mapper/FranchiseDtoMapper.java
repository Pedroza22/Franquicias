package com.nequi.prueba.franquicias.presentation.dto.mapper;

import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.presentation.dto.FranchiseRequest;
import com.nequi.prueba.franquicias.presentation.dto.FranchiseResponse;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir entre DTOs de franquicia y el modelo de dominio.
 */
@Mapper(componentModel = "spring")
public interface FranchiseDtoMapper {
    Franchise toDomain(FranchiseRequest request);
    FranchiseResponse toResponse(Franchise franchise);
}