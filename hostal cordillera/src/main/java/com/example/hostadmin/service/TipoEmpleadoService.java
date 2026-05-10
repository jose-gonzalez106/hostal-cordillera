package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hostadmin.DTO.TipoEmpleadoDTO;
import com.example.hostadmin.model.TipoEmpleado;
import com.example.hostadmin.repository.TipoEmpleadoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoEmpleadoService {

    @Autowired
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    public List<TipoEmpleadoDTO> obtenerTodos() {
        return tipoEmpleadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoEmpleadoDTO buscarPorId(Long id) {

        TipoEmpleado tipo = tipoEmpleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no se encontro el tipo: " + id));
        return convertirADTO(tipo);
    }

    public TipoEmpleado guardar(TipoEmpleado tipo) {

        boolean duplicado = tipoEmpleadoRepository.findAll().stream()
                .anyMatch(t -> t.getCategoria()
                        .equalsIgnoreCase(tipo.getCategoria()));

        if (duplicado) {
            throw new RuntimeException("ya existe el tipo: " + tipo.getCategoria());
        }

        return tipoEmpleadoRepository.save(tipo);
    }

    public String eliminar(Long id) {

        try {

            TipoEmpleado tipo = tipoEmpleadoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("id " + id + " no encontrado"));

            tipoEmpleadoRepository.delete(tipo);

            return "tipo: " + tipo.getCategoria() + " eliminado";

        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private TipoEmpleadoDTO convertirADTO(TipoEmpleado tipo) {
        TipoEmpleadoDTO dto = new TipoEmpleadoDTO();
        dto.setId(tipo.getId());
        dto.setCategoria(tipo.getCategoria());
        return dto;
    }
}