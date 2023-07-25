package com.devsu.test.modelo;

import java.util.Objects;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Modelo que mapea los datos de la entidad de clientes.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Entity
public class Cliente extends Persona {
	
	@NotBlank(message = "Contraseña obligatorio")
	@Size(max = 32, message = "La constraseña no debe exceder los 32 caracteres")
	private String contraseña;
	
	@NotNull
	private boolean estado;

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

	@Override
	public int hashCode() {
		return Objects.hash( contraseña, estado);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return this.getId() == other.getId();
	}

}
