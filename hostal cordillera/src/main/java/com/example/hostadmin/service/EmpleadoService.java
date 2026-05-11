package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.EmpleadoDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Empleado;
import com.example.hostadmin.model.Hostal;
import com.example.hostadmin.model.TipoEmpleado;
import com.example.hostadmin.repository.EmpleadoRepository;
import com.example.hostadmin.repository.HostalRepository;
import com.example.hostadmin.repository.TipoEmpleadoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private HostalRepository hostalRepository;

    @Autowired
    private TipoEmpleadoRepository tipoEmpleadoRepository;


    public List<EmpleadoDTO> obtenerTodos() {
        log.info("[EmpleadoService] Obteniendo todos los empleados");
        return empleadoRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public EmpleadoDTO buscarPorRut(String rut) {
        log.info("[EmpleadoService] Buscando empleado con rut: {}", rut);
        Empleado empleado = empleadoRepository.findById(rut)
        .orElseThrow(() -> {
            log.warn("[EmpleadoService] Empleado {} no encontrado", rut);
            return new RecursoNoEncontradoException("empleado " + rut + " no encontrado");
        });
        return convertirADTO(empleado);
    }

    public Empleado guardar(Long hostalId, Long tipoId, Empleado empleado) {
        log.info("[EmpleadoService] Registrando empleado con rut: {}", empleado.getRut());
        if (empleadoRepository.existsById(empleado.getRut())) {
            log.warn("[EmpleadoService] Ya existe empleado con rut: {}", empleado.getRut());
            throw new ValidacionException("ya existe el empleado " + empleado.getRut());
        }
        Hostal hostal = hostalRepository.findById(hostalId)
        .orElseThrow(() -> {
            log.warn("[EmpleadoService] Hostal {} no encontrado", hostalId);
            return new RecursoNoEncontradoException("el hostal " + hostalId + " no existe");
        });
        TipoEmpleado tipo = tipoEmpleadoRepository.findById(tipoId)
        .orElseThrow(() -> {
            log.warn("[EmpleadoService] TipoEmpleado {} no encontrado", tipoId);
            return new RecursoNoEncontradoException("id tipo " + tipoId + " no existe");
        });
        empleado.setHostal(hostal);
        empleado.setTipoEmpleado(tipo);
        Empleado guardado = empleadoRepository.save(empleado);
        log.info("[EmpleadoService] Empleado {} guardado exitosamente", guardado.getRut());
        return guardado;
    }

    public Empleado actualizar(String rut, Empleado empleado) {
        log.info("[EmpleadoService] Actualizando empleado con rut: {}", rut);
        Empleado existente = empleadoRepository.findById(rut)
        .orElseThrow(() -> {
            log.warn("[EmpleadoService] Empleado {} no encontrado para actualizar", rut);
            return new RecursoNoEncontradoException("empleado " + rut + " no encontrado");
        });
        if (empleado.getNombre() != null) {
            existente.setNombre(empleado.getNombre());
        }
        if (empleado.getOcupacion() != null) {
            existente.setOcupacion(empleado.getOcupacion());
        }
        if (empleado.getTurno() != null) {
            existente.setTurno(empleado.getTurno());
        }
        log.info("[EmpleadoService] Empleado {} actualizado", rut);
        return empleadoRepository.save(existente);
    }

    public String eliminar(String rut) {
        log.info("[EmpleadoService] Eliminando empleado con rut: {}", rut);
        Empleado empleado = empleadoRepository.findById(rut)
        .orElseThrow(() -> {
            log.warn("[EmpleadoService] Empleado {} no encontrado para eliminar", rut);
            return new RecursoNoEncontradoException("empleado " + rut + " no encontrado");
        });
        empleadoRepository.delete(empleado);
        log.info("[EmpleadoService] Empleado {} eliminado", rut);
        return "empleado " + empleado.getNombre() + " eliminado ";
    }

    private EmpleadoDTO convertirADTO(Empleado empleado) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setRut(empleado.getRut());
        dto.setNombre(empleado.getNombre());
        dto.setOcupacion(empleado.getOcupacion());
        dto.setTurno(empleado.getTurno());
        if (empleado.getTipoEmpleado() != null) {
            dto.setTipoEmpleado(empleado.getTipoEmpleado().getCategoria());
        } else {
            dto.setTipoEmpleado(" tipo no asignado ");
        }
        return dto;
    }

}
