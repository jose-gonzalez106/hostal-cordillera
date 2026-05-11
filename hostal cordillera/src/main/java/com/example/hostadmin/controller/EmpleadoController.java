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

import com.example.hostadmin.DTO.EmpleadoDTO;
import com.example.hostadmin.model.Empleado;
import com.example.hostadmin.service.EmpleadoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        List<EmpleadoDTO> empleados = empleadoService.obtenerTodos();
        if (!empleados.isEmpty()) {
            return new ResponseEntity<>(empleados, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay empleados", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<?> obtenerPorRut(@PathVariable String rut) {
        EmpleadoDTO empleado = empleadoService.buscarPorRut(rut);
        return new ResponseEntity<>(empleado, HttpStatus.OK);
    }

    @PostMapping("/hostal/{hostalId}/tipo/{tipoId}")
    public ResponseEntity<?> crear(@PathVariable Long hostalId,
                                    @PathVariable Long tipoId,
                                    @Valid @RequestBody Empleado empleado) {
        return new ResponseEntity<>(empleadoService.guardar(hostalId, tipoId, empleado), HttpStatus.CREATED);
    }

    @PutMapping("/{rut}")
    public ResponseEntity<?> actualizar(@PathVariable String rut,
                                        @Valid @RequestBody Empleado empleado) {
        return new ResponseEntity<>(empleadoService.actualizar(rut, empleado), HttpStatus.OK);
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminar(@PathVariable String rut) {
        return new ResponseEntity<>(empleadoService.eliminar(rut), HttpStatus.OK);
    }
}
