package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.HuespedDTO;
import com.example.hostadmin.model.Comuna;
import com.example.hostadmin.model.Huesped;
import com.example.hostadmin.repository.ComunaRepository;
import com.example.hostadmin.repository.HuespedRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HuespedService {
    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private ComunaRepository comunaRepository;


    public List<HuespedDTO> obtenerTodos() {
        return huespedRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public HuespedDTO buscarPorRun(String run) {
        Huesped huesped = huespedRepository.findById(run)
        .orElseThrow(() -> new RuntimeException("huesped" + run + "no encontrado"));
        return convertirADTO(huesped);
    }

    public Huesped guardar(Long comunaId, Huesped huesped) {
        if (huespedRepository.existsById(huesped.getRun())) {
            throw new RuntimeException("ya existe un huesped con el run:" + huesped.getRun());
        }
        boolean correoDuplicado = huespedRepository.findAll().stream()
        .anyMatch(h -> h.getCorreo().equals(huesped.getCorreo()));
        if (correoDuplicado) {
            throw new RuntimeException("ya esta registrado el correo:" + huesped.getCorreo());
        }
        Comuna comuna = comunaRepository.findById(comunaId)
        .orElseThrow(() -> new RuntimeException("la comuna " + comunaId + "no existe"));
        huesped.setComuna(comuna);
        return huespedRepository.save(huesped);
    }

    public Huesped actualizar(String run, Huesped huesped) {
        Huesped existente = huespedRepository.findById(run)
        .orElseThrow(() -> new RuntimeException("el run: " + run + "no se encontro"));
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
        return huespedRepository.save(existente);
    }

    public String eliminar(String run) {
        try {
            Huesped huesped = huespedRepository.findById(run)
            .orElseThrow(() -> new RuntimeException("run:" + run + "no encontrado"));
            huespedRepository.delete(huesped);
            return "huesped:" + huesped.getNombre() +"eliminado del registro";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
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
