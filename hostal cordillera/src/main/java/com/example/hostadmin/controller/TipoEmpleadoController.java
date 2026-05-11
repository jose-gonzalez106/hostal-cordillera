package com.example.hostadmin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.hostadmin.DTO.TipoEmpleadoDTO;
import com.example.hostadmin.model.TipoEmpleado;
import com.example.hostadmin.service.TipoEmpleadoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tipos-empleado")
public class TipoEmpleadoController {

    @Autowired
    private TipoEmpleadoService tipoEmpleadoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<TipoEmpleadoDTO> tipos = tipoEmpleadoService.obtenerTodos();
        if (!tipos.isEmpty()) {
            return new ResponseEntity<>(tipos, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay tipos de empleado", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        TipoEmpleadoDTO tipo = tipoEmpleadoService.buscarPorId(id);
        return new ResponseEntity<>(tipo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody TipoEmpleado tipo) {
        return new ResponseEntity<>(tipoEmpleadoService.guardar(tipo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return new ResponseEntity<>(tipoEmpleadoService.eliminar(id), HttpStatus.OK);
    }
}
