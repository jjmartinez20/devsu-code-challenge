package com.devsu.test.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Modelo que mapea los campos que se mandan como respuesta en el controlador de
 * movimientos después de procesar la solicitud.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Data
public class MovimientoRespuestaDTO {

	@JsonFormat(pattern = "d/M/yyyy")
	private LocalDate fecha;

	private String cliente;

	private String numeroCuenta;

	private String tipo;

	private double saldoInicial;

	private boolean estado;

	private double movimiento;

	private double saldoDisponible;

}
