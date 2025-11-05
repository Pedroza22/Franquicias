package com.nequi.prueba.franquicias.application.port.out;

import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.domain.model.Branch;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de franquicias.
 */
public interface FranchiseRepository {
    /**
     * Guarda una nueva franquicia.
     *
     * @param franchise la franquicia a guardar.
     * @return la franquicia guardada.
     */
    Franchise save(Franchise franchise);

    /**
     * Busca una franquicia por su nombre.
     *
     * @param name el nombre de la franquicia.
     * @return un opcional con la franquicia si se encuentra.
     */
    Optional<Franchise> findByName(String name);

    /**
     * Obtiene todas las franquicias.
     *
     * @return una lista de todas las franquicias.
     */
    List<Franchise> findAll();

    /**
     * Busca una franquicia por su ID.
     *
     * @param id el ID de la franquicia.
     * @return un opcional con la franquicia si se encuentra.
     */
    Optional<Franchise> findById(Long id);

    /**
     * Busca una sucursal por su ID.
     *
     * @param branchId el ID de la sucursal.
     * @return un opcional con la sucursal si se encuentra.
     */
    Optional<Branch> findBranchById(Long branchId);

    /**
     * Guarda una nueva sucursal.
     *
     * @param branch la sucursal a guardar.
     * @return la sucursal guardada.
     */
    Branch saveBranch(Branch branch);

    /**
     * Busca una sucursal por su nombre y el ID de la franquicia.
     *
     * @param name el nombre de la sucursal.
     * @param franchiseId el ID de la franquicia.
     * @return un opcional con la sucursal si se encuentra.
     */
    Optional<Branch> findBranchByNameAndFranchiseId(String name, Long franchiseId);

    /**
     * Actualiza el nombre de una franquicia.
     *
     * @param id el ID de la franquicia.
     * @param newName el nuevo nombre.
     * @return la franquicia actualizada.
     */
    Franchise updateName(Long id, String newName);

    /**
     * Actualiza el nombre de una sucursal.
     *
     * @param id el ID de la sucursal.
     * @param newName el nuevo nombre.
     * @return la sucursal actualizada.
     */
    Branch updateBranchName(Long id, String newName);
}