package com.example.hostadmin.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ResenniaDTO {

    private long id;
    private String comentario;
    private Integer calificacion;
    private LocalDate fecha;
    private String huesped;
    private String hostal;
}
