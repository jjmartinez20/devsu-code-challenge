package com.devsu.test.dto;

import lombok.Data;

/**
 * Modelo que mapea los campos que se reciben de las peticiones en el
 * controlador de cuentas y su posterior procesamiento.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Data
public class CuentaDTO {

	private String numeroCuenta;

	private long cliente;

	private String tipo;

	private double saldoInicial;

	private boolean estado;

}
