package com.example.hostadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hostadmin.model.Habitacion;
//usan número de habitación como id, por eso es Integer
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {

}
