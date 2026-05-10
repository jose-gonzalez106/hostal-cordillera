package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.TipoEmpleado;

public interface TipoEmpleadoRepository extends JpaRepository<TipoEmpleado, Long> {
    boolean existsByCargo(String cargo);
}
