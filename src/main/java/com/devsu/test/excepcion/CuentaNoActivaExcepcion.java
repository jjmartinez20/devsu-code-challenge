package com.devsu.test.excepcion;

/**
 * Excepción lanzada cuando se intenta realizar alguna transacción con una
 * cuenta que se encuentra inactiva.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class CuentaNoActivaExcepcion extends RuntimeException {
	
	private static final long serialVersionUID = 5303541811859724634L;

	public CuentaNoActivaExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public CuentaNoActivaExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
