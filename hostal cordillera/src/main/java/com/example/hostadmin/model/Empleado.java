package com.example.hostadmin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Empleado {
    @Id
    @NotBlank (message = "el run es obligatorio")
    @Size(max = 100, message = "El run debe tener un max  de 9 caracteres")
    @Column(nullable = false, length = 9)
    private String rut;   // RUT como PK

    @NotBlank (message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)  
    private String nombre;

    @NotBlank (message = "El cargo es obligatorio")
    @Size(min = 3, max = 100, message = "El cargo debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String ocupacion; //cargo

    @NotBlank (message = "El turno es obligatorio")
    @Size(min = 3, max = 6, message = "El turno debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 6)
    private String turno;   // "mañana", "tarde", "noche"

    @ManyToOne
    @JoinColumn(name = "tipo_empleado_id")
    private TipoEmpleado tipoEmpleado;

    @ManyToOne
    @JoinColumn(name = "hostal_id")
    private Hostal hostal;
}