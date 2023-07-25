package com.devsu.test.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar un número de cuenta que ya se
 * encuentra registrado en la base de datos.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class NumeroCuentaRegistradoExcepcion extends RuntimeException {

	private static final long serialVersionUID = 7376758995192411340L;

	public NumeroCuentaRegistradoExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public NumeroCuentaRegistradoExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
