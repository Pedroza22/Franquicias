package com.nequi.prueba.franquicias.application.service;

import com.nequi.prueba.franquicias.application.port.in.ManageFranchiseUseCase;
import com.nequi.prueba.franquicias.application.port.out.FranchiseRepository;
import com.nequi.prueba.franquicias.domain.exception.ResourceNotFoundException;
import com.nequi.prueba.franquicias.domain.exception.UniqueViolationException;
import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.domain.model.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class FranchiseService implements ManageFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    @Override
    public Franchise createFranchise(Franchise franchise) {
        franchiseRepository.findByName(franchise.getName()).ifPresent(f -> {
            throw new UniqueViolationException("A franchise with the name already exists: " + franchise.getName());
        });
        if (franchise.getBranches() == null) {
            franchise.setBranches(new ArrayList<>());
        }
        if (franchise.getId() == null || franchise.getId().isBlank()) {
            franchise.setId(UUID.randomUUID().toString());
        }
        return franchiseRepository.save(franchise);
    }

    @Override
    public Branch addBranch(String franchiseId, Branch branch) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found with id: " + franchiseId));
        if (franchise.getBranches() == null) {
            franchise.setBranches(new ArrayList<>());
        }

        franchise.getBranches().stream()
                .filter(b -> b.getName().equals(branch.getName()))
                .findFirst()
                .ifPresent(s -> {
                    throw new UniqueViolationException("A branch with the name already exists: " + branch.getName() + " in this franchise.");
                });
        if (branch.getId() == null || branch.getId().isBlank()) {
            branch.setId(UUID.randomUUID().toString());
        }
        branch.setFranchise(franchise);
        franchise.getBranches().add(branch);
        franchiseRepository.save(franchise);
        return branch;
    }

    @Override
    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    @Override
    public Optional<Franchise> getById(String id) {
        return franchiseRepository.findById(id);
    }

    @Override
    public Optional<Branch> getBranchById(String branchId) {
        return franchiseRepository.findBranchById(branchId);
    }

    @Override
    public Franchise updateFranchiseName(String id, String newName) {
        Franchise franchise = franchiseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found with id: " + id));
        franchise.setName(newName);
        return franchiseRepository.save(franchise);
    }

    @Override
    public Branch updateBranchName(String franchiseId, String branchId, String newName) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found with id: " + franchiseId));
        if (franchise.getBranches() == null) {
            throw new ResourceNotFoundException("No branches found for franchise id: " + franchiseId);
        }

        Branch branchToUpdate = franchise.getBranches().stream()
                .filter(b -> b.getId().equals(branchId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

        branchToUpdate.setName(newName);
        franchiseRepository.save(franchise);
        return branchToUpdate;
    }

    @Override
    public void deleteFranchise(String id) {
        franchiseRepository.delete(id);
    }

    @Override
    public boolean deleteBranch(String franchiseId, String branchId) {
        return franchiseRepository.deleteBranch(franchiseId, branchId);
    }
}