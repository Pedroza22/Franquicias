package com.nequi.prueba.franquicias.application.service;

import com.nequi.prueba.franquicias.application.port.in.ManageFranchiseUseCase;
import com.nequi.prueba.franquicias.application.port.out.FranchiseRepository;
import com.nequi.prueba.franquicias.domain.exception.ResourceNotFoundException;
import com.nequi.prueba.franquicias.domain.exception.UniqueViolationException;
import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.domain.model.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de franquicias.
 */
@Service
@RequiredArgsConstructor
public class FranchiseService implements ManageFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    /**
     * Crea una nueva franquicia.
     *
     * @param franchise la franquicia a crear.
     * @return la franquicia creada.
     * @throws UniqueViolationException si ya existe una franquicia con el mismo nombre.
     */
    @Override
    public Franchise createFranchise(Franchise franchise) {
        franchiseRepository.findByName(franchise.getName()).ifPresent(f -> {
            throw new UniqueViolationException("A franchise with the name already exists: " + franchise.getName());
        });
        return franchiseRepository.save(franchise);
    }

    /**
     * Agrega una nueva sucursal a una franquicia.
     *
     * @param franchiseId el ID de la franquicia.
     * @param branch la sucursal a agregar.
     * @return la sucursal agregada.
     * @throws ResourceNotFoundException si la franquicia no se encuentra.
     * @throws UniqueViolationException si ya existe una sucursal con el mismo nombre en la franquicia.
     */
    @Override
    public Branch addBranch(Long franchiseId, Branch branch) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found with id: " + franchiseId));

        franchiseRepository.findBranchByNameAndFranchiseId(branch.getName(), franchiseId).ifPresent(s -> {
            throw new UniqueViolationException("A branch with the name already exists: " + branch.getName() + " in this franchise.");
        });

        // Assign the franchise to the branch before saving it, although the bidirectional relationship is not persisted in the domain model.
        // This is important for business logic and for the persistence layer.

        return franchiseRepository.saveBranch(branch);
    }

    /**
     * Obtiene todas las franquicias.
     *
     * @return una lista de todas las franquicias.
     */
    @Override
    public List<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    /**
     * Actualiza el nombre de una franquicia.
     *
     * @param id el ID de la franquicia.
     * @param newName el nuevo nombre.
     * @return la franquicia actualizada.
     */
    @Override
    public Franchise updateName(Long id, String newName) {
        return franchiseRepository.updateName(id, newName);
    }

    /**
     * Actualiza el nombre de una sucursal.
     *
     * @param id el ID de la sucursal.
     * @param newName el nuevo nombre.
     * @return la sucursal actualizada.
     */
    @Override
    public Branch updateBranchName(Long id, String newName) {
        return franchiseRepository.updateBranchName(id, newName);
    }

    /**
     * Actualiza el nombre de una franquicia.
     *
     * @param id el ID de la franquicia.
     * @param newName el nuevo nombre.
     * @return la franquicia actualizada.
     */
    @Override
    public Franchise updateFranchiseName(Long id, String newName) {
        // Implementation for HU-F04
        return null;
    }

    /**
     * Actualiza el nombre de una sucursal.
     *
     * @param franchiseId el ID de la franquicia.
     * @param branchId el ID de la sucursal.
     * @param newName el nuevo nombre.
     * @return la sucursal actualizada.
     */
    @Override
    public Branch updateBranchName(Long franchiseId, Long branchId, String newName) {
        // Implementation for HU-F05
        return null;
    }
}