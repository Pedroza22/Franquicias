package com.nequi.prueba.franquicias.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidad que representa una franquicia en la base de datos.
 */
@Entity
@Table(name = "franquicias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseEntity {
    /**
     * El ID de la franquicia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El nombre de la franquicia.
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * La lista de sucursales de la franquicia.
     */
    @OneToMany(mappedBy = "franchise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BranchEntity> branches;
}