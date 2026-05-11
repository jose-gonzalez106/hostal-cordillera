package com.example.hostadmin.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "reservas")
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de check-in es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de término es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaTermino;

    @Column(nullable = false)
    private BigDecimal precio;

    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 3, max = 10, message = "El estado debe tener entre 3 y 10 caracteres")
    @Column(nullable = false, length = 10)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "huesped_run", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "reservas", "resenas", "comuna"})
    private Huesped huesped;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habitacion_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hostal"})
    private Habitacion habitacion;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Pago> pagos;
}