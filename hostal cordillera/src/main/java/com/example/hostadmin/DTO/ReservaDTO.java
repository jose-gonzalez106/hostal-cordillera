package com.example.hostadmin.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ReservaDTO {

    private long id;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private BigDecimal precio;

}
