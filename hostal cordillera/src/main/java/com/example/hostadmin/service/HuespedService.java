package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.HuespedDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Comuna;
import com.example.hostadmin.model.Huesped;
import com.example.hostadmin.repository.ComunaRepository;
import com.example.hostadmin.repository.HuespedRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class HuespedService {
    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private ComunaRepository comunaRepository;


    public List<HuespedDTO> obtenerTodos() {
        log.info("[HuespedService] Obteniendo todos los huespedes");
        return huespedRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public HuespedDTO buscarPorRun(String run) {
        log.info("[HuespedService] Buscando huesped con run: {}", run);
        Huesped huesped = huespedRepository.findById(run)
        .orElseThrow(() -> {
            log.warn("[HuespedService] Huesped {} no encontrado", run);
            return new RecursoNoEncontradoException("huesped" + run + "no encontrado");
        });
        return convertirADTO(huesped);
    }

    public Huesped guardar(Long comunaId, Huesped huesped) {
        log.info("[HuespedService] Registrando huesped con run: {}", huesped.getRun());
        if (huespedRepository.existsById(huesped.getRun())) {
            log.warn("[HuespedService] Ya existe huesped con run: {}", huesped.getRun());
            throw new ValidacionException("ya existe un huesped con el run: " + huesped.getRun());
        }
        if (huespedRepository.existsByCorreo(huesped.getCorreo())) {
            log.warn("[HuespedService] Correo duplicado: {}", huesped.getCorreo());
            throw new ValidacionException("ya esta registrado el correo: " + huesped.getCorreo());
        }
        Comuna comuna = comunaRepository.findById(comunaId)
        .orElseThrow(() -> {
            log.warn("[HuespedService] Comuna {} no existe", comunaId);
            return new RecursoNoEncontradoException("la comuna " + comunaId + " no existe");
        });
        huesped.setComuna(comuna);
        Huesped guardado = huespedRepository.save(huesped);
        log.info("[HuespedService] Huesped {} guardado exitosamente", guardado.getRun());
        return guardado;
    }

    public Huesped actualizar(String run, Huesped huesped) {
        log.info("[HuespedService] Actualizando huesped con run: {}", run);
        Huesped existente = huespedRepository.findById(run)
        .orElseThrow(() -> {
            log.warn("[HuespedService] Huesped {} no encontrado para actualizar", run);
            return new RecursoNoEncontradoException("el run: " + run + "no se encontro");
        });
        if (huesped.getNombre() != null) {
            existente.setNombre(huesped.getNombre());
        }
        if (huesped.getApellido() != null) {
            existente.setApellido(huesped.getApellido());
        }
        if (huesped.getTelefono() != null) {
            existente.setTelefono(huesped.getTelefono());
        }
        if (huesped.getCorreo() != null) {
            existente.setCorreo(huesped.getCorreo());
        }
        log.info("[HuespedService] Huesped {} actualizado", run);
        return huespedRepository.save(existente);
    }

    public String eliminar(String run) {
        log.info("[HuespedService] Eliminando huesped con run: {}", run);
        Huesped huesped = huespedRepository.findById(run)
        .orElseThrow(() -> {
            log.warn("[HuespedService] Huesped {} no encontrado para eliminar", run);
            return new RecursoNoEncontradoException("run:" + run + "no encontrado");
        });
        huespedRepository.delete(huesped);
        log.info("[HuespedService] Huesped {} eliminado", run);
        return "huesped:" + huesped.getNombre() +"eliminado del registro";
    }

    private HuespedDTO convertirADTO(Huesped huesped) {
        HuespedDTO dto = new HuespedDTO();
        dto.setRun(huesped.getRun());
        dto.setNombre(huesped.getNombre());
        dto.setApellido(huesped.getApellido());
        dto.setTelefono(huesped.getTelefono());
        dto.setCorreo(huesped.getCorreo());
        return dto;
    }

}
