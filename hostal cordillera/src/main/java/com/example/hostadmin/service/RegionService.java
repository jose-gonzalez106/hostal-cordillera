package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.RegionDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.model.Region;
import com.example.hostadmin.repository.RegionRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;


    public List<RegionDTO> obtenerTodas() {
        log.info("[RegionService] Obteniendo todas las regiones");
        return regionRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public RegionDTO buscarPorId(Long id) {
        log.info("[RegionService] Buscando region con id: {}", id);
        Region region = regionRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[RegionService] Region {} no encontrada", id);
            return new RecursoNoEncontradoException("region " + id + "no encontrada");
        });
        return convertirADTO(region);
    }

    public Region guardar(Region region) {
        log.info("[RegionService] Guardando region: {}", region.getNombre());
        Region guardada = regionRepository.save(region);
        log.info("[RegionService] Region guardada con id: {}", guardada.getId());
        return guardada;
    }

    public Region actualizar(Long id, Region region) {
        log.info("[RegionService] Actualizando region con id: {}", id);
        Region existente = regionRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("[RegionService] Region {} no encontrada para actualizar", id);
                return new RecursoNoEncontradoException("region" + id + "no encontrada");
            });
        if (region.getNombre() != null) {
            existente.setNombre(region.getNombre());
        }
        log.info("[RegionService] Region {} actualizada", id);
        return regionRepository.save(existente);
    }

    public String eliminar(Long id) {
        log.info("[RegionService] Eliminando region con id: {}", id);
            Region region = regionRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("[RegionService] Region {} no encontrada para eliminar", id);
                return new RecursoNoEncontradoException("region" + id + "no encontrada");
            });
            regionRepository.delete(region);
            log.info("[RegionService] Region {} eliminada", id);
            return "la region " + region.getNombre() + " eliminada";
    }

    private RegionDTO convertirADTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setNombre(region.getNombre());
        return dto;
    }

}
