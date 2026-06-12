package com.example.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.model.Genero;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="empleados")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Empleado {

    // Con las dos anotaciones de Id y GeneratedValue hacemos
    // el id autoincremental, primary key y no admite nulo. 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

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



}
