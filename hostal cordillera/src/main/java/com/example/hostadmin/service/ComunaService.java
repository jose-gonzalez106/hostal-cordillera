package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.ComunaDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.model.Comuna;
import com.example.hostadmin.model.Region;
import com.example.hostadmin.repository.ComunaRepository;
import com.example.hostadmin.repository.RegionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private RegionRepository regionRepository;


    public List<ComunaDTO> obtenerTodas() {
        log.info("[ComunaService] Obteniendo todas las comunas");
        return comunaRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public ComunaDTO buscarPorId(Long id) {
        log.info("[ComunaService] Buscando comuna con id: {}", id);
        Comuna comuna = comunaRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[ComunaService] Comuna {} no encontrada", id);
            return new RecursoNoEncontradoException("comuna " + id + " no encontrada");
        });
        return convertirADTO(comuna);
    }

    public Comuna guardar(Long regionId, Comuna comuna) {
        log.info("[ComunaService] Registrando comuna en region id: {}", regionId);
        Region region = regionRepository.findById(regionId)
        .orElseThrow(() -> {
            log.warn("[ComunaService] Region {} no existe", regionId);
            return new RecursoNoEncontradoException("la region " + regionId + " no existe");
        });
        comuna.setRegion(region);
        Comuna guardada = comunaRepository.save(comuna);
        log.info("[ComunaService] Comuna {} guardada exitosamente", guardada.getId());
        return guardada;
    }

    public Comuna actualizar(Long id, Comuna comuna) {
        log.info("[ComunaService] Actualizando comuna con id: {}", id);
        Comuna existente = comunaRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[ComunaService] Comuna {} no encontrada para actualizar", id);
            return new RecursoNoEncontradoException("comuna " + id + " no encontrada");
        });
        if (comuna.getNombre() != null) {
            existente.setNombre(comuna.getNombre());
        }
        log.info("[ComunaService] Comuna {} actualizada", id);
        return comunaRepository.save(existente);
    }

    public String eliminar(Long id) {
        log.info("[ComunaService] Eliminando comuna con id: {}", id);
        Comuna comuna = comunaRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[ComunaService] Comuna {} no encontrada para eliminar", id);
            return new RecursoNoEncontradoException("comuna " + id + " no encontrada");
        });
        comunaRepository.delete(comuna);
        log.info("[ComunaService] Comuna {} eliminada", id);
        return "comuna" + comuna.getNombre() +" eliminada";
    }

    private ComunaDTO convertirADTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setId(comuna.getId());
        dto.setNombre(comuna.getNombre());
        if (comuna.getRegion() != null) {
            dto.setRegion(comuna.getRegion().getNombre());
        } else {
            dto.setRegion("sin region");
        }
        return dto;
    }
}
