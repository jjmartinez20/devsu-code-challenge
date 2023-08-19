package com.devsu.test.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Modelo que mapea los campos de la entidad de las cuentas que pueden tener los
 * clientes.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Entity
@Data
public class Cuenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	@NotEmpty
	@Size(max = 20)
	private String numeroCuenta;

	@ManyToOne
	@JoinColumn(name = "cliente", nullable = false)
	private Cliente cliente;

	@NotEmpty
	@Size(max = 10)
	private String tipo;

	@Column(precision = 15, scale = 2)
	@NotNull
	private double saldoInicial;

	@NotNull
	private boolean estado;

}
