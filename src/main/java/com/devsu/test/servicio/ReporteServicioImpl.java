package com.devsu.test.servicio;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devsu.test.dto.MovimientoRespuestaDTO;
import com.devsu.test.interfaces.ReporteServicio;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReporteServicioImpl implements ReporteServicio {

	@Autowired
	private MovimientoServicioImpl movimientoServicio;

	@Override
	public List<MovimientoRespuestaDTO> obtenerReporteMovimientos(LocalDate desde, LocalDate hasta, Long cliente) {
		log.info(String.format("Reporte de movimientos cliente %d Fecha Inicio: [%s] - Fecha Fin: [%s]", cliente,
				desde != null ? desde.toString() : "N/A", hasta != null ? hasta.toString() : "N/A"));
		if (desde != null && hasta != null && desde.isAfter(hasta)) {
			log.warn(String.format("Rango de fechas inválido. Fecha Inicio: [%s] - Fecha Fin: [%s]", desde.toString(),
					hasta.toString()));
			throw new IllegalArgumentException("Rango de fechas no válido");
		}
		if (desde != null && hasta == null) {
			return movimientoServicio.estadoCuentaClienteDesdeFecha(cliente, desde).stream()
					.map(movimientoServicio::convertirEntidadDto).toList();
		} else if (desde == null && hasta != null) {
			return movimientoServicio.estadoCuentaClienteHastaFecha(cliente, hasta).stream()
					.map(movimientoServicio::convertirEntidadDto).toList();
		} else if (desde != null && hasta != null) {
			return movimientoServicio.estadoCuentaClienteEntreFechas(cliente, desde, hasta).stream()
					.map(movimientoServicio::convertirEntidadDto).toList();
		} else {
			return movimientoServicio.obtenerMovimientosCliente(cliente).stream()
					.map(movimientoServicio::convertirEntidadDto).toList();
		}
	}

}
