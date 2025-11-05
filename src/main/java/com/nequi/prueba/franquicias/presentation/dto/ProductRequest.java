package com.nequi.prueba.franquicias.presentation.dto;

import lombok.Data;

/**
 * DTO para la solicitud de creación de un producto.
 */
@Data
public class ProductRequest {
    private String name;
    private Integer stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}