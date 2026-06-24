package com.example.controllers;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Correo;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import jakarta.validation.Valid;
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
	public String mostrarFormulariAlta(Model model,
			@ModelAttribute Empleado empleado) {
			// esto pasa ya el empleado vacio a ModelAtribute
		
		// Se necesitan los departamentos desde la capa de servicios
		model.addAttribute("departamentos", 
				departamentoService.getAllDepartamentos());
		
		// Es necesario enviar un objeto empleado vacio para que se vinculen sus propiedades
		// con cada control (elemento input, select, etc.) del formulario:
		// Al final lo comentamos pues con la anotación ModelAtribute ya se recibe
		// como un parametro de ese método.
		// model.addAttribute("empleado", new Empleado ());
		
		return "formularioAltaModificacion"; // Creamos vista en template
	}
	
	// Método para recibir los datos del formulario de creación de empleado
	// Alta/Modificación Empleado
	// Model Atribute coge al empleado
	@PostMapping("/persistir")
	public String procesarFormularioAltaModificacion (
			@Valid
			@ModelAttribute Empleado empleado,
			BindingResult result, // esto justo depues del objeto empleado
			@RequestParam String numerosTelefono,
			@RequestParam String direccionesCorreo,
			Model model,
			@RequestParam(name="file", required = false) MultipartFile file) {
		// queremos ademas del empleado otro parametro bajo name para numerosTelefono
		// y convertirlo en string. Al convertir a una variable con el mismo nombre que el
		// parametro no hace falta volver a escribirlo.
		
		// Comprobar si hay errores en la información procedente del form
		if (result.hasErrors()) {
			
			model.addAttribute("departamentos", 
				departamentoService.getAllDepartamentos());
			
			return "formularioAltaModificacion"; 
			// si hay errores se vuelve a mostrar el form y hay q decirle q muestre de nuevo los departamentos
		}
		
		// preguntamos si han enviado foto del empleado para guardar el nombre de la foto en la propiedad 
		// atributo o variable mienbro de la clase foto en la tabla y guardar el contenido
		// de la foto com un archivo en el sistema de archivos (file system) del servidor.

		if (file != null && !file.isEmpty()) {

			// de esta forma siempre tendremos la ruta relativa este donde este guardado el proyecto
			Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");
			String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();
			Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

			try {
				byte[] bytesFotoRecibida = file.getBytes();
				Files.write(rutaCompleta, bytesFotoRecibida);
				empleado.setFoto(file.getOriginalFilename());
			
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

		LOG.info("Objeto empleado recibido");
		LOG.info(empleado.toString());
		LOG.info("Números de teléfono recibidos: " + numerosTelefono);
		LOG.info("Direcciones de correo recibidas: " + direccionesCorreo);
		
		// Aquì para que se reciban adecuadamente telefonos y correos, 
		// que vienen en un string separdos por ; 
		// hay que convertirlos en una lista de objetos telefono y correo
		// para luego agregarlos al objeto Empleado antes de persistirlo.
		
		// Declaramos Set de java util donde alojar nuestra lista de telefonos:
		// Set<Telefono> telefonos = new HashSet<Telefono>();
		// comentado lo de arriba pq y lo tenemos en la entidad empleado
		// y lo estamos repitiendo aquí. Ya tenemos el set.
		
		// Pero primero preguntar si nos llegan tenlefonos:
		if (!numerosTelefono.isEmpty() && !numerosTelefono.isBlank()) {
			
			// metemos los elementos de la cadena en un array:
			String[] arrayNumerosTelefono = numerosTelefono.split(";");
			
			// El array lo convertimos en una lista que podremos recorrer
			// Cada elemto es un string y la lista se llama listadoNumeros del tipo Array
			// de java.util lo convetimos en esa lista con .asList al que le pasamos como hemos
			// dicho el arrayNumerosTelefono:
			List<String> listadoNumeros = Arrays.asList(arrayNumerosTelefono);
			
			// le pasamos la lista que recorremos con un forEach y a traves de una lambda
			// los recibe y asigna adecuadamente a cada empleado recibido como parametro:
			// Así se crea la lista de telefonos del empleado:
			listadoNumeros.forEach(numero -> {
				empleado.getTelefonos().add(Telefono
						.builder()
						.numero(numero)
						.empleado(empleado)
						.build());
			});
			
			// nos sobra pq ya tenemos los telefonos del empleado en la sentencia de arriba
			// empleado.setTelefonos(telefonos);
			
		}
	
		// Declaramos Set de java util donde alojar nuestra lista de telefonos:
		// Lo comentamos aquí tambien pq ya tenemos el set creado en la entidad Empleado.
				// Set<Correo> correos = new HashSet<Correo>();
				
				// Pero primero preguntar si nos llegan tenlefonos:
				if (!direccionesCorreo.isEmpty() && !direccionesCorreo.isBlank()) {
					
					// metemos los elementos de la cadena en un array:
					String[] arrayDireccionesCorreo = direccionesCorreo.split(";");
					
					// El array lo convertimos en una lista que podremos recorrer
					// Cada elemto es un string y la lista se llama listadoNumeros el tipo Array
					// de java.util lo convetimos en esa lista con .asList al que le pasamos como hemos
					// dicho el arrayNumerosTelefono:
					List<String> listadoCorreos = Arrays.asList(arrayDireccionesCorreo);
					
					// le pasamos la lista que recorremos con un forEach y a traves de una lambda
					// los recibe y asigna adecuadamente a cada empleado recibido como parametro:
					// Así se crea la lista de telefonos del empleado:
					listadoCorreos.forEach(email -> {
						empleado.getEmails().add(Correo
								.builder()
								.email(email)
								.empleado(empleado)
								.build());
					});
					
					// empleado.setEmails(correos);
					
				}
		
		
		// Se recibe un objeto Empleado con los datos del fromulario
		// Se envía a la capa de servicios para que sea persistido
		empleadoService.saveEmpleado(empleado);
		
		return "redirect:/empleados/listar"; // Redirige a la lista de empleados 
		
	}

	// Método que muestra los detalles de un empleado cuyo id se recibe como 
	// parametro:

	// le indicamos que coja el id del empleado que irá variando. Variable en la ruta.
	@GetMapping("/details/{id}") 
	
	public String mostrarDetalle(Model model,
		// con esto tenemos al empleado de la id recibida al que podremos pedir sus cositas. 
		@PathVariable(name = "id", required = true) int empleado_id) {

			// recuperar el empleado cuyo id se ha recibido como parametro:
			// usamos el metodo que tenemos ya en EmpleadoService
			model.addAttribute("empleado", 
				empleadoService.getEmpleadoById(empleado_id));

		return "details";
	}
}
