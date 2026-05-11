package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.HabitacionDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Habitacion;
import com.example.hostadmin.model.Hostal;
import com.example.hostadmin.repository.HabitacionRepository;
import com.example.hostadmin.repository.HostalRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class HabitacionService {
    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private HostalRepository hostalRepository;


    public List<HabitacionDTO> obtenerTodas() {
        log.info("[HabitacionService] Obteniendo todas las habitaciones");
        return habitacionRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public HabitacionDTO buscarPorNumero(Integer numero) {
        log.info("[HabitacionService] Buscando habitacion numero: {}", numero);
        Habitacion habitacion = habitacionRepository.findByNumero(numero)
        .orElseThrow(() -> {
            log.warn("[HabitacionService] Habitacion {} no encontrada", numero);
            return new RecursoNoEncontradoException("la habitacion " + numero + " no existe");
        });
        return convertirADTO(habitacion);
    }

    public Habitacion guardar(Long hostalId, Habitacion habitacion) {
        log.info("[HabitacionService] Registrando habitacion numero {} en hostal {}", habitacion.getNumero(), hostalId);
        if (habitacionRepository.existsByNumero(habitacion.getNumero())) {
            log.warn("[HabitacionService] Ya existe habitacion con numero {}", habitacion.getNumero());
            throw new ValidacionException("ya existe una habitacion con el numero " + habitacion.getNumero());
        }
        validarEstado(habitacion.getEstado());
        Hostal hostal = hostalRepository.findById(hostalId)
        .orElseThrow(() -> {
            log.warn("[HabitacionService] Hostal {} no encontrado", hostalId);
            return new RecursoNoEncontradoException("el hostal " + hostalId + " no existe");
        });
        habitacion.setHostal(hostal);
        Habitacion guardada = habitacionRepository.save(habitacion);
        log.info("[HabitacionService] Habitacion {} guardada exitosamente", guardada.getNumero());
        return guardada;
    }

    public Habitacion actualizar(Integer numero, Habitacion habitacion) {
        log.info("[HabitacionService] Actualizando habitacion numero: {}", numero);
        Habitacion existente = habitacionRepository.findByNumero(numero)
        .orElseThrow(() -> {
            log.warn("[HabitacionService] Habitacion {} no encontrada para actualizar", numero);
            return new RecursoNoEncontradoException("la habitacion " + numero + " no existe");
        });
        if (habitacion.getCategoria() != null) {
            existente.setCategoria(habitacion.getCategoria());
        }
        if (habitacion.getCapacidad() != null) {
            existente.setCapacidad(habitacion.getCapacidad());
        }
        if (habitacion.getEstado() != null) {
            validarEstado(habitacion.getEstado());
            existente.setEstado(habitacion.getEstado());
        }
        log.info("[HabitacionService] Habitacion {} actualizada", numero);
        return habitacionRepository.save(existente);
    }

    public Habitacion cambiarEstado(Integer numero, String nuevoEstado) {
        log.info("[HabitacionService] Cambiando estado de habitacion {} a {}", numero, nuevoEstado);
        validarEstado(nuevoEstado);
        Habitacion habitacion = habitacionRepository.findByNumero(numero)
        .orElseThrow(() -> {
            log.warn("[HabitacionService] Habitacion {} no encontrada", numero);
            return new RecursoNoEncontradoException("la habitacion " + numero + " no existe");
        });
        habitacion.setEstado(nuevoEstado);
        log.info("[HabitacionService] Estado de habitacion {} actualizado a {}", numero, nuevoEstado);
        return habitacionRepository.save(habitacion);
    }

    public String eliminar(Integer numero) {
        log.info("[HabitacionService] Eliminando habitacion numero: {}", numero);
            Habitacion habitacion = habitacionRepository.findByNumero(numero)
            .orElseThrow(() -> {
                log.warn("[HabitacionService] Habitacion {} no encontrada para eliminar", numero);
                return new RecursoNoEncontradoException("la habitacion " + numero + " no existe");
            });
            habitacionRepository.delete(habitacion);
            log.info("[HabitacionService] Habitacion {} eliminada", numero);
            return "habitacion " + numero + " eliminada";
    }

    private void validarEstado(String estado) {
        if (estado == null) return;
        String e = estado.toLowerCase();
        if (!e.equals("disponible") && !e.equals("ocupado")
                && !e.equals("mantenimiento")) {
            log.warn("[HabitacionService] Estado invalido recibido: {}", estado);
            throw new ValidacionException(
                "incorrecto, use disponible, ocupado o mantenimiento");
        }
    }

    private HabitacionDTO convertirADTO(Habitacion habitacion) {
        HabitacionDTO dto = new HabitacionDTO();
        dto.setNumero(habitacion.getNumero());
        dto.setCategoria(habitacion.getCategoria());
        dto.setCapacidad(habitacion.getCapacidad());
        dto.setEstado(habitacion.getEstado());
        return dto;
    }

    
}
