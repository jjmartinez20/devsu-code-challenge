package com.devsu.test.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.devsu.test.modelo.Cuenta;

/**
 * Repositorio del modelo de cuentas.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Repository
public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

	/**
	 * Busca las cuentas que tiene un cliente a partir del id de este último.
	 * 
	 * @param id Id del cliente con el cual buscar las cuentas
	 * @return Listado con todas las cuentas asociadas al cliente.
	 * 
	 */
	public List<Cuenta> findByClienteId(long id);

	/**
	 * Busca una cuenta a partir del número de cuenta proporcionado.
	 * 
	 * @param cuenta Número de cuenta a buscar
	 * @return Instancia del modelo con los datos de la cuenta si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cuenta findByNumeroCuenta(String cuenta);

}
