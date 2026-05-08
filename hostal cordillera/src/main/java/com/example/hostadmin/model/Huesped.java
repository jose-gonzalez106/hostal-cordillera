package com.example.hostadmin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data

public class Huesped {
    @Id
    @NotBlank (message = "el run es obligatorio")
    @Size(max = 100, message = "El run debe tener un max  de 9 caracteres")
    @Column(nullable = false, length = 9)
    private String run;  
    
    @NotBlank (message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank (message = "El apellido es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String apellido;

    @NotBlank (message = "el telefono es pbligatorio")
    @Size( max = 9, message = "El nombre debe tener un maximo de  9 caracteres")
    @Column(nullable = false, length = 9)
    private String telefono;

    @NotBlank (message = "El correo es obligatorio")
    @Size(min = 3, max = 100, message = "El correo debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String correo;

    @ManyToOne
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;

    @OneToMany(mappedBy = "huesped")
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "huesped")
    private List<Resena> resenas;
}