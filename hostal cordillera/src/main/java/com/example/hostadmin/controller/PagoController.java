package com.example.hostadmin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.hostadmin.DTO.PagoDTO;
import com.example.hostadmin.model.Pago;
import com.example.hostadmin.service.PagoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<PagoDTO> pagos = pagoService.obtenerTodos();
        if (!pagos.isEmpty()) {
            return new ResponseEntity<>(pagos, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay pagos", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        PagoDTO pago = pagoService.buscarPorId(id);
        return new ResponseEntity<>(pago, HttpStatus.OK);
    }

    @PostMapping("/reserva/{reservaId}")
    public ResponseEntity<?> registrar(@PathVariable Long reservaId,
                                        @Valid @RequestBody Pago pago) {
        return new ResponseEntity<>(pagoService.registrar(reservaId, pago), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return new ResponseEntity<>(pagoService.eliminar(id), HttpStatus.OK);
    }
}
