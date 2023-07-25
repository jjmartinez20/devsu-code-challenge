package com.devsu.test.excepcion;

/**
 * Excepción lanzada cuando se intenta realizar alguna transacción con una
 * cuenta que no se encuentra registrada en la base de datos.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class CuentaNoEncontradaExcepcion extends RuntimeException {
	
	private static final long serialVersionUID = 7051456862845864610L;

	public CuentaNoEncontradaExcepcion(String mensajeError) {
		super(mensajeError);
	}

	public CuentaNoEncontradaExcepcion(String mensajeError, Exception e) {
		super(mensajeError, e);
	}

}
