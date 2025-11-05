package com.nequi.prueba.franquicias.application.service;

import com.nequi.prueba.franquicias.application.port.in.ManageProductUseCase;
import com.nequi.prueba.franquicias.application.port.out.ProductRepository;
import com.nequi.prueba.franquicias.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductService implements ManageProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> get(String id) {
        return productRepository.get(id);
    }

    @Override
    public List<Product> getAllByBranch(String branchId) {
        return productRepository.getAllByBranch(branchId);
    }

    @Override
    public Optional<Product> getMoreStockByBranch(String branchId) {
        return productRepository.getMoreStockByBranch(branchId);
    }

    @Override
    public List<Product> getLessStockByBranch(String branchId, int stock) {
        return productRepository.getLessStockByBranch(branchId, stock);
    }

    @Override
    public Product createInBranch(String branchId, Product product) {
        return productRepository.createInBranch(branchId, product);
    }

    @Override
    public Optional<Product> update(String id, Product product) {
        return productRepository.update(id, product);
    }

    @Override
    public boolean delete(String id) {
        return productRepository.delete(id);
    }
}