package com.nequi.prueba.franquicias.application.port.in;

import com.nequi.prueba.franquicias.domain.model.Franchise;
import com.nequi.prueba.franquicias.domain.model.Branch;

import java.util.List;

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
    Branch addBranch(Long franchiseId, Branch branch);

    /**
     * Obtiene todas las franquicias.
     *
     * @return una lista de todas las franquicias.
     */
    List<Franchise> getAllFranchises();

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

    /**
     * Actualiza el nombre de una franquicia.
     *
     * @param id el ID de la franquicia.
     * @param newName el nuevo nombre.
     * @return la franquicia actualizada.
     */
    Franchise updateFranchiseName(Long id, String newName);

    /**
     * Actualiza el nombre de una sucursal.
     *
     * @param franchiseId el ID de la franquicia.
     * @param branchId el ID de la sucursal.
     * @param newName el nuevo nombre.
     * @return la sucursal actualizada.
     */
    Branch updateBranchName(Long franchiseId, Long branchId, String newName);
}