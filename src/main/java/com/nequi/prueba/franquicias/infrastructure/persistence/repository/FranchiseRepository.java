package com.nequi.prueba.franquicias.infrastructure.persistence.repository;

import com.nequi.prueba.franquicias.infrastructure.persistence.entity.FranchiseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio de Spring Data JPA para la entidad de franquicia.
 */
public interface FranchiseRepository extends JpaRepository<FranchiseEntity, Long> {
    /**
     * Busca una franquicia por su nombre.
     *
     * @param name el nombre de la franquicia.
     * @return un Optional que contiene la entidad de franquicia si se encuentra, o un Optional vacío si no.
     */
    Optional<FranchiseEntity> findByName(String name);
}