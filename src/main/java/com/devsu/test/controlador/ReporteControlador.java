package com.devsu.test.controlador;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.test.dto.MovimientoRespuestaDTO;
import com.devsu.test.servicio.MovimientoServicio;

/**
 * Controlador que mapea los endpoints de reportes
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping("api/reportes")
public class ReporteControlador {

	@Autowired
	private MovimientoServicio movimientoServicio;

	/**
	 * Genera el estado de cuenta de todas las cuentas de un cliente en un rango de
	 * fechas determinado.
	 * 
	 * @param desde   Fecha a partir de la cual se generará el estado de cuenta
	 *                (Opcional)
	 * @param hasta   Fecha hasta la cual se generará el estado de cuenta (Opcional)
	 * @param cliente Id del cliente del cual se generará el estado de cuenta
	 *                (Requerido)
	 * @return Registros del estado de cuenta
	 * 
	 */
	@GetMapping
	public ResponseEntity<Object> estadoCuenta(
			@RequestParam(required = false) @DateTimeFormat(pattern = "d/M/yyyy") LocalDate desde,
			@RequestParam(required = false) @DateTimeFormat(pattern = "d/M/yyyy") LocalDate hasta,
			@RequestParam(required = true) Long cliente) {
		if (desde != null && hasta != null && desde.isAfter(hasta))
			throw new IllegalArgumentException("Rango de fechas no válido");
		List<MovimientoRespuestaDTO> lista;
		if (desde != null && hasta == null) {
			lista = movimientoServicio.estadoCuentaClienteDesdeFecha(cliente, desde).stream()
					.map(movimientoServicio::convertirEntidadDto).collect(Collectors.toList());
		} else if (desde == null && hasta != null) {
			lista = movimientoServicio.estadoCuentaClienteHastaFecha(cliente, hasta).stream()
					.map(movimientoServicio::convertirEntidadDto).collect(Collectors.toList());
		} else if (desde != null && hasta != null) {
			lista = movimientoServicio.estadoCuentaClienteEntreFechas(cliente, desde, hasta).stream()
					.map(movimientoServicio::convertirEntidadDto).collect(Collectors.toList());
		} else {
			lista = movimientoServicio.obtenerMovimientosCliente(cliente).stream()
					.map(movimientoServicio::convertirEntidadDto).collect(Collectors.toList());
		}
		return ResponseEntity.ok(lista);
	}

}
