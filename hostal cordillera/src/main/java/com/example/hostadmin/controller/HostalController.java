package com.example.hostadmin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hostadmin.DTO.HostalDTO;
import com.example.hostadmin.model.Hostal;
import com.example.hostadmin.service.HostalService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/hostales")
public class HostalController {

    @Autowired
    private HostalService hostalService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<HostalDTO> hostales = hostalService.obtenerTodos();
        if (!hostales.isEmpty()) {
            return new ResponseEntity<>(hostales, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay hostales", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        HostalDTO hostal = hostalService.buscarPorId(id);
        return new ResponseEntity<>(hostal, HttpStatus.OK);
    }

    @PostMapping("/comuna/{comunaId}")
    public ResponseEntity<?> crear(@PathVariable Long comunaId,
                                    @Valid @RequestBody Hostal hostal) {
        return new ResponseEntity<>(hostalService.guardar(comunaId, hostal), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                         @Valid @RequestBody Hostal hostal) {
        return new ResponseEntity<>(hostalService.actualizar(id, hostal), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return new ResponseEntity<>(hostalService.eliminar(id), HttpStatus.OK);
    }
}
