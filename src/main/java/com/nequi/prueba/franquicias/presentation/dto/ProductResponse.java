package com.nequi.prueba.franquicias.presentation.dto;

import lombok.Data;

/**
 * DTO para la respuesta de un producto.
 */
@Data
public class ProductResponse {
    private String id;
    private String name;
    private Integer stock;
}