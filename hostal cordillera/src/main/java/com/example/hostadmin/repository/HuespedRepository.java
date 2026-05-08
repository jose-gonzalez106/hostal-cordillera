package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.Huesped;
//mismo caso de empleado, el id es rut, por eso es String
public interface HuespedRepository extends JpaRepository<Huesped, String> {

}
