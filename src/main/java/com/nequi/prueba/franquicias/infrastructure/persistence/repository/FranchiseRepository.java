package com.nequi.prueba.franquicias.infrastructure.persistence.repository;

import com.nequi.prueba.franquicias.domain.model.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FranchiseRepository extends JpaRepository<Franchise, String> {
    Optional<Franchise> findByName(String name);
}