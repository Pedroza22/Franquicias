package com.nequi.prueba.franquicias.presentation.dto;

import lombok.Data;

/**
 * DTO para la solicitud de creación de una sucursal.
 */
@Data
public class BranchRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}