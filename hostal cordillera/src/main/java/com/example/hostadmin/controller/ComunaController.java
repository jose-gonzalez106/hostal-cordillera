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

import com.example.hostadmin.DTO.ComunaDTO;
import com.example.hostadmin.model.Comuna;
import com.example.hostadmin.service.ComunaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        List<ComunaDTO> comunas = comunaService.obtenerTodas();
        if (!comunas.isEmpty()) {
            return new ResponseEntity<>(comunas, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay comunas", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            ComunaDTO comuna = comunaService.buscarPorId(id);
            return new ResponseEntity<>(comuna, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontró la comuna", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/region/{regionId}")
    public ResponseEntity<?> crear(@PathVariable Long regionId,
                                    @Valid @RequestBody Comuna comuna) {
        try {
            return new ResponseEntity<>(comunaService.guardar(regionId, comuna), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                         @Valid @RequestBody Comuna comuna) {
        try {
            return new ResponseEntity<>(comunaService.actualizar(id, comuna), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(comunaService.eliminar(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}