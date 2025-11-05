package com.nequi.prueba.franquicias.infrastructure.persistence;


import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.domain.model.Branch;
import com.nequi.prueba.franquicias.domain.exception.UniqueViolationException;
import com.nequi.prueba.franquicias.domain.exception.ResourceNotFoundException;
import com.nequi.prueba.franquicias.infrastructure.persistence.entity.FranchiseEntity;
import com.nequi.prueba.franquicias.infrastructure.persistence.entity.BranchEntity;
import com.nequi.prueba.franquicias.infrastructure.persistence.mapper.FranchiseMapper;
import com.nequi.prueba.franquicias.infrastructure.persistence.mapper.BranchMapper;
import com.nequi.prueba.franquicias.infrastructure.persistence.repository.FranchiseRepository;
import com.nequi.prueba.franquicias.infrastructure.persistence.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia para la gestión de franquicias.
 */
@Component
@RequiredArgsConstructor
public class FranchisePersistenceAdapter implements com.nequi.prueba.franquicias.application.port.out.FranchiseRepository {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;
    private final FranchiseMapper franchiseMapper;
    private final BranchMapper branchMapper;

    /**
     * Guarda una franquicia.
     *
     * @param franchise la franquicia a guardar.
     * @return la franquicia guardada.
     */
    @Override
    public Franchise save(Franchise franchise) {
        FranchiseEntity entity = franchiseMapper.toEntity(franchise);
        return franchiseMapper.toDomain(franchiseRepository.save(entity));
    }

    /**
     * Busca una franquicia por su nombre.
     *
     * @param name el nombre de la franquicia.
     * @return un Optional que contiene la franquicia si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Franchise> findByName(String name) {
        return franchiseRepository.findByName(name).map(franchiseMapper::toDomain);
    }

    /**
     * Obtiene todas las franquicias.
     *
     * @return una lista de todas las franquicias.
     */
    @Override
    public List<Franchise> findAll() {
        return franchiseRepository.findAll().stream()
                .map(franchiseMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Busca una franquicia por su ID.
     *
     * @param id el ID de la franquicia.
     * @return un Optional que contiene la franquicia si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Franchise> findById(Long id) {
        return franchiseRepository.findById(id).map(franchiseMapper::toDomain);
    }

    /**
     * Busca una sucursal por su ID.
     *
     * @param branchId el ID de la sucursal.
     * @return un Optional que contiene la sucursal si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Branch> findBranchById(Long branchId) {
        return branchRepository.findById(branchId).map(branchMapper::toDomain);
    }

    /**
     * Guarda una sucursal.
     *
     * @param branch la sucursal a guardar.
     * @return la sucursal guardada.
     */
    @Override
    public Branch saveBranch(Branch branch) {
        BranchEntity branchEntity = branchMapper.toEntity(branch);
        return branchMapper.toDomain(branchRepository.save(branchEntity));
    }

    /**
     * Busca una sucursal por su nombre y el ID de la franquicia.
     *
     * @param name el nombre de la sucursal.
     * @param franchiseId el ID de la franquicia.
     * @return un Optional que contiene la sucursal si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Branch> findBranchByNameAndFranchiseId(String name, Long franchiseId) {
        return branchRepository.findByNameAndFranchiseId(name, franchiseId).map(branchMapper::toDomain);
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
        FranchiseEntity franchiseEntity = franchiseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found"));
        franchiseEntity.setName(newName);
        return franchiseMapper.toDomain(franchiseRepository.save(franchiseEntity));
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
        BranchEntity branchEntity = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        branchEntity.setName(newName);
        return branchMapper.toDomain(branchRepository.save(branchEntity));
    }
}