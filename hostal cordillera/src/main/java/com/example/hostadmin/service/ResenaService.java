package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.ResenniaDTO;
import com.example.hostadmin.model.Huesped;
import com.example.hostadmin.model.Resena;
import com.example.hostadmin.repository.HuespedRepository;
import com.example.hostadmin.repository.ResenaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private HuespedRepository huespedRepository;

    
    public List<ResenniaDTO> obtenerTodas() {
        return resenaRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public ResenniaDTO buscarPorId(Long id) {
        Resena resena = resenaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("resena " + id + " no encontrada"));
        return convertirADTO(resena);
    }

    public List<ResenniaDTO> buscarPorHuesped(String run) {
        return resenaRepository.findAll().stream()
        .filter(r -> r.getHuesped() != null && r.getHuesped().getRun().equals(run))
        .map(this::convertirADTO)
        .toList();
    }

    public Resena guardar(String huespedRun, Resena resena) {
        Huesped huesped = huespedRepository.findById(huespedRun)
        .orElseThrow(() -> new RuntimeException("el huesped " + huespedRun + " no existe"));
        if (resena.getComentario() == null || resena.getComentario().isBlank()) {
            throw new RuntimeException("el comentario no puede estar vacio");
        }
        resena.setHuesped(huesped);
        return resenaRepository.save(resena);
    }

    public String eliminar(Long id) {
        try {
            Resena resena = resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("resena " + id + " no encontrada"));
            resenaRepository.delete(resena);
            return "resena: " + id + " eliminada";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private ResenniaDTO convertirADTO(Resena resena) {
        ResenniaDTO dto = new ResenniaDTO();
        dto.setId(resena.getId());
        dto.setComentario(resena.getComentario());
        dto.setCalificacion(resena.getCalificacion());
        dto.setFecha(resena.getFecha());
        if (resena.getHuesped() != null) {
            dto.setHuesped(resena.getHuesped().getNombre());
        }
        if (resena.getHostal() != null) {
            dto.setHostal(resena.getHostal().getNombre());
        }
        return dto;
    }

}
