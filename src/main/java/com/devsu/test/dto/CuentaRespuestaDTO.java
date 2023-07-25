package com.devsu.test.dto;

/**
 * Modelo que mapea los campos que se mandan como respuesta en el controlador de
 * cuentas después de procesar la solicitud.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
public class CuentaRespuestaDTO {

	private String numeroCuenta;

	private String tipo;

	private double saldoInicial;

	private boolean estado;

	private String cliente;

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

}
