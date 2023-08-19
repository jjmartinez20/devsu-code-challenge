package com.devsu.test.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Modelo que mapea los campos que se reciben de las peticiones en el
 * controlador de movimientos y su posterior procesamiento.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Data
public class MovimientoDTO {
	
	private String cuenta;
	
	@JsonFormat(pattern = "d/M/yyyy")
	private LocalDate fecha;
	
	private double valor;

}
