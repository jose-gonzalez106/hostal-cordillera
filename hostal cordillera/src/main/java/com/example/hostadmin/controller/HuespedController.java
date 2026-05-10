package com.example.hostadmin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.hostadmin.DTO.HuespedDTO;
import com.example.hostadmin.model.Huesped;
import com.example.hostadmin.service.HuespedService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/huespedes")
public class HuespedController {

    @Autowired
    private HuespedService huespedService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<HuespedDTO> huespedes = huespedService.obtenerTodos();
        if (!huespedes.isEmpty()) {
            return new ResponseEntity<>(huespedes, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay huespedes", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{run}")
    public ResponseEntity<?> obtenerPorRun(@PathVariable String run) {
        try {
            HuespedDTO huesped = huespedService.buscarPorRun(run);
            return new ResponseEntity<>(huesped, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontró el huesped", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/comuna/{comunaId}")
    public ResponseEntity<?> crear(@PathVariable Long comunaId,
                                    @Valid @RequestBody Huesped huesped) {
        try {
            return new ResponseEntity<>(huespedService.guardar(comunaId, huesped), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{run}")
    public ResponseEntity<?> actualizar(@PathVariable String run,
                                        @Valid @RequestBody Huesped huesped) {
        try {
            return new ResponseEntity<>(huespedService.actualizar(run, huesped), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{run}")
    public ResponseEntity<?> eliminar(@PathVariable String run) {
        try {
            return new ResponseEntity<>(huespedService.eliminar(run), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}