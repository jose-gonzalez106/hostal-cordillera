package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.TipoEmpleadoDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.TipoEmpleado;
import com.example.hostadmin.repository.TipoEmpleadoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TipoEmpleadoService {

    @Autowired
    private TipoEmpleadoRepository tipoEmpleadoRepository;

    public List<TipoEmpleadoDTO> obtenerTodos() {
        log.info("[TipoEmpleadoService] Obteniendo todos los tipos de empleado");
        return tipoEmpleadoRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
    }

    public TipoEmpleadoDTO buscarPorId(Long id) {
        log.info("[TipoEmpleadoService] Buscando tipo empleado con id: {}", id);
        TipoEmpleado tipo = tipoEmpleadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[TipoEmpleadoService] Tipo empleado {} no encontrado", id);
                    return new RecursoNoEncontradoException("no se encontro el tipo: " + id);
                });
        return convertirADTO(tipo);
    }

    public TipoEmpleado guardar(TipoEmpleado tipo) {
        log.info("[TipoEmpleadoService] Guardando tipo empleado: {}", tipo.getCategoria());
        if (tipoEmpleadoRepository.existsByCategoria(tipo.getCategoria())) {
            log.warn("[TipoEmpleadoService] Ya existe tipo empleado: {}", tipo.getCategoria());
            throw new ValidacionException("ya existe el tipo: " + tipo.getCategoria());
        }
        TipoEmpleado guardado = tipoEmpleadoRepository.save(tipo);
        log.info("[TipoEmpleadoService] Tipo empleado guardado con id: {}", guardado.getId());
        return guardado;
    }

    public String eliminar(Long id) {
        log.info("[TipoEmpleadoService] Eliminando tipo empleado id: {}", id);
        TipoEmpleado tipo = tipoEmpleadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("[TipoEmpleadoService] Tipo empleado {} no encontrado para eliminar", id);
                    return new RecursoNoEncontradoException("id " + id + " no encontrado");
                });
        tipoEmpleadoRepository.delete(tipo);
        log.info("[TipoEmpleadoService] Tipo empleado {} eliminado", id);
        return "tipo: " + tipo.getCategoria() + " eliminado";
    }

    private TipoEmpleadoDTO convertirADTO(TipoEmpleado tipo) {
        TipoEmpleadoDTO dto = new TipoEmpleadoDTO();
        dto.setId(tipo.getId());
        dto.setCategoria(tipo.getCategoria());
        return dto;
    }
}
