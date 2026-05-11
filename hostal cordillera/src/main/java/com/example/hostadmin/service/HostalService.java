package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.HostalDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Comuna;
import com.example.hostadmin.model.Hostal;
import com.example.hostadmin.repository.ComunaRepository;
import com.example.hostadmin.repository.HostalRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class HostalService {
    @Autowired
    private HostalRepository hostalRepository;

    @Autowired
    private ComunaRepository comunaRepository;
    
    
    public List<HostalDTO> obtenerTodos() {
        log.info("[HostalService] Obteniendo todos los hostales");
        return hostalRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public HostalDTO buscarPorId(Long id) {
        log.info("[HostalService] Buscando hostal con id: {}", id);
        Hostal hostal = hostalRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[HostalService] Hostal {} no encontrado", id);
            return new RecursoNoEncontradoException("hostal" + id + "no encontrado");
        });
        return convertirADTO(hostal);
    }

    public Hostal guardar(Long comunaId, Hostal hostal) {
        log.info("[HostalService] Registrando hostal con rut: {}", hostal.getRutEmpresa());
        if (hostalRepository.existsByRutEmpresa(hostal.getRutEmpresa())) {
            log.warn("[HostalService] Ya existe hostal con rut: {}", hostal.getRutEmpresa());
            throw new ValidacionException("ya existe un hostal con el rut: " + hostal.getRutEmpresa());
        }
        Comuna comuna = comunaRepository.findById(comunaId)
        .orElseThrow(() -> {
            log.warn("[HostalService] Comuna {} no existe", comunaId);
            return new RecursoNoEncontradoException("la comuna " + comunaId + " no existe");
        });
        hostal.setComuna(comuna);
        Hostal guardado = hostalRepository.save(hostal);
        log.info("[HostalService] Hostal {} guardado exitosamente", guardado.getId());
        return guardado;
    }

    public Hostal actualizar(Long id, Hostal hostal) {
        log.info("[HostalService] Actualizando hostal con id: {}", id);
        Hostal existente = hostalRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[HostalService] Hostal {} no encontrado para actualizar", id);
            return new RecursoNoEncontradoException("hostal" + id + "no encontrado");
        });
        if (hostal.getNombre() != null) {
            existente.setNombre(hostal.getNombre());
        }
        if (hostal.getDireccion() != null) {
            existente.setDireccion(hostal.getDireccion());
        }
        if (hostal.getCiudad() != null) {
            existente.setCiudad(hostal.getCiudad());
        }
        if (hostal.getRutEmpresa() != null) {
        existente.setRutEmpresa(hostal.getRutEmpresa());
        }
        if (hostal.getComuna() != null) {
            existente.setComuna(hostal.getComuna());
        }
        log.info("[HostalService] Hostal {} actualizado", id);
        return hostalRepository.save(existente);
    }

    public String eliminar(Long id) {
        log.info("[HostalService] Eliminando hostal con id: {}", id);
        Hostal hostal = hostalRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[HostalService] Hostal {} no encontrado para eliminar", id);
            return new RecursoNoEncontradoException("hostal" + id + "no encontrado");
        });
        hostalRepository.delete(hostal);
        log.info("[HostalService] Hostal {} eliminado", id);
        return "el hostal" + hostal.getNombre() +"fue eliminado";
    }

    private HostalDTO convertirADTO(Hostal hostal) {
        HostalDTO dto = new HostalDTO();
        dto.setId(hostal.getId());
        dto.setNombre(hostal.getNombre());
        dto.setDireccion(hostal.getDireccion());
        dto.setRutEmpresa(hostal.getRutEmpresa());
        dto.setCiudad(hostal.getCiudad());
        return dto;
    }

}
