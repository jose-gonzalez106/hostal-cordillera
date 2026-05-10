package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.EmpleadoDTO;
import com.example.hostadmin.model.Empleado;
import com.example.hostadmin.model.Hostal;
import com.example.hostadmin.model.TipoEmpleado;
import com.example.hostadmin.repository.EmpleadoRepository;
import com.example.hostadmin.repository.HostalRepository;
import com.example.hostadmin.repository.TipoEmpleadoRepository;

import jakarta.transaction.Transactional;

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
        return empleadoRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public EmpleadoDTO buscarPorRut(String rut) {
        Empleado empleado = empleadoRepository.findById(rut)
        .orElseThrow(() -> new RuntimeException("empleado " + rut + " no encontrado"));
        return convertirADTO(empleado);
    }

    public Empleado guardar(Long hostalId, Long tipoId, Empleado empleado) {
        if (empleadoRepository.existsById(empleado.getRut())) {
            throw new RuntimeException("ya existe el empleaod " + empleado.getRut());
        }
        Hostal hostal = hostalRepository.findById(hostalId)
        .orElseThrow(() -> new RuntimeException("el hostal " + hostalId + " no existe"));
        TipoEmpleado tipo = tipoEmpleadoRepository.findById(tipoId)
        .orElseThrow(() -> new RuntimeException("id tipo " + tipoId + " no existe"));
        empleado.setHostal(hostal);
        empleado.setTipoEmpleado(tipo);
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizar(String rut, Empleado empleado) {
        Empleado existente = empleadoRepository.findById(rut)
        .orElseThrow(() -> new RuntimeException("empleado " + rut + " no encontrado"));
        if (empleado.getNombre() != null) {
            existente.setNombre(empleado.getNombre());
        }
        if (empleado.getOcupacion() != null) {
            existente.setOcupacion(empleado.getOcupacion());
        }
        if (empleado.getTurno() != null) {
            existente.setTurno(empleado.getTurno());
        }
        return empleadoRepository.save(existente);
    }

    public String eliminar(String rut) {
        try {
            Empleado empleado = empleadoRepository.findById(rut)
            .orElseThrow(() -> new RuntimeException("empleado " + rut + " no encontrado"));
            empleadoRepository.delete(empleado);
            return "empleado " + empleado.getNombre() + " eliminado ";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
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
