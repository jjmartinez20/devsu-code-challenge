package com.devsu.test.dto;

import lombok.Data;

/**
 * Modelo que mapea los campos que se mandan como respuesta en el controlador de
 * clientes después de procesar la solicitud.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Data
public class ClienteRespuestaDTO {

	private String nombre;

	private String direccion;

	private String telefono;

	private String contraseña;

	private boolean estado;

}
