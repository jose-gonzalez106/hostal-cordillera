package com.example.hostadmin.service;

import com.example.hostadmin.exceptions.RecursoNoEncontradoException;
import com.example.hostadmin.exceptions.ValidacionException;
import com.example.hostadmin.model.Habitacion;
import com.example.hostadmin.repository.HabitacionRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HabitacionService {
@Autowired
private HabitacionRepository habitacionRepository;

//obtener todas las habitaciones
public List<Habitacion> listarTodo(){
    return habitacionRepository.findAll();
}

//buscar por número
public Habitacion buscarPorNumero(Integer numero) {
    return habitacionRepository.findById(numero)
    .orElseThrow(() -> new RecursoNoEncontradoException("Habitacion con numero " + "no existe"));

}

//crear nueva habitación + validar datos correctos
@Transactional
public Habitacion crear(Habitacion habitacion){
    validarHabitacion(habitacion);

//validar que no haya una existente con el número
if (habitacionRepository.existsById(habitacion.getNumero())) {
    throw new ValidacionException("Ya existe una habitación con el numero " + habitacion.getNumero());
}

    return habitacionRepository.save(habitacion);
}

//actualizar habitación + validar datos correctos (no se cambia número)
@Transactional
public Habitacion actualizar(Integer numero, Habitacion habitacionActualizada) {
    Habitacion existente = buscarPorNumero(numero);
    validarHabitacion(habitacionActualizada);

    existente.setCategoria(habitacionActualizada.getCategoria());
    existente.setCapacidad(habitacionActualizada.getCapacidad());
    existente.setEstado(habitacionActualizada.getEstado());
    return habitacionRepository.save(existente);
}

//eliminar habitación por número
@Transactional
public void eliminar(Integer numero) {
    if (!habitacionRepository.existsById(numero)){
        throw new RecursoNoEncontradoException("Habitacion con numero " + numero + " no existe");
    }
    habitacionRepository.deleteById(numero);
}




//validaciones para crear o actualizar habitación
private void validarHabitacion(Habitacion habitacion) {
    if (habitacion.getNumero() == null || habitacion.getNumero() <= 0) {
        throw new ValidacionException("El número de habitación debe ser un entero positivo");
}
if (habitacion.getCapacidad() == null || habitacion.getCapacidad() <= 0) {
    throw new ValidacionException("La capacidad debe ser de al menos 1 persona... PERSONA");
}
if (habitacion.getCategoria() == null || habitacion.getCategoria().isEmpty()) {
    throw new ValidacionException("La categoría no puede estar vacía");
}
if (habitacion.getEstado() == null || habitacion.getEstado().isEmpty()) {
    throw new ValidacionException("El estado no puede estar vacío");
}
//validar que estado esté en disponible, ocupado o mantenimiento
String estado = habitacion.getEstado().toLowerCase();
if (!estado.equals("disponible") && !estado.equals("ocupado") && !estado.equals("mantenimiento")) {
    throw new ValidacionException("El estado debe ser 'disponible', 'ocupado' o 'mantenimiento'");
}
}









}