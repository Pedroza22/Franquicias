package com.nequi.prueba.franquicias.presentation.dto.mapper;

import com.nequi.prueba.franquicias.domain.model.Branch;
import com.nequi.prueba.franquicias.presentation.dto.BranchRequest;
import com.nequi.prueba.franquicias.presentation.dto.BranchResponse;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir entre DTOs de sucursal y el modelo de dominio.
 */
@Mapper(componentModel = "spring")
public interface BranchDtoMapper {
    Branch toDomain(BranchRequest request);
    BranchResponse toResponse(Branch branch);
}