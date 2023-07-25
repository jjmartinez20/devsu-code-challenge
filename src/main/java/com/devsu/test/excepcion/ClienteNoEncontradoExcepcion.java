package com.devsu.test.excepcion;

/**
 * Excepción lanzada cuando se intenta realizar alguna transacción con un
 * cliente que no se encuentra registrado en la base de datos.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class ClienteNoEncontradoExcepcion extends RuntimeException {

	private static final long serialVersionUID = 968022806211408120L;

	public ClienteNoEncontradoExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public ClienteNoEncontradoExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
