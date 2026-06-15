package com.example.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entities.Empleado;
import java.util.List;
import com.example.model.Genero;



//  Creamos interface con la clase y su tipo wrap
//  pq en el diamante no puede ir un tipo primitivo
// @Repository indica que la clase anotada puede recibir beans
// En JpaRepository Empleado es la entidad para la que queremos crear los metodos
// Integer es un wrapper de tipo de dato del id de la entidad.

public interface EmpleadoDao extends JpaRepository<Empleado, Integer> {
	
	/* Para generar métodos, además de los que ya se tienen por defecto en las interfaces
	 * de la cuales hereda JpaRepository hay que hacer suministrando la sintaxis correcta
	 * como se indica en los enlaces siguientes:
	 * 
	 * Oficial:
	 * 
	 * https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
	 * 
	 * Para aprender a generar y de forma más didactica:
	 * 
	 * https://www.baeldung.com/spring-data-derived-queries
	 * 
	 */
	
	// creado generando codigo AOT (Ahead Of Time) código que se genera adelantandose al tiempo.
	// List<Empleado> findByNombre(String nombre);
	
	// lo anterior de momento no hace falta, está en la capa servicios y aquí sobra.
	// basta con lo que implementa JpaRepository
		
    

}
