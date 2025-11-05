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
@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final ManageFranchiseUseCase franchiseUseCase;
    private final FranchiseDtoMapper franchiseDtoMapper;
    private final BranchDtoMapper branchDtoMapper;

    @PostMapping
    public ResponseEntity<FranchiseResponse> createFranchise(@RequestBody FranchiseRequest request) {
        Franchise franchise = franchiseDtoMapper.toDomain(request);
        Franchise newFranchise = franchiseUseCase.createFranchise(franchise);
        return new ResponseEntity<>(franchiseDtoMapper.toResponse(newFranchise), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FranchiseResponse> getFranchise(@PathVariable String id) {
        return franchiseUseCase.getById(id)
                .map(franchiseDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{franchiseId}/branches")
    public ResponseEntity<BranchResponse> addBranch(@PathVariable String franchiseId, @RequestBody BranchRequest request) {
        Branch branch = branchDtoMapper.toDomain(request);
        Branch newBranch = franchiseUseCase.addBranch(franchiseId, branch);
        return new ResponseEntity<>(branchDtoMapper.toResponse(newBranch), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FranchiseResponse>> getAllFranchises() {
        List<Franchise> franchises = franchiseUseCase.getAllFranchises();
        List<FranchiseResponse> responses = franchises.stream()
                .map(franchiseDtoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FranchiseResponse> updateFranchiseName(@PathVariable String id, @RequestBody FranchiseRequest request) {
        Franchise franchise = franchiseUseCase.updateFranchiseName(id, request.getName());
        return ResponseEntity.ok(franchiseDtoMapper.toResponse(franchise));
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}")
    public ResponseEntity<BranchResponse> updateBranchName(@PathVariable String franchiseId, @PathVariable String branchId, @RequestBody BranchRequest request) {
        Branch branch = franchiseUseCase.updateBranchName(franchiseId, branchId, request.getName());
        return ResponseEntity.ok(branchDtoMapper.toResponse(branch));
    }

    @GetMapping("/branches/{branchId}")
    public ResponseEntity<BranchResponse> getBranch(@PathVariable String branchId) {
        return franchiseUseCase.getBranchById(branchId)
                .map(branchDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchise(@PathVariable String id) {
        franchiseUseCase.deleteFranchise(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{franchiseId}/branches/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable String franchiseId, @PathVariable String branchId) {
        boolean deleted = franchiseUseCase.deleteBranch(franchiseId, branchId);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}