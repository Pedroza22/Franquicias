package com.nequi.prueba.franquicias.infrastructure.persistence;

import com.nequi.prueba.franquicias.application.port.out.FranchiseRepository;
import com.nequi.prueba.franquicias.domain.model.Branch;
import com.nequi.prueba.franquicias.domain.model.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FranchisePersistenceAdapter implements FranchiseRepository {

    private final com.nequi.prueba.franquicias.infrastructure.persistence.repository.FranchiseRepository franchiseRepository;

    @Override
    public Franchise save(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    @Override
    public Optional<Franchise> findByName(String name) {
        return franchiseRepository.findByName(name);
    }

    @Override
    public List<Franchise> findAll() {
        return franchiseRepository.findAll();
    }

    @Override
    public Optional<Franchise> findById(String id) {
        return franchiseRepository.findById(id);
    }

    @Override
    public Optional<Branch> findBranchById(String branchId) {
        return franchiseRepository.findAll().stream()
                .flatMap(franchise -> franchise.getBranches().stream())
                .filter(branch -> branch.getId().equals(branchId))
                .findFirst();
    }

    @Override
    public Branch saveBranch(Branch branch) {
        // This is a bit more complex with nested documents.
        // A simple save won't work here. You'd typically find the parent franchise
        // and update its list of branches.
        // For now, this is a placeholder.
        return branch;
    }

    @Override
    public Optional<Branch> findBranchByNameAndFranchiseId(String name, String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .flatMap(franchise -> franchise.getBranches().stream()
                        .filter(branch -> branch.getName().equals(name))
                        .findFirst());
    }

    @Override
    public Franchise updateName(String id, String newName) {
        return franchiseRepository.findById(id).map(franchise -> {
            franchise.setName(newName);
            return franchiseRepository.save(franchise);
        }).orElse(null);
    }

    @Override
    public Branch updateBranchName(String id, String newName) {
        // This is a bit more complex with nested documents.
        // You would need to find the franchise containing the branch, update the branch name,
        // and then save the franchise.
        // For now, this is a placeholder.
        return null;
    }

    @Override
    public void delete(String id) {
        franchiseRepository.deleteById(id);
    }

    @Override
    public boolean deleteBranch(String franchiseId, String branchId) {
        return franchiseRepository.findById(franchiseId).map(franchise -> {
            if (franchise.getBranches() == null) {
                return false;
            }
            boolean removed = franchise.getBranches().removeIf(b -> b.getId().equals(branchId));
            if (removed) {
                franchiseRepository.save(franchise);
            }
            return removed;
        }).orElse(false);
    }
}