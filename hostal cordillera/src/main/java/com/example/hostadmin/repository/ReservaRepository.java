package com.example.hostadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByHuespedRun(String run);
}
