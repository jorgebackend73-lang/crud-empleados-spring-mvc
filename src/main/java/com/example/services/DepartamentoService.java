package com.example.services;

import java.util.List;

import com.example.entities.Departamento;

public interface DepartamentoService {
	
	// Método que persiste los departamentos
	Departamento saveDepartamento(Departamento departamento);
	List<Departamento> getAllDepartamentos();

}
