package com.nequi.prueba.franquicias.application.port.in;

import com.nequi.prueba.franquicias.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Caso de uso para la gestión de productos.
 */
public interface ManageProductUseCase {
    /**
     * Obtiene un producto por su ID.
     *
     * @param id el ID del producto.
     * @return un opcional con el producto si se encuentra.
     */
    Optional<Product> get(String id);

    /**
     * Obtiene todos los productos de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return una lista de productos de la sucursal.
     */
    List<Product> getAllByBranch(String branchId);

    /**
     * Obtiene el producto con más stock de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return un opcional con el producto con más stock.
     */
    Optional<Product> getMoreStockByBranch(String branchId);

    /**
     * Obtiene los productos con menos stock que un valor dado en una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @param stock el valor de stock a comparar.
     * @return una lista de productos con menos stock.
     */
    List<Product> getLessStockByBranch(String branchId, int stock);

    /**
     * Crea un producto dentro de una sucursal.
     */
    Product createInBranch(String branchId, Product product);

    /**
     * Actualiza los campos de un producto por su ID.
     */
    Optional<Product> update(String id, Product product);

    /**
     * Elimina un producto por su ID.
     */
    boolean delete(String id);
}