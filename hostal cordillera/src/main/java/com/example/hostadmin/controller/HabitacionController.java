package com.example.hostadmin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hostadmin.DTO.HabitacionDTO;
import com.example.hostadmin.model.Habitacion;
import com.example.hostadmin.service.HabitacionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        List<HabitacionDTO> habitaciones = habitacionService.obtenerTodas();
        if (!habitaciones.isEmpty()) {
            return new ResponseEntity<>(habitaciones, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay habitaciones", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{numero}")
    public ResponseEntity<?> obtenerPorNumero(@PathVariable Integer numero) {
        HabitacionDTO habitacion = habitacionService.buscarPorNumero(numero);
        return new ResponseEntity<>(habitacion, HttpStatus.OK);
    }

    @PostMapping("/hostal/{hostalId}")
    public ResponseEntity<?> crear(@PathVariable Long hostalId,
                                    @Valid @RequestBody Habitacion habitacion) {
        return new ResponseEntity<>(habitacionService.guardar(hostalId, habitacion), HttpStatus.CREATED);
    }

    @PutMapping("/{numero}")
    public ResponseEntity<?> actualizar(@PathVariable Integer numero,
                                        @Valid @RequestBody Habitacion habitacion) {
        return new ResponseEntity<>(habitacionService.actualizar(numero, habitacion), HttpStatus.OK);
    }

    @PatchMapping("/{numero}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer numero,
                                        @Valid @RequestBody HabitacionDTO dto) {
        return new ResponseEntity<>(habitacionService.cambiarEstado(numero, dto.getEstado()), HttpStatus.OK);
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<?> eliminar(@PathVariable Integer numero) {
        return new ResponseEntity<>(habitacionService.eliminar(numero), HttpStatus.OK);
    }
}
