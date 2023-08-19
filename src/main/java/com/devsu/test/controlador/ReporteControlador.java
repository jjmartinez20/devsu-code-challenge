package com.devsu.test.controlador;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.test.interfaces.ReporteServicio;

/**
 * Controlador que mapea los endpoints de reportes
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@RestController
@RequestMapping("api/reportes")
@CrossOrigin
public class ReporteControlador {
	
	@Autowired
	private ReporteServicio reporteServicio;

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
		return ResponseEntity.ok(reporteServicio.obtenerReporteMovimientos(desde, hasta, cliente));
	}

}
