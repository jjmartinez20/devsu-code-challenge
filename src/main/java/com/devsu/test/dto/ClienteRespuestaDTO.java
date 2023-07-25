package com.devsu.test.dto;

/**
 * Modelo que mapea los campos que se mandan como respuesta en el controlador de
 * clientes después de procesar la solicitud.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class ClienteRespuestaDTO {

	private String nombre;

	private String direccion;

	private String telefono;

	private String contraseña;

	private boolean estado;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
