package com.devsu.test.excepcion;

/**
 * Excepción lanzada cuando se intenta registrar un cliente con un número de
 * identificación que ya se encuentra registrado en la base de datos.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class ClienteRegistradoExcepcion extends RuntimeException {

	private static final long serialVersionUID = 312248122218579524L;

	public ClienteRegistradoExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public ClienteRegistradoExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
