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
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ManageProductUseCase productUseCase;
    private final ProductDtoMapper productDtoMapper;

    @PostMapping("/branches/{branchId}")
    public ResponseEntity<ProductResponse> create(@PathVariable String branchId, @RequestBody ProductRequest request) {
        Product product = productDtoMapper.toDomain(request);
        Product created = productUseCase.createInBranch(branchId, product);
        return new ResponseEntity<>(productDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable String id) {
        return productUseCase.get(id)
                .map(productDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/branches/{branchId}")
    public ResponseEntity<List<ProductResponse>> getAllByBranch(@PathVariable String branchId) {
        List<Product> products = productUseCase.getAllByBranch(branchId);
        List<ProductResponse> responses = products.stream()
                .map(productDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/branches/{branchId}/most-stock")
    public ResponseEntity<ProductResponse> getMoreStockByBranch(@PathVariable String branchId) {
        return productUseCase.getMoreStockByBranch(branchId)
                .map(productDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/branches/{branchId}/less-stock")
    public ResponseEntity<List<ProductResponse>> getLessStockByBranch(@PathVariable String branchId, @RequestParam int stock) {
        List<Product> products = productUseCase.getLessStockByBranch(branchId, stock);
        List<ProductResponse> responses = products.stream()
                .map(productDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable String id, @RequestBody ProductRequest request) {
        Product product = productDtoMapper.toDomain(request);
        return productUseCase.update(id, product)
                .map(productDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = productUseCase.delete(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}