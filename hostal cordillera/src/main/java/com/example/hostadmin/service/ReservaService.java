package com.example.hostadmin.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.ReservaDTO;
import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Habitacion;
import com.example.hostadmin.model.Huesped;
import com.example.hostadmin.model.Reserva;
import com.example.hostadmin.repository.HabitacionRepository;
import com.example.hostadmin.repository.HuespedRepository;
import com.example.hostadmin.repository.ReservaRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class ReservaService {

        @Autowired
        private ReservaRepository reservaRepository;

        @Autowired
        private HabitacionRepository habitacionRepository;

        @Autowired
        private HuespedRepository huespedRepository;

        public List<ReservaDTO> obtenerTodas() {
        log.info("[ReservaService] Obteniendo todas las reservas");
        return reservaRepository.findAll().stream()
                .map(this::convertirADTO)
                .toList();
        }

        public ReservaDTO buscarPorId(Long id) {
        log.info("[ReservaService] Buscando reserva con id: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {log.warn("[ReservaService] Reserva con id {} no encontrada", id);
                return new RecursoNoEncontradoException("reserva " + id + " no encontrada");
                });
        return convertirADTO(reserva);
        }

        public List<ReservaDTO> buscarPorHuesped(String run) {
        log.info("[ReservaService] Buscando reservas del huesped: {}", run);
        return reservaRepository.findByHuespedRun(run).stream()
                .map(this::convertirADTO)
                .toList();
        }

        public Reserva crear(String huespedRun, Integer habitacionNumero, Reserva reserva) {
        log.info("[ReservaService] Creando reserva para huesped {} en habitacion {}", huespedRun, habitacionNumero);
        Huesped huesped = huespedRepository.findById(huespedRun)
                .orElseThrow(() -> {
                        log.warn("[ReservaService] Huesped {} no existe", huespedRun);
                        return new RecursoNoEncontradoException("huesped " + huespedRun + " no existe");
                });
        Habitacion habitacion = habitacionRepository.findByNumero(habitacionNumero)
                .orElseThrow(() -> {
                        log.warn("[ReservaService] Habitacion {} no existe", habitacionNumero);
                        return new RecursoNoEncontradoException("la habitacion " + habitacionNumero + " no existe");
                });
        if (!habitacion.getEstado().equalsIgnoreCase("disponible")) {
                log.warn("[ReservaService] Habitacion {} no disponible, estado: {}", habitacionNumero, habitacion.getEstado());
                throw new ValidacionException("la habitacion " + habitacionNumero +
                        " no esta disponible, el estado actual es: " + habitacion.getEstado());
        }
        if (!reserva.getFechaTermino().isAfter(reserva.getFechaInicio())) {
                log.warn("[ReservaService] Fechas invalidas: inicio={} termino={}", reserva.getFechaInicio(), reserva.getFechaTermino());
                throw new ValidacionException("la fecha de termino debe ser posterior a la de inicio");
        }
        if (reserva.getFechaInicio().isBefore(LocalDate.now())) {
                log.warn("[ReservaService] Fecha de inicio en el pasado: {}", reserva.getFechaInicio());
                throw new ValidacionException("la fecha de inicio no puede ser en el pasado");
        }

        long noches = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaTermino());
        reserva.setPrecio(BigDecimal.valueOf(noches * 30000));
        reserva.setEstado("activa");
        reserva.setHuesped(huesped);
        reserva.setHabitacion(habitacion);

        habitacion.setEstado("ocupado");
        habitacionRepository.save(habitacion);

        Reserva guardada = reservaRepository.save(reserva);
        log.info("[ReservaService] Reserva creada con id: {}, precio: {}", guardada.getId(), guardada.getPrecio());
        return guardada;
        }

        public String cancelar(Long id) {
        log.info("[ReservaService] Cancelando reserva id: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {log.warn("[ReservaService] Reserva {} no encontrada para cancelar", id);
                return new RecursoNoEncontradoException("reserva " + id + " no encontrada");
                });
                Habitacion habitacion = reserva.getHabitacion();
                habitacion.setEstado("disponible");
                habitacionRepository.save(habitacion);
                reservaRepository.delete(reserva);
                log.info("[ReservaService] Reserva {} cancelada, habitacion {} disponible nuevamente", id, habitacion.getNumero());
                return "reserva " + id + " cancelada, la habitacion ahora esta disponible";
                }

        public String eliminar(Long id) {
        log.info("[ReservaService] Eliminando reserva id: {}", id);
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> {log.warn("[ReservaService] Reserva {} no encontrada para eliminar", id);
                return new RecursoNoEncontradoException("reserva " + id + " no encontrada");
                });
        reservaRepository.delete(reserva);
        log.info("[ReservaService] Reserva {} eliminada", id);
        return "reserva " + id + " eliminada";
        }

        private ReservaDTO convertirADTO(Reserva reserva) {
                ReservaDTO dto = new ReservaDTO();
                dto.setId(reserva.getId());
                dto.setFechaIngreso(reserva.getFechaInicio());
                dto.setFechaSalida(reserva.getFechaTermino());
                dto.setPrecio(reserva.getPrecio() != null ? reserva.getPrecio() : BigDecimal.ZERO);
                dto.setEstado(reserva.getEstado());
                if (reserva.getHuesped() != null) {
                        dto.setHuesped(reserva.getHuesped().getNombre() + " " + reserva.getHuesped().getApellido());
                }
                if (reserva.getHabitacion() != null) {
                        dto.setHabitacion("Habitacion " + reserva.getHabitacion().getNumero());
                }
                return dto;
        }
}
