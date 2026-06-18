package com.example.controllers;

import com.example.services.EmpleadoServiceImpl;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.Empleado;
import com.example.services.DepartamentoService;
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
	
	private static final Logger LOG = Logger.getLogger("EmpleadoController");
	
	// Esto es el Handle Request en el patron MVC.
	// Método responsable de hablar con la capa de servicios.
	// Tenemos que hacer que se comunique con la capa de servicios.
	
	// private final EmpleadoServiceImpl empleadoService;
	private final EmpleadoService empleadoService;
	private final DepartamentoService departamentoService;


//	 EmpleadoController(EmpleadoServiceImpl empleadoServiceImpl) {
//		 this.empleadoServiceImpl = empleadoServiceImpl;
//	}
	
	
	// y traer el listado de empleados y su vista.
	// Las vistas estan en src/main/templates.
	@GetMapping("/listar")
	public String listarEmpleados(Model model) {
		
		// Usamos model y le agregamos como atributo todos los empleados:
		model.addAttribute("empleados", empleadoService.getAllEmpleados());
		
		// Retorna la vista para listar empleados.
		// Endpoint lugar donde se muestra alguna capacidad de nuestro código.
		// Devolvemos listadoEmpleados el nombre de nuestro template.
				
		return "listadoEmpleados"; 
		
		
	}
	
	// Método que muestra el formulario de creación de empleado
	@GetMapping("/alta")
	public String mostrarFormulariAlta(Model model) {

		
		// Se necesitan los departamentos desde la capa de servicios
		model.addAttribute("departamentos", 
				departamentoService.getAllDepartamentos());
		
		// Es necesario enviar un objeto empleado vacio para que se vinculen sus propiedades
		// con cada control (elemento input, select, etc.) del formulario:
		model.addAttribute("empleado", new Empleado ());
		
		return "formularioAltaModificacion"; // Creamos vista en template
	}
	
	// Método para recibir los datos del formulario de creación de empleado
	// Alta/Modificación Empleado
	// Model Atribute coge al empleado
	@PostMapping("/persistir")
	public String procesarFormularioAltaModificacion (@ModelAttribute Empleado empleado,
			@RequestParam String numerosTelefono,
			@RequestParam String direccionesCorreo) {
		// queremos ademas del empleado otro parametro bajo name para numerosTelefono
		// y convertirlo en string. Al convertir a una variable con el mismo nombre que el
		// parametro no hace falta volver a escribirlo.
		
		LOG.info("Objeto empleado recibido");
		LOG.info(empleado.toString());
		LOG.info("Números de teléfono recibidos: " + numerosTelefono);
		LOG.info("Direcciones de correo recibidas: " + direccionesCorreo);
	
		// Se recibe un objeto Empleado con los datos del fromulario
		// Se envía a la capa de servicios para que sea persistido
		// empleadoService.saveEmpleado(empleado);
		
		return "redirect:/empleados/listar"; // Redirige a la lista de empleados 
		
	}
}
