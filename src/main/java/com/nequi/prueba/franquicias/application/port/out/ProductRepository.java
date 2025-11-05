package com.nequi.prueba.franquicias.application.port.out;

import com.nequi.prueba.franquicias.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de productos.
 */
public interface ProductRepository {
    Optional<Product> get(String id);

    List<Product> getAllByBranch(String branchId);

    Optional<Product> getMoreStockByBranch(String branchId);

    List<Product> getLessStockByBranch(String branchId, int stock);

    Product createInBranch(String branchId, Product product);

    Optional<Product> update(String id, Product product);

    boolean delete(String id);
}