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
import com.devsu.test.dto.CuentaDTO;
import com.devsu.test.dto.CuentaRespuestaDTO;
import com.devsu.test.modelo.Cuenta;
import com.devsu.test.servicio.CuentaServicio;

/**
 * Controlador que mapea los endpoints de cuentas
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping("api/cuentas")
public class CuentaControlador {
	
	@Autowired
	private CuentaServicio cuentaServicio;
	
	/**
	 * Busca todas las cuentas registradas.
	 * 
	 * @return Listado de cuentas registradas.
	 */
	@GetMapping
	public ResponseEntity<Object> obtenerCuentas() {
		List<CuentaRespuestaDTO> lista = cuentaServicio.obtenerCuentas().stream()
				.map(cuentaServicio::convertirEntidadDto).collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}
	
	/**
	 * Busca las cuentas de un cliente a partir de su id
	 * 
	 * @param id Id del cliente a buscar
	 * @return Listado de cuentas que pertenecen al cliente
	 * 
	 */
	@GetMapping(path = "/cliente/{id}")
	public ResponseEntity<Object> obtenerCuentasCliente(@PathVariable Long id) {
		List<CuentaRespuestaDTO> lista = cuentaServicio.obtenerCuentasCliente(id).stream()
				.map(cuentaServicio::convertirEntidadDto).collect(Collectors.toList());
		return ResponseEntity.ok(lista);
	}

	/**
	 * Busca todas las cuentas registradas de un cliente
	 * 
	 * @param id Id del cliente a buscar
	 * @return Información de las cuentas del cliente si fue encontrado
	 * 
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> obtenerCuenta(@PathVariable Long id) {
		Cuenta cuenta = cuentaServicio.buscarPorId(id);
		if (cuenta != null) {
			CuentaRespuestaDTO respuesta = cuentaServicio.convertirEntidadDto(cuenta);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Agrega una nueva cuenta a la base de datos.
	 * 
	 * @param dto Datos de la cuenta a registrar
	 * @return Información de la cuenta si fue correctamente registrada.
	 * 
	 */
	@PostMapping
	public ResponseEntity<Object> nuevaCuenta(@Valid @RequestBody CuentaDTO dto) {
		Cuenta cuenta = cuentaServicio.convertirDtoEntidad(dto);
		cuenta = cuentaServicio.nuevaCuenta(cuenta);
		CuentaRespuestaDTO respuesta = cuentaServicio.convertirEntidadDto(cuenta);
		return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
	}

	/**
	 * Actualiza la información de una cuenta que es buscada a partir de su id.
	 * 
	 * @param id  Id de la cuenta a actualizar
	 * @param dto Datos de la cuenta a actualizar
	 * @return Información de la cuenta si fue correctamente actualizada
	 * 
	 */
	@PutMapping(path = "{id}")
	public ResponseEntity<Object> actualizarCuenta(@Valid @RequestBody CuentaDTO dto, @PathVariable Long id) {
		Cuenta cuenta = cuentaServicio.convertirDtoEntidad(dto);
		cuenta = cuentaServicio.actualizarCuenta(id, cuenta);
		if (cuenta != null) {
			CuentaRespuestaDTO respuesta = cuentaServicio.convertirEntidadDto(cuenta);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Actualiza la información de atributos específicos de una cuenta que es
	 * buscado a partir de su id.
	 * 
	 * @param id      Id de la cuenta a parchar
	 * @param cambios Atributos específicos a ser actualizados
	 * @return Información de la cuenta si fue correctamente actualizada
	 * 
	 */
	@PatchMapping(path = "{id}")
	public ResponseEntity<Object> actualizarCliente(@RequestBody Map<String, Object> cambios, @PathVariable Long id) {
		Cuenta cuenta = cuentaServicio.actualizarCuenta(id, cambios);
		if (cuenta != null) {
			CuentaRespuestaDTO respuesta = cuentaServicio.convertirEntidadDto(cuenta);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Elimina una cuenta de la base de datos.
	 * 
	 * @param id Id de la cuenta a eliminar
	 * @return Respuesta con código HTTP 200 si el registro fue eliminado
	 *         correctamente u otro código de error en caso de algún fallo.
	 * 
	 */
	@DeleteMapping(path = "{id}")
	public ResponseEntity<Object> eliminarCliente(@PathVariable Long id) {
		if (cuentaServicio.eliminarCuenta(id)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
