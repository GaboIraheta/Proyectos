# WarehouseInventory

Backend Spring Boot con JWT en cookie, CSRF, roles y gestión de inventario para almacenes.

📌 Resumen del proyecto

WarehouseInventory es una API REST modular para la operación de un sistema de almacenes. La aplicación cubre autenticación, gestión de productos, control de inventario, reservas, bodegas, ubicaciones de almacenamiento y reportes operativos.

El proyecto está diseñado para operar con PostgreSQL y utiliza JWT firmado con RSA en `access_token` cookie, junto a protección CSRF basada en cookies.

🧩 Tecnologías principales

- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Security
- Spring Validation
- Spring Web MVC
- Springdoc OpenAPI
- PostgreSQL
- Lombok
- Nimbus JWT

📁 Estructura importante

- `src/main/java/org/example/warehouseinventory/WarehouseInventoryApplication.java` - punto de entrada de la app.
- `src/main/java/org/example/warehouseinventory/auth/` - autenticación, login, logout y registro.
- `src/main/java/org/example/warehouseinventory/catalog/` - catálogo de productos.
- `src/main/java/org/example/warehouseinventory/inventory/` - registro de ingresos y consumo de stock.
- `src/main/java/org/example/warehouseinventory/order/` - reservas de inventario.
- `src/main/java/org/example/warehouseinventory/warehouse/` - bodegas, ubicaciones y políticas de asignación.
- `src/main/java/org/example/warehouseinventory/reporting/` - reportes ABC, sugerencias de reorden y notificaciones.
- `src/main/java/org/example/warehouseinventory/shared/` - respuestas generales, excepciones, utilidades y configuración compartida.
- `src/main/resources/application.yaml` - configuración principal.

🔒 Diseño de seguridad

Seguridad general

- `SecurityConfig` habilita seguridad sin estado (`STATELESS`) y desactiva login por formulario.
- La autorización se define por roles en los controladores con `@PreAuthorize`.
- Se usan JWT dentro de la cookie `access_token`.
- El filtro `cookieTokenFilter()` convierte la cookie en el header `Authorization: Bearer <token>` para Spring Security.
- Se aceptan sin autenticación: `/api/auth/login`, `/api/auth/logout`, `/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs/**`.
- El resto de rutas requiere autenticación.

Autenticación JWT

- `JwtService` genera tokens con una clave RSA generada al arranque.
- El token incluye el `subject` con el username y el claim `role`.
- Al iniciar sesión, el token se envía al cliente en la cookie `access_token`.
- También existe `CsrfTokenRepository` basado en cookies para soportar CSRF.

Roles

- El sistema define roles como `ADMIN`, `WAREHOUSE_MANAGER`, `OPERATOR`.
- El `JwtAuthenticationConverter` asigna la autoridad `ROLE_<ROL>` desde el claim `role`.
- Los endpoints usan `@PreAuthorize` para restringir acceso según rol.

🧠 Comportamiento de CSRF

- CSRF está habilitado por defecto (`app.csrf-enabled=true`).
- Se utiliza `CookieCsrfTokenRepository.withHttpOnlyFalse()`.
- `/api/auth/login` y `/api/auth/logout` están excluidos de la verificación CSRF.

🧾 Modelo de datos principal

El proyecto tiene entidades para:

- `User` - usuario con `username`, `password` cifrado (BCrypt), `role` y `active`.
- `Product` - productos del catálogo con SKU, categoría y estado activo/inactivo.
- `Warehouse` - almacenes físicos.
- `StorageLocation` - ubicaciones dentro de un almacén.
- `Reservation` - reservas de inventario.
- `CyclicCount` - controles cíclicos de inventario.
- `ReorderSuggestion` / `Notification` - sugerencias de reorden y notificaciones operativas.

📦 Endpoints disponibles

### Auth

- `POST /api/auth/login`
  - body: `{ "username": "...", "password": "..." }`
  - devuelve cookie `access_token` y token CSRF.
- `POST /api/auth/logout`
  - cierra sesión y borra cookie.
- `POST /api/auth/register`
  - requiere rol `ADMIN`.
  - registra nuevo usuario.

### Catálogo de productos

- `POST /api/product` - crear producto.
- `GET /api/product/id/{id}` - obtener producto por ID.
- `GET /api/product/sku/{sku}` - obtener producto por SKU.
- `GET /api/product/category/{category}` - filtrado por categoría.
- `GET /api/product/inactive` - obtener productos inactivos.
- `GET /api/product/id/inactive/{id}` - producto inactivo por ID.
- `PUT /api/product/update/{id}` - actualizar producto.
- `DELETE /api/product/delete/{id}` - desactivar producto.
- `PUT /api/product/activate/{id}` - activar producto.

### Inventario

- `POST /api/inventory/entry` - registrar ingreso de inventario.
- `POST /api/inventory/consume` - consumir stock.

### Almacenes y ubicaciones

- `POST /api/warehouse` - crear almacén.
- `GET /api/warehouse` - listar almacenes.
- `GET /api/warehouse/{id}` - obtener almacén por ID.
- `PUT /api/warehouse/{id}` - actualizar almacén.
- `DELETE /api/warehouse/{id}` - desactivar almacén.
- `PATCH /api/warehouse/{id}/activate` - activar almacén.
- `POST /api/storage-location` - crear ubicación de almacenamiento.
- `GET /api/storage-location/warehouse/{warehouse}` - ubicaciones por almacén.
- `GET /api/storage-location/{id}` - ubicar por ID.

### Políticas de almacén

- `GET /api/warehouse-policy/{warehouse}` - obtener política de asignación.
- `PUT /api/warehouse-policy/{warehouse}?strategy=<strategy>` - actualizar estrategia de asignación.

### Reservas

- `POST /api/reservation` - crear reserva.
- `PUT /api/reservation/confirm/{id}` - confirmar reserva.
- `PUT /api/reservation/release/{id}` - liberar reserva.

### Reporting

- `GET /api/reporting/abc?from=<YYYY-MM-DD>&to=<YYYY-MM-DD>` - generar reporte ABC.
- `GET /api/reorder-suggestions` - obtener todas las sugerencias de reorden.
- `GET /api/reorder-suggestions/pending` - sugerencias pendientes.
- `PATCH /api/reorder-suggestions/{id}/attended` - marcar sugerencia como atendida.

### Notificaciones

- `GET /api/notifications` - obtener todas las notificaciones.
- `GET /api/notifications/unread` - notificaciones no leídas.
- `PATCH /api/notifications/{id}/read` - marcar notificación como leída.
- `GET /api/notifications/active` - notificaciones activas.

### Conteos cíclicos

- `POST /api/cyclic-counts` - crear conteo cíclico.
- `PATCH /api/cyclic-counts/{id}/start` - iniciar conteo.
- `PATCH /api/cyclic-counts/{id}/submit` - enviar conteo físico.
- `GET /api/cyclic-counts` - listar todos los conteos.
- `GET /api/cyclic-counts/status/{status}` - filtrar por estado.

⚙️ DTOs y respuestas

El proyecto usa DTOs de petición y respuesta en cada módulo para validar datos y homogeneizar las respuestas.

- `LoginRequest`, `RegisterRequest`
- `CreateProductRequest`, `UpdateProductRequest`
- `InventoryEntryRequest`, `StockConsumptionRequest`
- `CreateWarehouseRequest`, `UpdateWarehouseRequest`
- `CreateStorageLocationRequest`
- `ReservationRequest`
- `CreateCyclicCountRequest`, `SubmitPhysicalCountRequest`
- `GeneralResponse` - respuesta estándar de la API.

📦 Variables de entorno usadas

- `DB_URL` - URL de conexión PostgreSQL.
- `DB_USER` - usuario de la base de datos.
- `DB_PASSWORD` - contraseña de la base de datos.
- `SPRING_PROFILES_ACTIVE` - perfil activo (`dev`, `prod`).
- `SERVER_PORT` - puerto de la aplicación.
- `app.csrf-enabled` - habilita/deshabilita CSRF en `SecurityConfig`.

🚀 Ejecución local

```bash
./mvnw clean package
./mvnw spring-boot:run
```

O con Maven instalado:

```bash
mvn clean package
mvn spring-boot:run
```

🐳 Docker

Este repositorio no incluye `Dockerfile` ni `docker-compose.yml` por defecto.

📄 Swagger / OpenAPI

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

🧪 Pruebas

```bash
./mvnw test
```

✅ Observaciones del código

- El token JWT se guarda en cookie `access_token` y se lee con un filtro personalizado antes de la autenticación.
- `SecurityConfig` convierte la cookie en encabezado `Authorization` y valida JWT con RSA.
- CSRF se maneja con `CookieCsrfTokenRepository` y se ignoran las rutas de login/logout.
- `WarehouseInventoryApplication` crea un admin por defecto en perfiles distintos a `test`.
- `spring.jpa.hibernate.ddl-auto=update` sincroniza el esquema automáticamente en desarrollo.

📝 Recomendaciones

- Crea `.env` o define variables de entorno antes de ejecutar.
- Verifica la conexión PostgreSQL con `DB_URL`, `DB_USER` y `DB_PASSWORD`.
- Prueba primero `/api/auth/login` y luego los endpoints protegidos.
- Usa Swagger para explorar rutas y payloads.
