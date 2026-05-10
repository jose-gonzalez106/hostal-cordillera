package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
    boolean existsByNombre(String nombre);
}
