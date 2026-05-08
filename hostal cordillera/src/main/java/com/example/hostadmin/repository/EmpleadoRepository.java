package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.Empleado;
// id es rut, por eso es String
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

}
