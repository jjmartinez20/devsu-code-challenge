package com.devsu.test.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.devsu.test.dto.MovimientoRespuestaDTO;

public interface ReporteServicio {
	
	public List<MovimientoRespuestaDTO> obtenerReporteMovimientos(LocalDate desde, LocalDate hasta, Long cliente);

}
