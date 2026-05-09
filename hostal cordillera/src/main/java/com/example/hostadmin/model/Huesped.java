package com.example.hostadmin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "huespedes")
@Data
public class Huesped {

    @Id
    @NotBlank(message = "El run es obligatorio")
    @Size(min = 8, max = 9, message = "El run debe tener entre 8 y 9 caracteres")
    @Column(nullable = false, length = 9)
    private String run;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 100, message = "El apellido debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String apellido;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 9, max = 9, message = "El teléfono debe tener 9 caracteres")
    @Column(nullable = false, length = 9)
    private String telefono;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(nullable = false, length = 100, unique = true)
    private String correo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comuna_id", nullable = false)
    private Comuna comuna;

    @JsonIgnore
    @OneToMany(mappedBy = "huesped", fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    @JsonIgnore
    @OneToMany(mappedBy = "huesped", fetch = FetchType.LAZY)
    private List<Resena> resenas;
}