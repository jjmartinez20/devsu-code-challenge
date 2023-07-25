package com.devsu.test.servicio;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devsu.test.dto.ClienteDTO;
import com.devsu.test.dto.ClienteRespuestaDTO;
import com.devsu.test.excepcion.ClienteNoEncontradoExcepcion;
import com.devsu.test.excepcion.ClienteRegistradoExcepcion;
import com.devsu.test.modelo.Cliente;
import com.devsu.test.repositorio.ClienteRepositorio;
import com.devsu.test.utilidad.Validador;

/**
 * Servicio que se encarga de realizar las operaciones referentes a los
 * clientes.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Service
public class ClienteServicio {

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	/**
	 * Busca todos los registros de los clientes en la base de datos.
	 * 
	 * @return Todos los registros encontrados.
	 * 
	 */
	public List<Cliente> obtenerClientes() {
		return clienteRepositorio.findAll();
	}

	/**
	 * Busca un cliente a partir de un id.
	 * 
	 * @param id Id de cliente a buscar
	 * @return Instancia del modelo con los datos del cliente si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cliente buscarCliente(long id) {
		return clienteRepositorio.findById(id).orElse(null);
	}

	/**
	 * Busca un cliente a partir de su número de identificación.
	 * 
	 * @param identificacion Número de identificación de cliente a buscar
	 * @return Instancia del modelo con los datos del cliente si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cliente buscarClientePorIdentificacion(String identificacion) {
		return clienteRepositorio.findByIdentificacion(identificacion);
	}

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
	public Cliente nuevoCliente(Cliente cliente) {
		Validador.validarEntidad(cliente);
		Cliente c = buscarClientePorIdentificacion(cliente.getIdentificacion());
		if (c != null)
			throw new ClienteRegistradoExcepcion(String.format(
					"El cliente con número de identificación %s ya está registrado", cliente.getIdentificacion()));
		return clienteRepositorio.save(cliente);
	}

	/**
	 * Actualiza un cliente en la base de datos.
	 * 
	 * @param id Id del cliente a actualizar
	 * @param cliente Entidad con los nuevos datos a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en la
	 *         base de datos o null si el cliente no fue encontrado.
	 * @throws ConstraintViolationException Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * 
	 */
	public Cliente actualizarCliente(long id, Cliente cliente) {
		Validador.validarEntidad(cliente);
		Cliente registroCliente = buscarCliente(id);
		if (registroCliente != null) {
			registroCliente.setNombre(cliente.getNombre());
			registroCliente.setGenero(cliente.getGenero());
			registroCliente.setEdad(cliente.getEdad());
			registroCliente.setIdentificacion(cliente.getIdentificacion());
			registroCliente.setDireccion(cliente.getDireccion());
			registroCliente.setTelefono(cliente.getTelefono());
			registroCliente.setContraseña(cliente.getContraseña());
			registroCliente.setEstado(cliente.isEstado());
			return clienteRepositorio.save(registroCliente);
		} else {
			return null;
		}
	}

	/**
	 * Actualiza los atributos proporcionados de un cliente en la base de datos.
	 * 
	 * @param id Id del cliente a actualizar
	 * @param cambios Mapa con los campos de los atributos y sus valores a actualizar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en la
	 *         base de datos o null si el cliente no fue encontrado.
	 * @throws ConstraintViolationException Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * 
	 */
	public Cliente actualizarCliente(long id, Map<String, Object> cambios) {
		final Cliente cliente = buscarCliente(id);
		if (cliente != null) {
			cambios.forEach((campo, valor) -> {
				switch (campo) {
				case "nombre":
					cliente.setNombre(valor.toString());
					break;
				case "genero":
					cliente.setGenero(valor.toString().charAt(0));
					break;
				case "edad":
					cliente.setEdad((int) valor);
					break;
				case "identificacion":
					cliente.setIdentificacion(valor.toString());
					break;
				case "direccion":
					cliente.setDireccion(valor.toString());
					break;
				case "telefono":
					cliente.setTelefono(valor.toString());
					break;
				case "contraseña":
					cliente.setContraseña(valor.toString());
					break;
				case "estado":
					cliente.setEstado((boolean) valor);
					break;
				default:
					break;
				}
			});
			Validador.validarEntidad(cliente);
			return clienteRepositorio.save(cliente);
		} else {
			return null;
		}
	}

	/**
	 * Busca un cliente e intenta eliminarlo si lo encuentra.
	 * 
	 * @param id Id del cliente a eliminar
	 * @return true si el registro fue eliminado correctamente o false en caso
	 *         contrario.
	 * 
	 */
	public boolean eliminarCliente(long id) {
		Cliente cliente = buscarCliente(id);
		if (cliente != null) {
			clienteRepositorio.delete(cliente);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Pasa los datos de un modelo DTO a un modelo de persistencia.
	 * 
	 * @param dto Modelo DTO a convertir
	 * @return Modelo de persistencia resultante a partir de los datos del modelo
	 *         DTO.
	 * 
	 */
	public Cliente convertirDtoEntidad(ClienteDTO dto) {
		Cliente entidad = new Cliente();
		entidad.setNombre(dto.getNombre());
		entidad.setGenero(dto.getGenero());
		entidad.setEdad(dto.getEdad());
		entidad.setIdentificacion(dto.getIdentificacion());
		entidad.setDireccion(dto.getDireccion());
		entidad.setTelefono(dto.getTelefono());
		entidad.setContraseña(dto.getContraseña());
		entidad.setEstado(dto.isEstado());
		return entidad;
	}

	/**
	 * Pasa los datos de un modelo de persistencia a un modelo DTO de respuesta.
	 * 
	 * @param entidad Modelo de persistencia a convertir
	 * @return Modelo DTO de respuesta a partir del modelo persistente.
	 * 
	 */
	public ClienteRespuestaDTO convertirEntidadDto(Cliente entidad) {
		ClienteRespuestaDTO dto = new ClienteRespuestaDTO();
		dto.setNombre(entidad.getNombre());
		dto.setDireccion(entidad.getDireccion());
		dto.setTelefono(entidad.getTelefono());
		dto.setContraseña(entidad.getContraseña());
		dto.setEstado(entidad.isEstado());
		return dto;
	}

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
	public Cliente verificarExistenciaCliente(long id) {
		Cliente cliente = buscarCliente(id);
		if (cliente == null)
			throw new ClienteNoEncontradoExcepcion(String.format("No se ha encontrado el cliente con id %d", id));
		return cliente;
	}

}
