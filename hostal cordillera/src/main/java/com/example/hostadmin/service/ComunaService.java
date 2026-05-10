package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.ComunaDTO;
import com.example.hostadmin.model.Comuna;
import com.example.hostadmin.model.Region;
import com.example.hostadmin.repository.ComunaRepository;
import com.example.hostadmin.repository.RegionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComunaService {
    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private RegionRepository regionRepository;


    public List<ComunaDTO> obtenerTodas() {
        return comunaRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public ComunaDTO buscarPorId(Long id) {
        Comuna comuna = comunaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("comuna " + id + " no encontrada"));
        return convertirADTO(comuna);
    }

    public Comuna guardar(Long regionId, Comuna comuna) {
        Region region = regionRepository.findById(regionId)
        .orElseThrow(() -> new RuntimeException("la region " + regionId + " no existe"));
        comuna.setRegion(region);
        return comunaRepository.save(comuna);
    }

    public Comuna actualizar(Long id, Comuna comuna) {
        Comuna existente = comunaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("comuna " + id + " no encontrada"));
        if (comuna.getNombre() != null) {
            existente.setNombre(comuna.getNombre());
        }
        return comunaRepository.save(existente);
    }

    public String eliminar(Long id) {
        try {
            Comuna comuna = comunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("comuna " + id + " no encontrada"));
            comunaRepository.delete(comuna);
            return "comuna" + comuna.getNombre() +" eliminada";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
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

