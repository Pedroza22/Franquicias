package com.nequi.prueba.franquicias.application.port.out;

import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.domain.model.Branch;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de franquicias.
 */
public interface FranchiseRepository {
    Franchise save(Franchise franchise);

    Optional<Franchise> findByName(String name);

    List<Franchise> findAll();

    Optional<Franchise> findById(String id);

    Optional<Branch> findBranchById(String branchId);

    Branch saveBranch(Branch branch);

    Optional<Branch> findBranchByNameAndFranchiseId(String name, String franchiseId);

    Franchise updateName(String id, String newName);

    Branch updateBranchName(String id, String newName);

    void delete(String id);

    boolean deleteBranch(String franchiseId, String branchId);
}