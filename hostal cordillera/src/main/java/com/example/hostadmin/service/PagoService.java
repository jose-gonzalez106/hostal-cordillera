package com.example.hostadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.PagoDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Pago;
import com.example.hostadmin.model.Reserva;
import com.example.hostadmin.repository.PagoRepository;
import com.example.hostadmin.repository.ReservaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ReservaRepository reservaRepository;


    public List<PagoDTO> obtenerTodos() {
        log.info("[PagoService] Obteniendo todos los pagos");
        return pagoRepository.findAll().stream()
        .map(this::convertirADTO)
        .toList();
    }

    public PagoDTO buscarPorId(Long id) {
        log.info("[PagoService] Buscando pago con id: {}", id);
        Pago pago = pagoRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[PagoService] Pago {} no encontrado", id);
            return new RecursoNoEncontradoException("pago " + id + " no encontrado");
        });
        return convertirADTO(pago);
    }

    public Pago registrar(Long reservaId, Pago pago) {
        log.info("[PagoService] Registrando pago para reserva id: {}", reservaId);
        Reserva reserva = reservaRepository.findById(reservaId)
        .orElseThrow(() -> {
            log.warn("[PagoService] Reserva {} no existe", reservaId);
            return new RecursoNoEncontradoException("la reserva " + reservaId + " no existe");
        });
        if (pagoRepository.existsByReservaId(reservaId)) {
            log.warn("[PagoService] La reserva {} ya tiene un pago registrado", reservaId);
            throw new ValidacionException("la reserva " + reservaId + " ya tiene un pago");
        }
        pago.setMontoTotal(reserva.getPrecio());
        pago.setReserva(reserva);
        Pago guardado = pagoRepository.save(pago);
        log.info("[PagoService] Pago registrado con id: {}", guardado.getId());
        return guardado;
    }

    public String eliminar(Long id) {
        log.info("[PagoService] Eliminando pago id: {}", id);
        Pago pago = pagoRepository.findById(id)
        .orElseThrow(() -> {
            log.warn("[PagoService] Pago {} no encontrado para eliminar", id);
            return new RecursoNoEncontradoException("pago " + id + " no encontrado");
        });
        pagoRepository.delete(pago);
        log.info("[PagoService] Pago {} eliminado correctamente", id);
        return "pago " + id + " eliminado";
    }

    private PagoDTO convertirADTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setId(pago.getId());
        dto.setMontoTotal(pago.getMontoTotal());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setEstadoPago(pago.getEstadoPago());
        if (pago.getReserva() != null) {
            dto.setReservaId(pago.getReserva().getId());
        }
        return dto;
    }

}
