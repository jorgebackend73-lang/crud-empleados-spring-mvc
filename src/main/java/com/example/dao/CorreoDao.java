package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Correo;
import com.example.entities.Empleado;
import java.util.List;



public interface CorreoDao extends JpaRepository<Correo, Integer> {
	
	// Método para construir correo
	
	// Método para primero preguntar y comprobar si para este empleado hay correos
	boolean existsByEmpleado(Empleado empleado);
	
	// Método para eliminar todos los correos de un empleado
	void deleteByEmpleado(Empleado empleado);
	
	// Método para encontrar todos los correos de un empleado
	List<Correo> findByEmpleado(Empleado empleado);

}
