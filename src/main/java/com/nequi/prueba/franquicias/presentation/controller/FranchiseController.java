package com.nequi.prueba.franquicias.presentation.controller;

import com.nequi.prueba.franquicias.application.port.in.ManageFranchiseUseCase;
import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.domain.model.Branch;
import com.nequi.prueba.franquicias.presentation.dto.FranchiseRequest;
import com.nequi.prueba.franquicias.presentation.dto.FranchiseResponse;
import com.nequi.prueba.franquicias.presentation.dto.BranchRequest;
import com.nequi.prueba.franquicias.presentation.dto.BranchResponse;
import com.nequi.prueba.franquicias.presentation.dto.mapper.FranchiseDtoMapper;
import com.nequi.prueba.franquicias.presentation.dto.mapper.BranchDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar las franquicias y sus sucursales.
 */
@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final ManageFranchiseUseCase franchiseUseCase;
    private final FranchiseDtoMapper franchiseDtoMapper;
    private final BranchDtoMapper branchDtoMapper;

    /**
     * Crea una nueva franquicia.
     *
     * @param request la solicitud para crear la franquicia.
     * @return la franquicia creada.
     */
    @PostMapping
    public ResponseEntity<FranchiseResponse> createFranchise(@RequestBody FranchiseRequest request) {
        Franchise franchise = franchiseDtoMapper.toDomain(request);
        Franchise newFranchise = franchiseUseCase.createFranchise(franchise);
        return new ResponseEntity<>(franchiseDtoMapper.toResponse(newFranchise), HttpStatus.CREATED);
    }

    /**
     * Agrega una nueva sucursal a una franquicia.
     *
     * @param franchiseId el ID de la franquicia.
     * @param request la solicitud para crear la sucursal.
     * @return la sucursal creada.
     */
    @PostMapping("/{franchiseId}/branches")
    public ResponseEntity<BranchResponse> addBranch(@PathVariable Long franchiseId, @RequestBody BranchRequest request) {
        Branch branch = branchDtoMapper.toDomain(request);
        Branch newBranch = franchiseUseCase.addBranch(franchiseId, branch);
        return new ResponseEntity<>(branchDtoMapper.toResponse(newBranch), HttpStatus.CREATED);
    }

    /**
     * Obtiene todas las franquicias.
     *
     * @return una lista de todas las franquicias.
     */
    @GetMapping
    public ResponseEntity<List<FranchiseResponse>> getAllFranchises() {
        List<Franchise> franchises = franchiseUseCase.getAllFranchises();
        List<FranchiseResponse> responses = franchises.stream()
                .map(franchiseDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Actualiza el nombre de una franquicia.
     *
     * @param id el ID de la franquicia.
     * @param request la solicitud con el nuevo nombre.
     * @return la franquicia actualizada.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<FranchiseResponse> updateFranchiseName(@PathVariable Long id, @RequestBody FranchiseRequest request) {
        Franchise franchise = franchiseUseCase.updateName(id, request.getName());
        return ResponseEntity.ok(franchiseDtoMapper.toResponse(franchise));
    }

    /**
     * Actualiza el nombre de una sucursal.
     *
     * @param id el ID de la sucursal.
     * @param request la solicitud con el nuevo nombre.
     * @return la sucursal actualizada.
     */
    @PatchMapping("/branches/{id}")
    public ResponseEntity<BranchResponse> updateBranchName(@PathVariable Long id, @RequestBody BranchRequest request) {
        Branch branch = franchiseUseCase.updateBranchName(id, request.getName());
        return ResponseEntity.ok(branchDtoMapper.toResponse(branch));
    }
}