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
import com.example.hostadmin.DTO.ResenniaDTO;
import com.example.hostadmin.model.Resena;
import com.example.hostadmin.service.ResenaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        List<ResenniaDTO> resenas = resenaService.obtenerTodas();
        if (!resenas.isEmpty()) {
            return new ResponseEntity<>(resenas, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay reseñas", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        ResenniaDTO resena = resenaService.buscarPorId(id);
        return new ResponseEntity<>(resena, HttpStatus.OK);
    }

    @GetMapping("/huesped/{run}")
    public ResponseEntity<?> obtenerPorHuesped(@PathVariable String run) {
        List<ResenniaDTO> resenas = resenaService.buscarPorHuesped(run);
        if (!resenas.isEmpty()) {
            return new ResponseEntity<>(resenas, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay reseñas para este huesped", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/huesped/{run}")
    public ResponseEntity<?> crear(@PathVariable String run,
            @Valid @RequestBody Resena resena) {
        return new ResponseEntity<>(resenaService.guardar(run, resena), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return new ResponseEntity<>(resenaService.eliminar(id), HttpStatus.OK);
    }
}
