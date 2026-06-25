package com.example.services;

import java.util.List;

import com.example.entities.Correo;
import com.example.entities.Empleado;



public interface CorreoService {
	
	// traer o implentar los metodos de Correo
	
	// Método que persiste los correos
	Correo saveCorreo(Correo correo);
	
	// Método para recuperar todos los telefonos
	List<Correo> getAllCorreos();
	
	// Método para primero preguntar y comprobar si para este empleado hay correos
	boolean existsByEmpleado(Empleado empleado);
	
	// Método para eliminar todos los correos de un empleado
	void deleteByEmpleado(Empleado empleado);
	
	// Método para encontrar todos los correos de un empleado
	List<Correo> findByEmpleado(Empleado empleado);

}
