package com.devsu.test.modelo;

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
import lombok.Data;

/**
 * Modelo que mapea los datos generales de una persona de las entidades.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@MappedSuperclass
@Data
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

}
