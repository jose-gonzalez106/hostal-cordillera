package com.example.hostadmin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.hostadmin.DTO.ReservaDTO;
import com.example.hostadmin.model.Reserva;
import com.example.hostadmin.service.ReservaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        List<ReservaDTO> reservas = reservaService.obtenerTodas();
        if (!reservas.isEmpty()) {
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay reservas", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            ReservaDTO reserva = reservaService.buscarPorId(id);
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontró la reserva", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/huesped/{run}")
    public ResponseEntity<?> obtenerPorHuesped(@PathVariable String run) {
        List<ReservaDTO> reservas = reservaService.buscarPorHuesped(run);
        if (!reservas.isEmpty()) {
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay reservas para este huesped", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/huesped/{run}/habitacion/{numero}")
    public ResponseEntity<?> crear(@PathVariable String run,
                                    @PathVariable Integer numero,
                                    @Valid @RequestBody Reserva reserva) {
        try {
            return new ResponseEntity<>(reservaService.crear(run, numero, reserva), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(reservaService.cancelar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(reservaService.eliminar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}