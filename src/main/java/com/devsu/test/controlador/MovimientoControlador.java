package com.devsu.test.controlador;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.test.dto.MovimientoDTO;
import com.devsu.test.dto.MovimientoRespuestaDTO;
import com.devsu.test.interfaces.MovimientoServicio;
import com.devsu.test.modelo.Movimiento;

/**
 * Controlador que mapea los endpoints de movimientos
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping("api/movimientos")
@CrossOrigin
public class MovimientoControlador {

	@Autowired
	private MovimientoServicio movimientoServicio;

	/**
	 * Busca todos los movimientos registrados.
	 * 
	 * @return Listado de movimientos registrados.
	 */
	@GetMapping
	public ResponseEntity<Object> obtenerMovimientos() {
		List<MovimientoRespuestaDTO> lista = movimientoServicio.obtenerMovimientos().stream()
				.map(movimientoServicio::convertirEntidadDto).toList();
		return ResponseEntity.ok(lista);
	}

	/**
	 * Busca los movimientos de un cliente a partir de su id
	 * 
	 * @param id Id del cliente a buscar
	 * @return Listado de movimientos que pertenecen al cliente
	 * 
	 */
	@GetMapping(path = "/cliente/{id}")
	public ResponseEntity<Object> obtenerMovimientosCliente(@PathVariable Long id) {
		List<MovimientoRespuestaDTO> lista = movimientoServicio.obtenerMovimientosCliente(id).stream()
				.map(movimientoServicio::convertirEntidadDto).toList();
		return ResponseEntity.ok(lista);
	}

	/**
	 * Busca los movimientos de una cuenta a partir del número de cuenta
	 * 
	 * @param numeroCuenta Número de cuenta a buscar
	 * @return Listado de movimientos que pertenecen a la cuenta buscada
	 * 
	 */
	@GetMapping(path = "/cuenta/{numeroCuenta}")
	public ResponseEntity<Object> obtenerMovimientosCuenta(@PathVariable String numeroCuenta) {
		List<MovimientoRespuestaDTO> lista = movimientoServicio.obtenerMovimientosCuenta(numeroCuenta).stream()
				.map(movimientoServicio::convertirEntidadDto).toList();
		return ResponseEntity.ok(lista);
	}

	/**
	 * Busca la información de un movimiento a partir de su id
	 * 
	 * @param id Id del movimiento a buscar
	 * @return Información del movimiento si fue encontrado
	 * 
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> obtenerMovimiento(@PathVariable Long id) {
		Movimiento movimiento = movimientoServicio.buscarMovimiento(id);
		if (movimiento != null) {
			MovimientoRespuestaDTO respuesta = movimientoServicio.convertirEntidadDto(movimiento);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Agrega un nuevo movimiento a la base de datos.
	 * 
	 * @param dto Datos del movimiento a registrar
	 * @return Información del movimiento si fue correctamente registrado.
	 * 
	 */
	@PostMapping
	public ResponseEntity<Object> nuevoMovimiento(@Valid @RequestBody MovimientoDTO dto) {
		Movimiento movimiento = movimientoServicio.convertirDtoEntidad(dto);
		movimiento = movimientoServicio.nuevoMovimiento(movimiento);
		MovimientoRespuestaDTO respuesta = movimientoServicio.convertirEntidadDto(movimiento);
		return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
	}

	/**
	 * Actualiza la información de un movimiento que es buscado a partir de su id.
	 * 
	 * @param id  Id del movimiento a actualizar
	 * @param dto Datos del movimiento a actualizar
	 * @return Información del movimiento si fue correctamente actualizado
	 * 
	 */
	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> actualizarMovimiento(@Valid @RequestBody MovimientoDTO dto, @PathVariable Long id) {
		Movimiento movimiento = movimientoServicio.convertirDtoEntidad(dto);
		movimiento = movimientoServicio.actualizarMovimiento(id, movimiento);
		if (movimiento != null) {
			MovimientoRespuestaDTO respuesta = movimientoServicio.convertirEntidadDto(movimiento);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Actualiza la información de atributos específicos de un movimiento que es
	 * buscado a partir de su id.
	 * 
	 * @param id      Id del movimiento a parchar
	 * @param cambios Atributos específicos a ser actualizados
	 * @return Información del movimiento si fue correctamente actualizado
	 * 
	 */
	@PatchMapping(path = "/{id}")
	public ResponseEntity<Object> actualizarMovimiento(@RequestBody Map<String, Object> cambios,
			@PathVariable Long id) {
		Movimiento movimiento = movimientoServicio.actualizarMovimiento(id, cambios);
		if (movimiento != null) {
			MovimientoRespuestaDTO respuesta = movimientoServicio.convertirEntidadDto(movimiento);
			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Elimina un movimiento de la base de datos.
	 * 
	 * @param id Id del movimiento a eliminar
	 * @return Respuesta con código HTTP 200 si el registro fue eliminado
	 *         correctamente u otro código de error en caso de algún fallo.
	 * 
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> eliminarMovimiento(@PathVariable Long id) {
		if (movimientoServicio.eliminarMovimiento(id)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
