package com.example.hostadmin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Id
    private Integer numero;   

    @NotBlank (message = "La categoria  es obligatorio")
    @Size(min = 3, max = 100, message = "lLa categoria debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String categoria; 

    @Builder.Default
    @Min(value = 1, message = "la capacidad minima es 1")
    @Max(value = 4, message = "la capacidad maxima es 4")
    @Column(nullable = false)
    private Integer capacidad = 1;

    @NotBlank (message = "El estado es obligatorio")
    @Size(min = 3, max = 20, message = "El estado debe tener entre 3 y 20 caracteres")
    @Column(nullable = false, length = 20)
    private String estado;    

    @ManyToOne
    @JoinColumn(name = "hostal_id")
    private Hostal hostal;
}
