package com.example.hostadmin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "empleado")
@Data
public class Empleado {

    @Id
    @NotBlank(message = "El rut es obligatorio")
    @Size(min = 8, max = 9, message = "El rut debe tener entre 8 y 9 caracteres")
    @Column(nullable = false, length = 9)
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "La ocupación es obligatoria")
    @Size(min = 3, max = 100, message = "La ocupación debe tener entre 3 y 100 caracteres")
    private String ocupacion;

    @NotBlank(message = "El turno es obligatorio")
    @Size(min = 3, max = 10, message = "El turno debe tener entre 3 y 10 caracteres")
    @Column(nullable = false, length = 10)
    private String turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_empleado_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "empleados"})
    private TipoEmpleado tipoEmpleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostal_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "comuna"})
    private Hostal hostal;


}