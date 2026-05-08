package com.example.hostadmin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "la fecha de check-in es obligatorio")
    @Column(nullable = false, length = 100)
    private LocalDate fechaInicio;


    private LocalDate fechaTermino;

    
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "huesped_run")
    private Huesped huesped;

    @ManyToOne
    @JoinColumn(name = "habitacion_numero")
    private Habitacion habitacion;

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL)
    private Pago pago;
}
