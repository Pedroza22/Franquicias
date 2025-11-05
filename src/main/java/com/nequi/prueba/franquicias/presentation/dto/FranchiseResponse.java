package com.nequi.prueba.franquicias.presentation.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO para la respuesta de una franquicia.
 */
@Data
public class FranchiseResponse {
    private String id;
    private String name;
    private List<BranchResponse> branches;
}