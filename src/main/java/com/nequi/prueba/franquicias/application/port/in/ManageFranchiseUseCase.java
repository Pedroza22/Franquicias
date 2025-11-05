package com.nequi.prueba.franquicias.application.port.in;

import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.domain.model.Branch;

import java.util.List;
import java.util.Optional;

/**
 * Caso de uso para la gestión de franquicias.
 */
public interface ManageFranchiseUseCase {
    /**
     * Crea una nueva franquicia.
     *
     * @param franchise la franquicia a crear.
     * @return la franquicia creada.
     */
    Franchise createFranchise(Franchise franchise);

    /**
     * Agrega una nueva sucursal a una franquicia.
     *
     * @param franchiseId el ID de la franquicia.
     * @param branch la sucursal a agregar.
     * @return la sucursal agregada.
     */
    Branch addBranch(String franchiseId, Branch branch);

    /**
     * Obtiene todas las franquicias.
     *
     * @return una lista de todas las franquicias.
     */
    List<Franchise> getAllFranchises();

    /**
     * Obtiene una franquicia por su ID.
     */
    Optional<Franchise> getById(String id);

    /**
     * Obtiene una sucursal por su ID.
     */
    Optional<Branch> getBranchById(String branchId);

    /**
     * Actualiza el nombre de una franquicia.
     *
     * @param id el ID de la franquicia.
     * @param newName el nuevo nombre.
     * @return la franquicia actualizada.
     */
    Franchise updateFranchiseName(String id, String newName);

    /**
     * Actualiza el nombre de una sucursal.
     *
     * @param franchiseId el ID de la franquicia.
     * @param branchId el ID de la sucursal.
     * @param newName el nuevo nombre.
     * @return la sucursal actualizada.
     */
    Branch updateBranchName(String franchiseId, String branchId, String newName);

    /**
     * Elimina una franquicia por su ID.
     */
    void deleteFranchise(String id);

    /**
     * Elimina una sucursal de una franquicia.
     */
    boolean deleteBranch(String franchiseId, String branchId);
}