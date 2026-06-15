package com.example.services;

import java.util.List;


import com.example.entities.Empleado;
import com.example.entities.Telefono;

public interface TelefonoService {
	
	// Método para recuperar todos los telefonos
	List<Telefono> getAllTelefonos();
	
	// Método para persistir un telefono
	Telefono saveTelefono(Telefono telefono);
	
	// Método para ver si el empleado tiene telefonos
	boolean existsByEmpleado(Empleado empleado);
	
	// Método para eliminar todos los telefonos de un empleado
	void deleteByEmpleado(Empleado empleado);
	
	// Método para encontrar todos los telefonos de un empleado
	List<Telefono> findByEmpleado(Empleado empleado);

}
