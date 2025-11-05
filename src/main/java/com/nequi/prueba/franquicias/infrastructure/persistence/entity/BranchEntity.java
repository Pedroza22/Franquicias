package com.nequi.prueba.franquicias.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad que representa una sucursal en la base de datos.
 */
@Entity
@Table(name = "branches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchEntity {
    /**
     * El ID de la sucursal.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El nombre de la sucursal.
     */
    @Column(nullable = false)
    private String name;

    /**
     * La franquicia a la que pertenece la sucursal.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "franchise_id")
    private FranchiseEntity franchise;

    /**
     * La lista de productos de la sucursal.
     */
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products;
}