package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hostadmin.DTO.HostalDTO;
import com.example.hostadmin.model.Comuna;
import com.example.hostadmin.model.Hostal;
import com.example.hostadmin.repository.ComunaRepository;
import com.example.hostadmin.repository.HostalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HostalService {
    @Autowired
    private HostalRepository hostalRepository;

    @Autowired
    private ComunaRepository comunaRepository;
    
    
    public List<HostalDTO> obtenerTodos() {
        return hostalRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public HostalDTO buscarPorId(Long id) {
        Hostal hostal = hostalRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("hostal" + id + "no encontrado"));
        return convertirADTO(hostal);
    }

    public Hostal guardar(Long comunaId, Hostal hostal) {
        boolean rutDuplicado = hostalRepository.findAll().stream()
        .anyMatch(h -> h.getRutEmpresa()
        .equals(hostal.getRutEmpresa()));
        if (rutDuplicado) {
            throw new RuntimeException("ya existe un hostal con el rut: " + hostal.getRutEmpresa());
        }
        Comuna comuna = comunaRepository.findById(comunaId)
        .orElseThrow(() -> new RuntimeException("la comuna" + comunaId + "no existe"));
        hostal.setComuna(comuna);
        return hostalRepository.save(hostal);
    }

    public Hostal actualizar(Long id, Hostal hostal) {
        Hostal existente = hostalRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("hostal" + id + "no encontrado"));
        if (hostal.getNombre() != null) {
            existente.setNombre(hostal.getNombre());
        }
        if (hostal.getDireccion() != null) {
            existente.setDireccion(hostal.getDireccion());
        }
        if (hostal.getCiudad() != null) {
            existente.setCiudad(hostal.getCiudad());
        }
        return hostalRepository.save(existente);
    }

    public String eliminar(Long id) {
        try {
            Hostal hostal = hostalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("hostal" + id + "no encontrado"));
            hostalRepository.delete(hostal);
            return "el hostal" + hostal.getNombre() +"fue eliminado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
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
