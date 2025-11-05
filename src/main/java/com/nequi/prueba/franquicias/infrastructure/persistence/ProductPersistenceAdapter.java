package com.nequi.prueba.franquicias.infrastructure.persistence;


import com.nequi.prueba.franquicias.domain.model.Product;
import com.nequi.prueba.franquicias.domain.exception.ResourceNotFoundException;
import com.nequi.prueba.franquicias.infrastructure.persistence.entity.ProductEntity;
import com.nequi.prueba.franquicias.infrastructure.persistence.mapper.ProductMapper;
import com.nequi.prueba.franquicias.infrastructure.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia para la gestión de productos.
 */
@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements com.nequi.prueba.franquicias.application.port.out.ProductRepository {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Guarda un producto.
     *
     * @param product el producto a guardar.
     */
    @Override
    public void save(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        productRepository.save(entity);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id el ID del producto.
     * @return un Optional que contiene el producto si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Product> get(Long id) {
        return productRepository.findById(id).map(productMapper::toDomain);
    }

    /**
     * Actualiza un producto.
     *
     * @param product el producto a actualizar.
     */
    @Override
    public void update(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        productRepository.save(entity);
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto a eliminar.
     */
    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Obtiene todos los productos de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return una lista de productos de la sucursal.
     */
    @Override
    public List<Product> getAllByBranch(Long branchId) {
        return productRepository.findAllByBranchId(branchId).stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el producto con más stock de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return un Optional que contiene el producto con más stock si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Product> getMoreStockByBranch(Long branchId) {
        return productRepository.findTopByBranchIdOrderByStockDesc(branchId).map(productMapper::toDomain);
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
        return productRepository.findAllByBranchIdAndStockLessThan(branchId, stock).stream()
                .map(productMapper::toDomain)
                .collect(Collectors.toList());
    }
}