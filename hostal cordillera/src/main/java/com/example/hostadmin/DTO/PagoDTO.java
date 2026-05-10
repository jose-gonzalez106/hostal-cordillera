package com.example.hostadmin.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class PagoDTO {

    private Long id;
    private BigDecimal montoTotal;
    private LocalDate fechaPago;
    private String metodoPago;
    private String estadoPago;
    private Long reservaId; 
}
