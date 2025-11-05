package com.nequi.prueba.franquicias.presentation.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO para la respuesta de una franquicia.
 */
@Data
public class FranchiseResponse {
    private Long id;
    private String name;
    private List<BranchResponse> branches;
}