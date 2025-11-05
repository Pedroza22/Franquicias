package com.nequi.prueba.franquicias.infrastructure.persistence;

import com.nequi.prueba.franquicias.application.port.out.ProductRepository;
import com.nequi.prueba.franquicias.domain.model.Product;
import com.nequi.prueba.franquicias.domain.model.Branch;
import com.nequi.prueba.franquicias.domain.exception.ResourceNotFoundException;
import com.nequi.prueba.franquicias.domain.exception.UniqueViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepository {

    private final com.nequi.prueba.franquicias.infrastructure.persistence.repository.FranchiseRepository franchiseRepository;

    @Override
    public Optional<Product> get(String id) {
        return franchiseRepository.findAll().stream()
                .flatMap(franchise -> franchise.getBranches().stream())
                .flatMap(branch -> branch.getProducts().stream())
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAllByBranch(String branchId) {
        return franchiseRepository.findAll().stream()
                .flatMap(franchise -> franchise.getBranches().stream())
                .filter(branch -> branch.getId().equals(branchId))
                .flatMap(branch -> branch.getProducts().stream())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getMoreStockByBranch(String branchId) {
        return getAllByBranch(branchId).stream()
                .max(Comparator.comparing(Product::getStock));
    }

    @Override
    public List<Product> getLessStockByBranch(String branchId, int stock) {
        return getAllByBranch(branchId).stream()
                .filter(product -> product.getStock() < stock)
                .collect(Collectors.toList());
    }

    @Override
    public Product createInBranch(String branchId, Product product) {
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Stock must be non-negative");
        }
        // Find franchise containing the branch
        return franchiseRepository.findAll().stream()
                .filter(franchise -> franchise.getBranches() != null)
                .filter(franchise -> franchise.getBranches().stream().anyMatch(b -> b.getId().equals(branchId)))
                .findFirst()
                .map(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElse(null);
                    if (branch == null) {
                        throw new ResourceNotFoundException("Branch not found with id: " + branchId);
                    }
                    if (branch.getProducts() == null) {
                        branch.setProducts(new java.util.ArrayList<>());
                    }
                    boolean existsByName = branch.getProducts().stream()
                            .anyMatch(p -> p.getName().equals(product.getName()));
                    if (existsByName) {
                        throw new UniqueViolationException("A product with the name already exists: " + product.getName() + " in this branch.");
                    }
                    if (product.getId() == null || product.getId().isBlank()) {
                        product.setId(UUID.randomUUID().toString());
                    }
                    product.setBranch(branch);
                    branch.getProducts().add(product);
                    franchiseRepository.save(franchise);
                    return product;
                })
                .orElse(product);
    }

    @Override
    public Optional<Product> update(String id, Product product) {
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Stock must be non-negative");
        }
        // find product and update fields
        return franchiseRepository.findAll().stream()
                .filter(franchise -> franchise.getBranches() != null)
                .flatMap(franchise -> franchise.getBranches().stream()
                        .filter(b -> b.getProducts() != null)
                        .peek(b -> {})
                        .map(b -> new java.util.AbstractMap.SimpleEntry<>(franchise, b)))
                .map(entry -> {
                    Branch branch = entry.getValue();
                    Product existing = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(id))
                            .findFirst()
                            .orElse(null);
                    if (existing != null) {
                        boolean nameConflict = branch.getProducts().stream()
                                .anyMatch(p -> !p.getId().equals(id) && p.getName().equals(product.getName()));
                        if (nameConflict) {
                            throw new UniqueViolationException("A product with the name already exists: " + product.getName() + " in this branch.");
                        }
                        existing.setName(product.getName());
                        existing.setStock(product.getStock());
                        franchiseRepository.save(entry.getKey());
                        return Optional.of(existing);
                    }
                    return Optional.<Product>empty();
                })
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());
    }

    @Override
    public boolean delete(String id) {
        // remove product by id from any branch and save franchise
        return franchiseRepository.findAll().stream()
                .map(franchise -> {
                    boolean changed = false;
                    if (franchise.getBranches() != null) {
                        for (Branch b : franchise.getBranches()) {
                            if (b.getProducts() != null) {
                                boolean removed = b.getProducts().removeIf(p -> p.getId().equals(id));
                                changed = changed || removed;
                            }
                        }
                    }
                    if (changed) {
                        franchiseRepository.save(franchise);
                    }
                    return changed;
                })
                .reduce(false, (a, b) -> a || b);
    }
}