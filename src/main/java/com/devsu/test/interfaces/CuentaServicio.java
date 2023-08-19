package com.devsu.test.interfaces;

import java.util.List;
import java.util.Map;
import com.devsu.test.dto.CuentaDTO;
import com.devsu.test.dto.CuentaRespuestaDTO;
import com.devsu.test.excepcion.ClienteNoEncontradoExcepcion;
import com.devsu.test.excepcion.CuentaNoEncontradaExcepcion;
import com.devsu.test.excepcion.NumeroCuentaRegistradoExcepcion;
import com.devsu.test.modelo.Cuenta;

public interface CuentaServicio {

	/**
	 * Busca todos los registros de las cuentas en la base de datos.
	 * 
	 * @return Todos los registros encontrados.
	 * 
	 */
	public List<Cuenta> obtenerCuentas();

	/**
	 * Busca todos los registros de las cuentas de un cliente en la base de datos.
	 * 
	 * @param id Id del cliente con el cual buscar los movimientos de todas sus
	 *           cuentas
	 * @return Todos los registros encontrados.
	 * @throws ClienteNoEncontradoExcepcion Si no se encuentra ningún cliente con el
	 *                                      id proporcionado
	 * 
	 */
	public List<Cuenta> obtenerCuentasCliente(long clienteId);

	/**
	 * Busca una cuenta a partir de un id.
	 * 
	 * @param id Id de la cuenta a buscar
	 * @return Instancia del modelo con los datos de la cuenta si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cuenta buscarPorId(long id);

	/**
	 * Busca una cuenta a partir de un número de cuenta.
	 * 
	 * @param numeroCuenta Número de cuenta a buscar
	 * @return Instancia del modelo con los datos de la cuenta si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cuenta buscarPorNumeroCuenta(String numeroCuenta);

	/**
	 * Agrega una nueva cuenta en la base de datos.
	 * 
	 * @param cuenta Entidad a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la inserción en la
	 *         base de datos.
	 * @throws ConstraintViolationException    Si alguno de los valores de los
	 *                                         atributos de la entidad tiene alguna
	 *                                         violación en las restricciones
	 * @throws NumeroCuentaRegistradoExcepcion Si se encuentra un registro con el
	 *                                         número de cuenta proporcionado
	 * 
	 */
	public Cuenta nuevaCuenta(Cuenta cuenta);

	/**
	 * Actualiza una cuenta en la base de datos.
	 * 
	 * @param id     Id de la cuenta a actualizar
	 * @param cuenta Entidad con los nuevos datos a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si la cuenta no fue encontrada.
	 * @throws ConstraintViolationException    Si alguno de los valores de los
	 *                                         atributos de la entidad tiene alguna
	 *                                         violación en las restricciones
	 * @throws NumeroCuentaRegistradoExcepcion Si se encuentra un registro con el
	 *                                         número de cuenta proporcionado
	 * 
	 */
	public Cuenta actualizarCuenta(long id, Cuenta cuenta);

	/**
	 * Actualiza los atributos proporcionados de una cuenta en la base de datos.
	 * 
	 * @param id      Id de la cuenta a actualizar
	 * @param cambios Mapa con los campos de los atributos y sus valores a
	 *                actualizar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si la cuenta no fue encontrada.
	 * @throws ConstraintViolationException    Si alguno de los valores de los
	 *                                         atributos de la entidad tiene alguna
	 *                                         violación en las restricciones
	 * @throws NumeroCuentaRegistradoExcepcion Si se encuentra un registro con el
	 *                                         número de cuenta proporcionado
	 * 
	 */
	public Cuenta actualizarCuenta(long id, Map<String, Object> cambios);

	public void validarCampoCuenta(long id, String campo, String valor, Cuenta cuenta);

	/**
	 * Busca una cuenta e intenta eliminarlo si lo encuentra.
	 * 
	 * @param id Id de la cuenta a eliminar
	 * @return true si el registro fue eliminado correctamente o false en caso
	 *         contrario.
	 * 
	 */
	public boolean eliminarCuenta(long id);

	/**
	 * Pasa los datos de un modelo DTO a un modelo de persistencia.
	 * 
	 * @param dto Modelo DTO a convertir
	 * @return Modelo de persistencia resultante a partir de los datos del modelo
	 *         DTO.
	 * 
	 */
	public Cuenta convertirDtoEntidad(CuentaDTO dto);

	/**
	 * Pasa los datos de un modelo de persistencia a un modelo DTO de respuesta.
	 * 
	 * @param entidad Modelo de persistencia a convertir
	 * @return Modelo DTO de respuesta a partir del modelo persistente.
	 * 
	 */
	public CuentaRespuestaDTO convertirEntidadDto(Cuenta entidad);

	/**
	 * Valida si un número de cuenta ya está registrado.
	 * 
	 * @param numeroCuenta Número de cuenta a buscar
	 * @throws NumeroCuentaRegistradoExcepcion Si se encuentra un registro con el
	 *                                         número de cuenta proporcionado
	 * 
	 */
	public void validarNumeroCuenta(String numeroCuenta);

	/**
	 * Busca una cuenta a partir de un número de cuenta y lo retorna si lo
	 * encuentra.
	 * 
	 * @param numeroCuenta Número de cuenta a buscar
	 * @return Instancia del modelo con los datos de la cuenta si encuentra el
	 *         registro buscado.
	 * @throws CuentaNoEncontradaExcepcion Si no se encuentra ninguna cuenta con el
	 *                                     número proporcionado
	 * 
	 */
	public Cuenta validarExistenciaNumeroCuenta(String numeroCuenta);

}
