package com.example.hostadmin.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Entity
@Table(name = "pagos")
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    @Column(nullable = false)
    private BigDecimal montoTotal;

    @Column(nullable = false)
    private LocalDate fechaPago;

    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private String estadoPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "huesped", "habitacion", "pagos"})
    private Reserva reserva;
}