package com.example.hostadmin.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hostadmin.DTO.ReservaDTO;
import com.example.hostadmin.model.Habitacion;
import com.example.hostadmin.model.Huesped;
import com.example.hostadmin.model.Reserva;
import com.example.hostadmin.repository.HabitacionRepository;
import com.example.hostadmin.repository.HuespedRepository;
import com.example.hostadmin.repository.ReservaRepository;

import jakarta.transaction.Transactional;

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
                return reservaRepository.findAll().stream()
                                .map(this::convertirADTO)
                                .toList();
        }

        public ReservaDTO buscarPorId(Long id) {
                Reserva reserva = reservaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("reserva " + id + " no encontrada"));
                return convertirADTO(reserva);
        }

        public List<ReservaDTO> buscarPorHuesped(String run) {
                return reservaRepository.findAll().stream()
                                .filter(r -> r.getHuesped() != null
                                                && r.getHuesped().getRun().equals(run))
                                .map(this::convertirADTO)
                                .toList();
        }

        public Reserva crear(String huespedRun,
                        Integer habitacionNumero,
                        Reserva reserva) {
                Huesped huesped = huespedRepository.findById(huespedRun)
                                .orElseThrow(() -> new RuntimeException("huesped "
                                                + huespedRun
                                                + " no existe"));
                Habitacion habitacion = habitacionRepository.findById(habitacionNumero)
                                .orElseThrow(() -> new RuntimeException("la habitacion "
                                                + habitacionNumero
                                                + " no existe"));
                if (!habitacion.getEstado().equalsIgnoreCase("disponible")) {
                        throw new RuntimeException(
                                        "la habitacion "
                                                        + habitacionNumero
                                                        + " no esta disponible, el estado actual es: "
                                                        + habitacion.getEstado());
                }
                if (!reserva.getFechaTermino()
                                .isAfter(reserva.getFechaInicio())) {
                        throw new RuntimeException(
                                        "la fecha de termino debe ser posterior a la de inicio");
                }
                if (reserva.getFechaInicio().isBefore(LocalDate.now())) {
                        throw new RuntimeException(
                                        "la fecha de inicio no puede ser en el pasado");
                }
                long noches = ChronoUnit.DAYS.between(
                                reserva.getFechaInicio(),
                                reserva.getFechaTermino());
                reserva.setPrecio(
                                BigDecimal.valueOf(noches * 30000));
                reserva.setEstado("activa");
                reserva.setHuesped(huesped);
                reserva.setHabitacion(habitacion);
                habitacion.setEstado("ocupado");
                habitacionRepository.save(habitacion);
                return reservaRepository.save(reserva);
        }

        public String cancelar(Long id) {
                try {
                        Reserva reserva = reservaRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "reserva " + id + " no encontrada"));
                        Habitacion habitacion = reserva.getHabitacion();
                        habitacion.setEstado("disponible");
                        habitacionRepository.save(habitacion);
                        reservaRepository.delete(reserva);
                        return "reserva "
                                        + id
                                        + " cancelada, la habitacion ahora esta disponible";
                } catch (RuntimeException e) {
                        return e.getMessage();
                }
        }

        public String eliminar(Long id) {
                try {
                        Reserva reserva = reservaRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException(
                                                        "reserva " + id + " no encontrada"));
                        reservaRepository.delete(reserva);
                        return "reserva " + id + " eliminada";
                } catch (RuntimeException e) {
                        return e.getMessage();
                }
        }

        private ReservaDTO convertirADTO(Reserva reserva) {
                ReservaDTO dto = new ReservaDTO();
                dto.setId(reserva.getId());
                dto.setFechaIngreso(reserva.getFechaInicio());
                dto.setFechaSalida(reserva.getFechaTermino());
                dto.setPrecio(
                                reserva.getPrecio() != null
                                                ? reserva.getPrecio()
                                                : BigDecimal.ZERO);
                return dto;
        }
}