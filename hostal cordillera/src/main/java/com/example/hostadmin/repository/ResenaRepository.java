package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByHuespedRun(String run);
}
