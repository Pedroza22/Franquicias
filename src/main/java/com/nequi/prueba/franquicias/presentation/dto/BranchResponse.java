package com.nequi.prueba.franquicias.presentation.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO para la respuesta de una sucursal.
 */
@Data
public class BranchResponse {
    private Long id;
    private String name;
    private List<ProductResponse> products;
}