package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.model.Genero;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;

import lombok.RequiredArgsConstructor;

// para que todo vaya bien implementamos CommandLineRunner

@RequiredArgsConstructor
@SpringBootApplication
public class CrudEmpleadosSpringMvcApplication implements CommandLineRunner {

	private final EmpleadoService empleadoService;
	private final DepartamentoService departamentoService;
	private final CorreoService correoService;
	private final TelefonoService telefonoService;
	
	public static void main(String[] args) {
		SpringApplication.run(CrudEmpleadosSpringMvcApplication.class, args);
	}

	// Cuando le demos a run ejecutará todo lo que tengamos a continuación
	@Override
	public void run(String... args) throws Exception {
		// crear registros de ejemplo para la base de datos.
		// Así comprobamos si la aplicación y su capa de servicios funciona correctamente
		// y también si funciona la capa de persistencia.
		
		/*
		 * Crear Departamentos hay que crear método
		 * */
		
		Departamento departamento1 = Departamento.builder()
				.nombre("RRHH")
				.build();
		
		Departamento departamento2 = Departamento.builder()
				.nombre("IT")
				.build();
		
		Departamento departamento3 = Departamento.builder()
				.nombre("Marketing")
				.build();
		
		Departamento departamento4 = Departamento.builder()
				.nombre("Ventas")
				.build();
		
		Departamento departamento5 = Departamento.builder()
				.nombre("Informatica")
				.build();
		
		// Persistir los departamentos en la base de datos
		departamentoService.saveDepartamento(departamento1);
		departamentoService.saveDepartamento(departamento2);
		departamentoService.saveDepartamento(departamento3);
		departamentoService.saveDepartamento(departamento4);
		departamentoService.saveDepartamento(departamento5);
		
		// Crear Empleados
		Empleado empleado1 = Empleado.builder()
				.nombre("Juan")
				.primerApellido("Perez")
				.segundoApellido("Garcia")
				.genero(Genero.HOMBRE)
				.fechaAlta(LocalDate.of(2026, 1, 15))
				.departamento(departamento1)
				.salario(new BigDecimal(3500.50))
				.telefonos(Set.of(Telefono.builder().numero("456784329").build(),
						Telefono.builder().numero("678954453").build()))
				.emails(Set.of(Correo.builder().email("mp@g.com").build(),
						Correo.builder().email("emp2@g.com").build()))
				.build();
		
		// Antes de persistir el empleado, para que en las tablas de correos y teléfonos
		// el campo empleado_id no sea nulo, hay que establecer la relación entre
		// el empleado y sus correos y teléfonos.
		
		empleado1.getTelefonos().forEach(telefono -> telefono.setEmpleado(empleado1));
		empleado1.getEmails().forEach(correo -> correo.setEmpleado(empleado1));
		
		
		empleadoService.saveEmpleado(empleado1);
		
	
	}

}
