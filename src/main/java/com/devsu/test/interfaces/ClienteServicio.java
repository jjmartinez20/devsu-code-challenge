package com.devsu.test.interfaces;

import java.util.List;
import java.util.Map;
import com.devsu.test.dto.ClienteDTO;
import com.devsu.test.dto.ClienteRespuestaDTO;
import com.devsu.test.excepcion.ClienteNoEncontradoExcepcion;
import com.devsu.test.excepcion.ClienteRegistradoExcepcion;
import com.devsu.test.modelo.Cliente;

public interface ClienteServicio {

	/**
	 * Busca todos los registros de los clientes en la base de datos.
	 * 
	 * @return Todos los registros encontrados.
	 * 
	 */
	public List<Cliente> obtenerClientes();

	/**
	 * Busca un cliente a partir de un id.
	 * 
	 * @param id Id de cliente a buscar
	 * @return Instancia del modelo con los datos del cliente si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cliente buscarCliente(long id);

	/**
	 * Busca un cliente a partir de su número de identificación.
	 * 
	 * @param identificacion Número de identificación de cliente a buscar
	 * @return Instancia del modelo con los datos del cliente si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cliente buscarClientePorIdentificacion(String identificacion);

	/**
	 * Agrega un nuevo cliente en la base de datos.
	 * 
	 * @param cliente Entidad a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la inserción en la
	 *         base de datos.
	 * @throws ConstraintViolationException Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * @throws ClienteRegistradoExcepcion   Si el número de identificación
	 *                                      proporcionado ya se encuentra registrado
	 * 
	 */
	public Cliente nuevoCliente(Cliente cliente);

	/**
	 * Actualiza un cliente en la base de datos.
	 * 
	 * @param id      Id del cliente a actualizar
	 * @param cliente Entidad con los nuevos datos a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si el cliente no fue encontrado.
	 * @throws ConstraintViolationException Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * 
	 */
	public Cliente actualizarCliente(long id, Cliente cliente);

	/**
	 * Actualiza los atributos proporcionados de un cliente en la base de datos.
	 * 
	 * @param id      Id del cliente a actualizar
	 * @param cambios Mapa con los campos de los atributos y sus valores a
	 *                actualizar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si el cliente no fue encontrado.
	 * @throws ConstraintViolationException Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * 
	 */
	public Cliente actualizarCliente(long id, Map<String, Object> cambios);
	
	public void validarCampoCliente(String campo, String valor, Cliente cliente);

	/**
	 * Busca un cliente e intenta eliminarlo si lo encuentra.
	 * 
	 * @param id Id del cliente a eliminar
	 * @return true si el registro fue eliminado correctamente o false en caso
	 *         contrario.
	 * 
	 */
	public boolean eliminarCliente(long id);

	/**
	 * Pasa los datos de un modelo DTO a un modelo de persistencia.
	 * 
	 * @param dto Modelo DTO a convertir
	 * @return Modelo de persistencia resultante a partir de los datos del modelo
	 *         DTO.
	 * 
	 */
	public Cliente convertirDtoEntidad(ClienteDTO dto);

	/**
	 * Pasa los datos de un modelo de persistencia a un modelo DTO de respuesta.
	 * 
	 * @param entidad Modelo de persistencia a convertir
	 * @return Modelo DTO de respuesta a partir del modelo persistente.
	 * 
	 */
	public ClienteRespuestaDTO convertirEntidadDto(Cliente entidad);

	/**
	 * Busca un cliente a partir de un id y lo retorna si lo encuentra.
	 * 
	 * @param id Id de cliente a buscar
	 * @return Instancia del modelo con los datos del cliente si encuentra el
	 *         registro buscado.
	 * @throws ClienteNoEncontradoExcepcion Si no se encuentra ningún cliente con el
	 *                                      id proporcionado
	 * 
	 */
	public Cliente verificarExistenciaCliente(long id);

}
