package com.devsu.test.dto;

import lombok.Data;

/**
 * Modelo que mapea los campos que se mandan como respuesta en el controlador de
 * cuentas después de procesar la solicitud.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Data
public class CuentaRespuestaDTO {

	private String numeroCuenta;

	private String tipo;

	private double saldoInicial;

	private boolean estado;

	private String cliente;

}
