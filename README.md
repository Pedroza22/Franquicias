# Franquicias API

API Spring Boot para gestión de franquicias, sucursales y productos con JPA y PostgreSQL. Documentación con Swagger.

## Arranque Rápido

- Requisitos: `Java 17+`, `Maven`, `Docker Desktop`.
- Desarrollo (H2 por defecto): `./mvnw spring-boot:run` y abre `http://localhost:8081/swagger-ui/index.html`.
- Desarrollo con Postgres local (perfil dev): `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -DskipTests`.
- Producción (Docker Compose): `docker compose up -d` para levantar `app`, `postgres` y `pgadmin`.

## Base de Datos

- Esquema inicial SQL: `db/init/01_schema.sql`. Se aplica automáticamente en el primer arranque de Postgres si el directorio de datos está vacío.
- Tablas: `franchises`, `branches`, `products` con claves foráneas e índices. Unicidad de nombre por franquicia/sucursal.

### Docker Compose

- Archivo: `docker-compose.yml`.
- Servicio `postgres`:
  - Imagen `postgres:16`.
  - Variables: `POSTGRES_DB=franquicias`, `POSTGRES_USER=postgres`, `POSTGRES_PASSWORD=postgres`.
  - Puertos: `5432:5432`.
  - Volúmenes: `./db/init:/docker-entrypoint-initdb.d` y volumen de datos `postgres_data`.
- Servicio `app`:
  - Perfil `prod` (`server.port=8080`).
  - Variables de entorno `SPRING_DATASOURCE_*` apuntando al servicio `postgres`.
- Servicio opcional `pgadmin` en `http://localhost:8083`.

### Levantar Postgres con Docker Desktop (UI)

- Container name: `franquicias-postgres`.
- Host port: `5432`.
- Env: `POSTGRES_DB=franquicias`, `POSTGRES_USER=postgres`, `POSTGRES_PASSWORD=postgres`.
- Volumes:
  - Host path: `c:\Users\julia\OneDrive\Desktop\franquicias\franquicias\db\init` → Container path: `/docker-entrypoint-initdb.d`.
  - Persistencia opcional: `c:\docker\pgdata\franquicias` → `/var/lib/postgresql/data`.
- Nota: los scripts del init solo corren en el primer arranque con datos vacíos. Si ya existía el volumen, elimínalo para reaplicar el init.

## Conexión desde DBeaver

- Host: `localhost`
- Port: `5432`
- Database: `franquicias`
- User: `postgres`
- Password: `postgres`
- Verifica tablas:
```
SELECT table_name
FROM information_schema.tables
WHERE table_schema='public'
  AND table_name IN ('franchises','branches','products');
```

## Perfiles y Puertos

- `default` (H2 in-memory): `server.port=8081`, dialect H2.
- `dev` (Postgres local): `server.port=8081`, `jdbc:postgresql://localhost:5432/franquicias`.
- `prod` (Postgres por entorno/Docker): `server.port=8080`, dialect PostgreSQL.

## Endpoints Principales

- Franquicias:
  - `POST /franchise`: crear.
  - `GET /franchise`: listar todas.
  - `GET /franchise/{id}`: obtener por ID.
  - `PUT /franchise/{id}`: actualizar nombre.
  - `DELETE /franchise/{id}`: eliminar.

- Sucursales:
  - `POST /franchise/{franchiseId}/branch`: crear en franquicia.
  - `GET /franchise/{franchiseId}/branch/{branchId}`: obtener por ID.
  - `DELETE /franchise/{franchiseId}/branch/{branchId}`: eliminar.

- Productos:
  - `POST /branch/{branchId}/product`: crear en sucursal.
  - `GET /branch/{branchId}/product`: listar por sucursal.
  - `GET /branch/{branchId}/product/moreStock`: mayor stock.
  - `GET /branch/{branchId}/product/lessStock/{stock}`: stock menor a `stock`.
  - `PUT /product/{productId}`: actualizar.
  - `DELETE /product/{productId}`: eliminar.

## Validaciones

- Stock no negativo en productos.
- Unicidad de nombre por sucursal (productos) y por franquicia (sucursales).
- Manejo de errores con excepciones de dominio.

## Observabilidad y Salud

- Swagger: `http://localhost:8081/swagger-ui/index.html` (dev) / `http://localhost:8080/swagger-ui/index.html` (prod Docker).
- Salud DB: `GET /health/db` devuelve proveedor, URL y estado de conexión.
- Actuator: `management.endpoints.web.exposure.include=*`.

## Desarrollo

1. Arranca con H2: `./mvnw spring-boot:run`.
2. Con Postgres (dev): `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev -DskipTests`.
3. Flujo sugerido:
   - Crea una franquicia.
   - Crea una sucursal dentro de esa franquicia.
   - Crea productos en la sucursal y prueba validaciones.
   - Actualiza y elimina para verificar CRUD.

## Notas

- Si cambias modelos, revisa los mapeadores DTO y adaptadores de persistencia.
- DevTools está activo en desarrollo; cambios suelen recargar automáticamente.