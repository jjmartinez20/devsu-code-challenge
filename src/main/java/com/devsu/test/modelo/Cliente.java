package com.devsu.test.modelo;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Modelo que mapea los datos de la entidad de clientes.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Cliente extends Persona {
	
	@NotBlank(message = "Contraseña obligatorio")
	@Size(max = 32, message = "La constraseña no debe exceder los 32 caracteres")
	private String contraseña;
	
	@NotNull
	private boolean estado;

}
