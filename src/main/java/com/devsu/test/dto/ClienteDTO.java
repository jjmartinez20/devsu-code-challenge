package com.devsu.test.dto;

import lombok.Data;

/**
 * Modelo que mapea los campos que se reciben de las peticiones en el
 * controlador de clientes y su posterior procesamiento.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Data
public class ClienteDTO {

	private String nombre;

	private char genero;

	private short edad;

	private String identificacion;

	private String direccion;

	private String telefono;

	private String contraseña;

	private boolean estado;

}
