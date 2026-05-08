package com.example.hostadmin.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Double montoTotal;

    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
}