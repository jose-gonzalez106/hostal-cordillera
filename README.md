# Hostal Cordillera – Sistema de Gestión

## Integrantes
- José González
- Javier Calquin

---

# Descripción

API REST para la gestión de un hostal, construida con Spring Boot 3, JPA/Hibernate y MySQL.

El sistema permite administrar:

- Regiones
- Comunas
- Hostales
- Habitaciones
- Huéspedes
- Empleados
- Reservas
- Pagos
- Reseñas

El proyecto fue desarrollado bajo una arquitectura modular basada en el patrón CSR (Controller – Service – Repository), permitiendo separación de responsabilidades, mantenibilidad y escalabilidad futura hacia una arquitectura de microservicios.

---

# Tecnologías Utilizadas

- Java 21
- Spring Boot 3.5
- Spring Data JPA
- Hibernate ORM
- MySQL 8
- Maven
- Lombok
- Bean Validation (JSR 380)
- SLF4J Logging
- Postman

---

# Arquitectura del Proyecto

El proyecto implementa el patrón CSR:

## Controller
Recibe solicitudes HTTP REST, valida entradas y delega operaciones a la capa de servicio.

## Service
Contiene la lógica de negocio, validaciones, manejo de reglas del dominio y logs del sistema.

## Repository
Gestiona el acceso a datos utilizando `JpaRepository`.

## Model
Contiene las entidades JPA y relaciones entre tablas.

## DTO
Objetos de transferencia de datos utilizados para validación y comunicación segura entre capas.

## Exceptions
Manejo centralizado de errores mediante:

- `@RestControllerAdvice`
- excepciones personalizadas
- respuestas HTTP controladas

---

# Modelo del Dominio

```text
Región → Comuna → Hostal → Habitación
                           ↓
                       Reserva
                           ↓
                         Pago

Huésped → Reserva → Reseña
Empleado → TipoEmpleado
