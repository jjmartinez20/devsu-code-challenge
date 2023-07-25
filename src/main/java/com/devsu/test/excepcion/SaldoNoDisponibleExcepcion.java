package com.devsu.test.excepcion;

/**
 * Excepción lanzada cuando se intenta realizar un retiro y este sobrepasa el el
 * saldo disponible en la cuenta.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class SaldoNoDisponibleExcepcion extends RuntimeException {

	private static final long serialVersionUID = 2082608627623793847L;

	public SaldoNoDisponibleExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public SaldoNoDisponibleExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
