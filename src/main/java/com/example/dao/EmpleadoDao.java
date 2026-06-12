package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Empleado;

//  Creamos interface con la clase y su tipo wrap
//  pq en el diamante no puede ir un tipo primitivo
// @Repository indica que la clase anotada puede recibir beans

@Repository
public interface EmpleadoDao extends JpaRepository<Empleado, Integer> {

    

}
