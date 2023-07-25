package com.devsu.test.modelo;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Modelo que mapea los datos generales de una persona de las entidades.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@MappedSuperclass
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Nombre obligatorio")
	@Size(max = 75, message = "El nombre no debe exceder los 75 caracteres")
	private String nombre;

	@NotNull(message = "Género obligatorio")
	private char genero;

	@Positive(message = "Edad no válida")
	private int edad;

	@Column(unique = true)
	@NotBlank(message = "Identificación obligatoria")
	@Size(max = 20, message = "La identificación no debe exceder los 20 caracteres")
	private String identificacion;

	@NotBlank(message = "Dirección obligatoria")
	@Size(max = 255, message = "La dirección no debe exceder los 255 caracteres")
	private String direccion;

	@NotBlank(message = "Identificación obligatoria")
	@Size(max = 12, message = "El teléfono no debe exceder los 12 dígitos")
	@Pattern(regexp = "\\d+", message = "El teléfono solo debe incluir dígitos")
	private String telefono;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
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

	@Override
	public int hashCode() {
		return Objects.hash(direccion, edad, genero, id, identificacion, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		return id == other.id;
	}

}
