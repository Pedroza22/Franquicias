-- Inicialización de esquema para Postgres
-- Base: franquicias (se crea automáticamente por POSTGRES_DB)

-- Opcional: extensiones útiles
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabla de franquicias
CREATE TABLE IF NOT EXISTS franchises (
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla de sucursales
CREATE TABLE IF NOT EXISTS branches (
    id           VARCHAR(36) PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    franchise_id VARCHAR(36)  NOT NULL,
    CONSTRAINT fk_branch_franchise
        FOREIGN KEY (franchise_id)
        REFERENCES franchises (id)
        ON DELETE CASCADE,
    CONSTRAINT uq_branch_name_per_franchise UNIQUE (name, franchise_id)
);

CREATE INDEX IF NOT EXISTS idx_branches_franchise_id ON branches (franchise_id);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS products (
    id        VARCHAR(36) PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    stock     INTEGER      NOT NULL CHECK (stock >= 0),
    branch_id VARCHAR(36)  NOT NULL,
    CONSTRAINT fk_product_branch
        FOREIGN KEY (branch_id)
        REFERENCES branches (id)
        ON DELETE CASCADE,
    CONSTRAINT uq_product_name_per_branch UNIQUE (name, branch_id)
);

CREATE INDEX IF NOT EXISTS idx_products_branch_id ON products (branch_id);