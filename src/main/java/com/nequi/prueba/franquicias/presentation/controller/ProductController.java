package com.nequi.prueba.franquicias.presentation.controller;

import com.nequi.prueba.franquicias.application.port.in.ManageProductUseCase;
import com.nequi.prueba.franquicias.domain.model.Product;
import com.nequi.prueba.franquicias.presentation.dto.ProductRequest;
import com.nequi.prueba.franquicias.presentation.dto.ProductResponse;
import com.nequi.prueba.franquicias.presentation.dto.mapper.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar los productos.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ManageProductUseCase productUseCase;
    private final ProductDtoMapper productDtoMapper;

    /**
     * Guarda un nuevo producto.
     *
     * @param request la solicitud para crear el producto.
     * @return una respuesta sin contenido.
     */
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ProductRequest request) {
        Product product = productDtoMapper.toDomain(request);
        productUseCase.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id el ID del producto.
     * @return el producto encontrado o un estado de no encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return productUseCase.get(id)
                .map(productDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza un producto existente.
     *
     * @param request la solicitud para actualizar el producto.
     * @return una respuesta sin contenido.
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProductRequest request) {
        Product product = productDtoMapper.toDomain(request);
        productUseCase.update(product);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto.
     * @return una respuesta sin contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productUseCase.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Obtiene todos los productos de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return una lista de productos de la sucursal.
     */
    @GetMapping("/branches/{branchId}")
    public ResponseEntity<List<ProductResponse>> getAllByBranch(@PathVariable Long branchId) {
        List<Product> products = productUseCase.getAllByBranch(branchId);
        List<ProductResponse> responses = products.stream()
                .map(productDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Obtiene el producto con más stock de una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @return el producto con más stock.
     */
    @GetMapping("/branches/{branchId}/most-stock")
    public ResponseEntity<ProductResponse> getMoreStockByBranch(@PathVariable Long branchId) {
        return productUseCase.getMoreStockByBranch(branchId)
                .map(productDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtiene los productos con menos stock que una cantidad dada en una sucursal.
     *
     * @param branchId el ID de la sucursal.
     * @param stock la cantidad de stock.
     * @return una lista de productos con menos stock.
     */
    @GetMapping("/branches/{branchId}/less-stock")
    public ResponseEntity<List<ProductResponse>> getLessStockByBranch(@PathVariable Long branchId, @RequestParam int stock) {
        List<Product> products = productUseCase.getLessStockByBranch(branchId, stock);
        List<ProductResponse> responses = products.stream()
                .map(productDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}