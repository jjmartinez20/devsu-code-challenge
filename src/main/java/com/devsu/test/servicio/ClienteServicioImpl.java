package com.devsu.test.servicio;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devsu.test.dto.ClienteDTO;
import com.devsu.test.dto.ClienteRespuestaDTO;
import com.devsu.test.excepcion.ClienteNoEncontradoExcepcion;
import com.devsu.test.excepcion.ClienteRegistradoExcepcion;
import com.devsu.test.interfaces.ClienteServicio;
import com.devsu.test.modelo.Cliente;
import com.devsu.test.repositorio.ClienteRepositorio;
import com.devsu.test.utilidad.Mensaje;
import com.devsu.test.utilidad.Validador;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio que se encarga de realizar las operaciones referentes a los
 * clientes.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Service
@Slf4j
public class ClienteServicioImpl implements ClienteServicio {

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cliente> obtenerClientes() {
		return clienteRepositorio.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cliente buscarCliente(long id) {
		log.info(String.format("Buscando cliente con id %d", id));
		return clienteRepositorio.findById(id).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cliente buscarClientePorIdentificacion(String identificacion) {
		log.info(String.format("Buscando cliente con identificacion %s", identificacion));
		return clienteRepositorio.findByIdentificacion(identificacion);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cliente nuevoCliente(Cliente cliente) {
		Validador.validarEntidad(cliente);
		Cliente c = buscarClientePorIdentificacion(cliente.getIdentificacion());
		if (c != null)
			throw new ClienteRegistradoExcepcion(
					String.format(Mensaje.IDENTIFICACION_CLIENTE_YA_REGISTRADA, cliente.getIdentificacion()));
		return clienteRepositorio.save(cliente);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cliente actualizarCliente(long id, Cliente cliente) {
		Validador.validarEntidad(cliente);
		Cliente registroCliente = buscarCliente(id);
		if (registroCliente != null) {
			Cliente c = buscarClientePorIdentificacion(cliente.getIdentificacion());
			if (c != null && c.getId() != id)
				throw new ClienteRegistradoExcepcion(
						String.format(Mensaje.IDENTIFICACION_CLIENTE_YA_REGISTRADA, cliente.getIdentificacion()));
			BeanUtils.copyProperties(cliente, registroCliente, "id");
			return clienteRepositorio.save(registroCliente);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cliente actualizarCliente(long id, Map<String, Object> cambios) {
		final Cliente cliente = buscarCliente(id);
		if (cliente != null) {
			cambios.forEach((campo, valor) -> validarCampoCliente(campo, valor.toString(), cliente));
			Validador.validarEntidad(cliente);
			return clienteRepositorio.save(cliente);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validarCampoCliente(String campo, String valor, Cliente cliente) {
		switch (campo) {
		case "nombre":
			cliente.setNombre(valor);
			break;
		case "genero":
			cliente.setGenero(valor.charAt(0));
			break;
		case "edad":
			cliente.setEdad(Integer.parseInt(valor));
			break;
		case "identificacion":
			cliente.setIdentificacion(valor);
			break;
		case "direccion":
			cliente.setDireccion(valor);
			break;
		case "telefono":
			cliente.setTelefono(valor);
			break;
		case "contraseña":
			cliente.setContraseña(valor);
			break;
		case "estado":
			cliente.setEstado(Boolean.parseBoolean(valor));
			break;
		default:
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * {@inheritDoc}
	 */
	@Override
	public Cliente convertirDtoEntidad(ClienteDTO dto) {
		return modelMapper.map(dto, Cliente.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ClienteRespuestaDTO convertirEntidadDto(Cliente entidad) {
		return modelMapper.map(entidad, ClienteRespuestaDTO.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cliente verificarExistenciaCliente(long id) {
		Cliente cliente = buscarCliente(id);
		if (cliente == null)
			throw new ClienteNoEncontradoExcepcion(String.format(Mensaje.ID_CLIENTE_NO_ENCONTRADO, id));
		return cliente;
	}

}
