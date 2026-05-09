package com.example.hostadmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "habitaciones")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @NotBlank(message = "La categoria es obligatorio")
    @Size(min = 3, max = 20, message = "La categoria debe tener entre 3 y 20 caracteres")
    @Column(nullable = false, length = 20)
    private String categoria;

    @Builder.Default
    @Min(value = 1, message = "La capacidad mínima es 1")
    @Max(value = 4, message = "La capacidad máxima es 4")
    @Column(nullable = false)
    private Integer capacidad = 1;

    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 3, max = 10, message = "El estado debe tener entre 3 y 10 caracteres")
    @Column(nullable = false, length = 10)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostal_id", nullable = false)
    private Hostal hostal;
}