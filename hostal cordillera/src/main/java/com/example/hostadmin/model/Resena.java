package com.example.hostadmin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Resena { //SI, ES RESEÑA, PERO NO PODEMOS USAR LA Ñ EN EL NOMBRE DE LA CLASE

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100, message = "la resenia debe tener un maximo de  100 caracteres")
    @Column(nullable = false, length = 100)
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "huesped_run")
    private Huesped huesped;
}
