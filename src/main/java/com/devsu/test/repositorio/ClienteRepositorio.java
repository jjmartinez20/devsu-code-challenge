package com.devsu.test.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.devsu.test.modelo.Cliente;

/**
 * Repositorio del modelo de clientes.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

	/**
	 * Busca un cliente a partir del número de identificación proporcionado.
	 * 
	 * @param identificacion Número de identificación a buscar
	 * @return Instancia del modelo con los datos del cliente si encuentra el
	 *         registro buscado o null en caso contrario.
	 *         
	 */
	public Cliente findByIdentificacion(String identificacion);

}
