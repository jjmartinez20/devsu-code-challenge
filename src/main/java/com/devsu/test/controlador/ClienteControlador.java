package com.devsu.test.controlador;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.test.dto.ClienteDTO;
import com.devsu.test.dto.ClienteRespuestaDTO;
import com.devsu.test.modelo.Cliente;
import com.devsu.test.servicio.ClienteServicio;

/**
 * Controlador que mapea los endpoints de clientes
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping("api/clientes")
public class ClienteControlador {

	@Autowired
	private ClienteServicio clienteServicio;

	/**
	 * Busca todos los clientes registrados.
	 * 
	 * @return Listado de clientes registrados.
	 */
	@GetMapping
	public ResponseEntity<Object> obtenerClientes() {
		List<ClienteRespuestaDTO> lista = clienteServicio.obtenerClientes().stream()
				.map(clienteServicio::convertirEntidadDto).collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	/**
	 * Busca la información de un cliente a partir de su id
	 * 
	 * @param id Id del cliente a buscar
	 * @return Información del cliente si fue encontrado
	 * 
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> obtenerCliente(@PathVariable Long id) {
		Cliente cliente = clienteServicio.buscarCliente(id);
		if (cliente != null) {
			ClienteRespuestaDTO respuesta = clienteServicio.convertirEntidadDto(cliente);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Agrega un nuevo cliente a la base de datos.
	 * 
	 * @param dto Datos del cliente a registrar
	 * @return Información del cliente si fue correctamente registrado.
	 * 
	 */
	@PostMapping
	public ResponseEntity<Object> nuevoCliente(@Valid @RequestBody ClienteDTO dto) {
		Cliente cliente = clienteServicio.convertirDtoEntidad(dto);
		cliente = clienteServicio.nuevoCliente(cliente);
		ClienteRespuestaDTO respuesta = clienteServicio.convertirEntidadDto(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
	}

	/**
	 * Actualiza la información de un cliente que es buscado a partir de su id.
	 * 
	 * @param id  Id del cliente a actualizar
	 * @param dto Datos del cliente a actualizar
	 * @return Información del cliente si fue correctamente actualizado
	 * 
	 */
	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> actualizarCliente(@Valid @RequestBody ClienteDTO dto, @PathVariable Long id) {
		Cliente cliente = clienteServicio.convertirDtoEntidad(dto);
		cliente = clienteServicio.actualizarCliente(id, cliente);
		if (cliente != null) {
			ClienteRespuestaDTO respuesta = clienteServicio.convertirEntidadDto(cliente);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Actualiza la información de atributos específicos de un cliente que es
	 * buscado a partir de su id.
	 * 
	 * @param id      Id del cliente a parchar
	 * @param cambios Atributos específicos a ser actualizados
	 * @return Información del cliente si fue correctamente actualizado
	 * 
	 */
	@PatchMapping(path = "/{id}")
	public ResponseEntity<Object> actualizarCliente(@RequestBody Map<String, Object> cambios, @PathVariable Long id) {
		Cliente cliente = clienteServicio.actualizarCliente(id, cambios);
		if (cliente != null) {
			ClienteRespuestaDTO respuesta = clienteServicio.convertirEntidadDto(cliente);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Elimina un cliente de la base de datos.
	 * 
	 * @param id Id del cliente a eliminar
	 * @return Respuesta con código HTTP 200 si el registro fue eliminado
	 *         correctamente u otro código de error en caso de algún fallo.
	 * 
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> eliminarCliente(@PathVariable Long id) {
		if (clienteServicio.eliminarCliente(id)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
