package com.example.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.model.Genero;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="empleados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"telefonos", "emails"})
@Builder
public class Empleado implements Serializable {

    // hay que serializar esta entidad para que extraiga correctamente los datos de la tabla
	private static final long serialVersionUID = 1L;

	// Con las dos anotaciones de Id y GeneratedValue hacemos
    // el id autoincremental, primary key y no admite nulo. 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    // Anotaciones de validación:
    @NotNull(message = "El nombre no puede estar vacio.")
    @NotBlank(message = "El nombre no puede estar en blanco.")
    @Size(min = 4, max = 30, message = "El nombre debe tener entre 4 y 30 caracteres.")
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    
    // para que no guarde ordinal y guarde el nombre hay que anotar:
    @Enumerated(EnumType.STRING)
    private Genero genero;

    // formato canonico de fecha (mm serían minutos en vez de meses)
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private LocalDate fechaAlta;

    private BigDecimal salario;

    // Relación de muchos a uno. Aunq se bidireccional seguimos teneiendo que distinguir
    // entre mmuchos a uno y uno a muchos
    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;

    // para que se pasen telefonos y correos cuando creemos o grabemos un empleado
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "empleado")
    @Builder.Default
    private Set<Telefono> telefonos = new HashSet<>();
    // no debería ser necesario, pero resulta mejor inicializar estas colecciones de teléfonos y correos
    // así evitamos también null pointer exception.

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "empleado")
    @Builder.Default
    private Set<Correo> emails = new HashSet<>();




}
