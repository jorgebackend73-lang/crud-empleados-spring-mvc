package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Telefono;
import com.example.entities.Empleado;
import java.util.List;



public interface TelefonoDao extends JpaRepository<Telefono, Integer> {
	
	// Método para ver si el empleado tiene telefonos
	boolean existsByEmpleado(Empleado empleado);
	
	// Método para eliminar telefonos
	void deleteByEmpleado(Empleado empleado);
	
	// Metodo para encontrar todos los telefonos del empleado
	List<Telefono> findByEmpleado(Empleado empleado);
	// casi lo genera solo eclipse solo recordar exists, delete, find By empleado

}
