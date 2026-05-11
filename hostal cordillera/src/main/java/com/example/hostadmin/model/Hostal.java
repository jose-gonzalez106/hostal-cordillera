package com.example.hostadmin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "hostales")
@Data
public class Hostal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 3, max = 100, message = "La dirección debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String direccion;

    @NotBlank(message = "El rut empresa es obligatorio")
    @Size(min = 8, max = 9, message = "El rut debe tener entre 8 y 9 caracteres")
    @Column(nullable = false, length = 9, unique = true)
    private String rutEmpresa;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 3, max = 100, message = "La ciudad debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comuna_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "region"})
    private Comuna comuna;
}
