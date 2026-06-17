package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.services.EmpleadoService;

import lombok.RequiredArgsConstructor;

// Anotación para convertir nuestra clase en un controller:
@Controller

// Las peticiones que en la URL tengan empleado vendran a 
// este controlador gracias a esta anotación:
@RequestMapping("/empleados")

//Para EmpleadoService. Inyeccion de dependencias por constructor.
@RequiredArgsConstructor 

public class EmpleadoController {
	
	// Esto es el Handle Request en el patron MVC.
	// Método responsable de hablar con la capa de servicios.
	// Tenemos que hace que se comunique con la capa de servicios.
	
	private final EmpleadoService empleadoservice;
	
	
	// y traer el listado de empleados y su vista.
	// Las vistas estan en src/main/templates.
	@GetMapping("/listar")
	public String listarEmpleados(Model model) {
		
		// Usamos model y le agregamos como atributo todos los empleados:
		model.addAttribute("empleados", empleadoservice.getAllEmpleados());
		
		// Retorna la vista para listar empleados.
		// Endpoint lugar donde se muestra alguna capacidad de nuestro código.
		// Devolvemos listadoEmpleados el nombre de nuestro template.
		
		
		
		return "listadoEmpleados"; 
		
		
	}

}
