package com.devsu.test.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Modelo que mapea los campos que se reciben de las peticiones en el
 * controlador de movimientos y su posterior procesamiento.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class MovimientoDTO {
	
	private String cuenta;
	
	@JsonFormat(pattern = "d/M/yyyy")
	private LocalDate fecha;
	
	private double valor;

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
