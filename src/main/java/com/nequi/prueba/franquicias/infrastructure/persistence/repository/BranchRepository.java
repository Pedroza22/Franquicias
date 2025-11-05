package com.nequi.prueba.franquicias.infrastructure.persistence.repository;

import com.nequi.prueba.franquicias.infrastructure.persistence.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de Spring Data JPA para la entidad de sucursal.
 */
public interface BranchRepository extends JpaRepository<BranchEntity, Long> {
    /**
     * Busca una sucursal por su nombre y el ID de la franquicia a la que pertenece.
     *
     * @param name el nombre de la sucursal.
     * @param franchiseId el ID de la franquicia.
     * @return un Optional que contiene la entidad de sucursal si se encuentra, o un Optional vacío si no.
     */
    Optional<BranchEntity> findByNameAndFranchiseId(String name, Long franchiseId);
}