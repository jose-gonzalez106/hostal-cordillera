# Hostal Cordillera – Sistema de Gestión

## Integrantes
- José González
- Javier Calquin

## Descripción
API REST para la gestión de un hostal, construida con Spring Boot 3, JPA/Hibernate y MySQL.
Permite administrar regiones, comunas, hostales, habitaciones, huéspedes, empleados, reservas, pagos y reseñas.

## Tecnologías
- Java 21
- Spring Boot 3.5
- Spring Data JPA + Hibernate
- MySQL 8
- Lombok
- Bean Validation (JSR 380)

## Arquitectura
El proyecto sigue el patrón CSR (Controller – Service – Repository):
- **controller/** → recibe peticiones REST y delega al servicio
- **service/** → lógica de negocio, validaciones, logs SLF4J
- **repository/** → acceso a datos via JpaRepository
- **model/** → entidades JPA
- **DTO/** → objetos de transferencia de datos
- **exceptions/** → GlobalExceptionHandler (@RestControllerAdvice) + excepciones personalizadas

## Cómo ejecutar

### Requisitos previos
- Java 21 instalado
- MySQL 8 corriendo en `localhost:3306`
- Maven instalado (o usar el `mvnw` incluido)

### Pasos
1. Crear la base de datos en MySQL:
   ```sql
   CREATE DATABASE hostaldb;
   ```
2. Ajustar credenciales en `src/main/resources/application.properties` si es necesario.
3. Ejecutar:
   ```bash
   ./mvnw spring-boot:run
   ```
4. La API estará disponible en `http://localhost:8080`

## Endpoints principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /api/v1/regiones | Listar regiones |
| POST | /api/v1/regiones | Crear región |
| GET | /api/v1/comunas | Listar comunas |
| POST | /api/v1/comunas/region/{id} | Crear comuna |
| GET | /api/v1/hostales | Listar hostales |
| POST | /api/v1/hostales/comuna/{id} | Crear hostal |
| GET | /api/v1/habitaciones | Listar habitaciones |
| POST | /api/v1/habitaciones/hostal/{id} | Crear habitación |
| GET | /api/v1/huespedes | Listar huéspedes |
| POST | /api/v1/huespedes/comuna/{id} | Registrar huésped |
| GET | /api/v1/reservas | Listar reservas |
| POST | /api/v1/reservas/huesped/{run}/habitacion/{numero} | Crear reserva |
| PATCH | /api/v1/reservas/{id}/cancelar | Cancelar reserva |
| POST | /api/v1/pagos/reserva/{id} | Registrar pago |
| GET | /api/v1/empleados | Listar empleados |
| POST | /api/v1/empleados/hostal/{hId}/tipo/{tId} | Crear empleado |
| GET | /api/v1/resenas | Listar reseñas |
| POST | /api/v1/resenas/huesped/{run} | Crear reseña |

## Manejo de errores
Todos los errores son manejados centralizadamente por `GlobalExceptionHandler`:
- `RecursoNoEncontradoException` → HTTP 404
- `ValidacionException` → HTTP 400
- Errores de Bean Validation → HTTP 400 con detalle por campo
- Errores inesperados → HTTP 500
