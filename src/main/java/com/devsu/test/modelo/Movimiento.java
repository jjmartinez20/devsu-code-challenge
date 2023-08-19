package com.devsu.test.modelo;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

/**
 * Modelo que mapea los campos de los movimientos que realizan las cuentas.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Entity
@Data
public class Movimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "cuenta", nullable = false)
	private Cuenta cuenta;

	@NotNull
	@DateTimeFormat(pattern = "d/M/yyyy")
	private LocalDate fecha;

	private String tipoTransaccion;

	@Column(precision = 15, scale = 2)
	private double valor;

	@Column(precision = 15, scale = 2)
	private double saldo;

}
