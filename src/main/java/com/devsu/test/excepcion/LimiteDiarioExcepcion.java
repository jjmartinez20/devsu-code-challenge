package com.devsu.test.excepcion;

/**
 * Excepción lanzada cuando se intenta realizar un retiro y se sobrepasa el
 * límite dirario permitido.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class LimiteDiarioExcepcion extends RuntimeException {

	private static final long serialVersionUID = -5666029212203503502L;

	public LimiteDiarioExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public LimiteDiarioExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
