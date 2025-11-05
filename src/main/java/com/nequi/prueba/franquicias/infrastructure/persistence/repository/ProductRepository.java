package com.nequi.prueba.franquicias.infrastructure.persistence.repository;

import com.nequi.prueba.franquicias.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Spring Data JPA para la entidad de producto.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    /**
     * Busca todos los productos de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return una lista de entidades de producto.
     */
    List<ProductEntity> findAllByBranchId(Long branchId);

    /**
     * Busca el producto con más stock en una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return un Optional que contiene la entidad de producto si se encuentra, o un Optional vacío si no.
     */
    Optional<ProductEntity> findTopByBranchIdOrderByStockDesc(Long branchId);

    /**
     * Busca todos los productos de una sucursal con un stock inferior a un valor dado.
     *
     * @param branchId el ID de la sucursal.
     * @param stock el valor del stock.
     * @return una lista de entidades de producto.
     */
    List<ProductEntity> findAllByBranchIdAndStockLessThan(Long branchId, int stock);
}