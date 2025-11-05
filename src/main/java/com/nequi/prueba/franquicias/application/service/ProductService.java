package com.nequi.prueba.franquicias.application.service;

import com.nequi.prueba.franquicias.application.port.in.ManageProductUseCase;
import com.nequi.prueba.franquicias.application.port.out.ProductRepository;
import com.nequi.prueba.franquicias.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de productos.
 */
@Service
@RequiredArgsConstructor
public class ProductService implements ManageProductUseCase {

    private final ProductRepository productRepository;

    /**
     * Guarda un producto.
     *
     * @param product el producto a guardar.
     */
    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id el ID del producto.
     * @return un Optional que contiene el producto si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Product> get(Long id) {
        return productRepository.get(id);
    }

    /**
     * Actualiza un producto.
     *
     * @param product el producto a actualizar.
     */
    @Override
    public void update(Product product) {
        productRepository.update(product);
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto a eliminar.
     */
    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }

    /**
     * Obtiene todos los productos de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return una lista de productos de la sucursal.
     */
    @Override
    public List<Product> getAllByBranch(Long branchId) {
        return productRepository.getAllByBranch(branchId);
    }

    /**
     * Obtiene el producto con más stock de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return un Optional que contiene el producto con más stock si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Product> getMoreStockByBranch(Long branchId) {
        return productRepository.getMoreStockByBranch(branchId);
    }

    /**
     * Obtiene los productos con menos stock que una cantidad dada en una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @param stock la cantidad de stock.
     * @return una lista de productos con menos stock que la cantidad dada.
     */
    @Override
    public List<Product> getLessStockByBranch(Long branchId, int stock) {
        return productRepository.getLessStockByBranch(branchId, stock);
    }
}