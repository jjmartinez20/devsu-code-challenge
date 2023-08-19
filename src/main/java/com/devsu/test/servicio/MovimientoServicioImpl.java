package com.devsu.test.servicio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devsu.test.dto.MovimientoDTO;
import com.devsu.test.dto.MovimientoRespuestaDTO;
import com.devsu.test.excepcion.CuentaNoActivaExcepcion;
import com.devsu.test.excepcion.CuentaNoEncontradaExcepcion;
import com.devsu.test.excepcion.LimiteDiarioExcepcion;
import com.devsu.test.excepcion.SaldoNoDisponibleExcepcion;
import com.devsu.test.interfaces.MovimientoServicio;
import com.devsu.test.modelo.Cuenta;
import com.devsu.test.modelo.Movimiento;
import com.devsu.test.repositorio.MovimientoRepositorio;
import com.devsu.test.utilidad.Mensaje;
import com.devsu.test.utilidad.Validador;
import lombok.extern.slf4j.Slf4j;

/**
 * Servicio que se encarga de realizar las operaciones referentes a los
 * movimientos.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Service
@Slf4j
public class MovimientoServicioImpl implements MovimientoServicio {

	@Autowired
	private MovimientoRepositorio movimientoRepositorio;

	@Autowired
	private CuentaServicioImpl cuentaServicio;

	@Autowired
	private ClienteServicioImpl clienteServicio;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Valor del límite de retiros diarios que puede realizar una cuenta.
	 */
	private static final double LIMITE_DIARIO = 1000.0;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Movimiento> obtenerMovimientos() {
		return movimientoRepositorio.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Movimiento> obtenerMovimientosCliente(long clienteId) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return movimientoRepositorio.findByCuentaClienteIdOrderByFechaDesc(clienteId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Movimiento> estadoCuentaClienteDesdeFecha(long clienteId, LocalDate fecha) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return movimientoRepositorio.estadoCuentaClienteDesdeFecha(clienteId, fecha);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Movimiento> estadoCuentaClienteHastaFecha(long clienteId, LocalDate fecha) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return movimientoRepositorio.estadoCuentaClienteHastaFecha(clienteId, fecha);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Movimiento> estadoCuentaClienteEntreFechas(long clienteId, LocalDate fechaDesde, LocalDate fechaHasta) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return movimientoRepositorio.estadoCuentaClienteEntreFechas(clienteId, fechaDesde, fechaHasta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Movimiento> obtenerMovimientosCuenta(String numeroCuenta) {
		cuentaServicio.validarExistenciaNumeroCuenta(numeroCuenta);
		return movimientoRepositorio.findByCuentaNumeroCuentaOrderByFechaDesc(numeroCuenta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Movimiento buscarMovimiento(long id) {
		return movimientoRepositorio.findById(id).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Movimiento nuevoMovimiento(Movimiento movimiento) {
		Validador.validarEntidad(movimiento);
		Cuenta cuenta = cuentaServicio.validarExistenciaNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
		if (!cuenta.isEstado())
			throw new CuentaNoActivaExcepcion("La cuenta está inactiva");
		if (movimiento.getValor() == 0)
			throw new IllegalArgumentException(Mensaje.MENSAJE_ERROR_VALOR);
		double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
		if (movimiento.getValor() < 0) {
			if (nuevoSaldo < 0)
				throw new SaldoNoDisponibleExcepcion("Saldo no disponible");
			double retiroDiario = Math.abs(
					movimientoRepositorio.obtenerRetiroDiarioCuenta(cuenta.getNumeroCuenta(), movimiento.getFecha()));
			if (retiroDiario >= LIMITE_DIARIO)
				throw new LimiteDiarioExcepcion(String.format(Mensaje.LIMITE_DIARIO_ALCANZADO, LIMITE_DIARIO));
			double diferenciaLimite = retiroDiario + Math.abs(movimiento.getValor()) - LIMITE_DIARIO;
			if (diferenciaLimite > 0)
				throw new LimiteDiarioExcepcion(
						String.format(Mensaje.LIMITE_DIARIO_EXCEDIDO, diferenciaLimite, LIMITE_DIARIO));
		}
		Movimiento mov = movimientoRepositorio.save(movimiento);
		cuenta.setSaldoInicial(nuevoSaldo);
		cuentaServicio.actualizarCuenta(cuenta.getId(), cuenta);
		return mov;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Movimiento actualizarMovimiento(long id, Movimiento movimiento) {
		Validador.validarEntidad(movimiento);
		Movimiento registroMovimiento = buscarMovimiento(id);
		if (registroMovimiento != null) {
			String numeroCuenta = movimiento.getCuenta().getNumeroCuenta();
			Cuenta cuenta = cuentaServicio.buscarPorNumeroCuenta(numeroCuenta);
			if (cuenta == null)
				throw new CuentaNoEncontradaExcepcion(String.format(Mensaje.NUMERO_CUENTA_NO_ENCONTRADO, numeroCuenta));
			if (movimiento.getValor() == 0)
				throw new IllegalArgumentException(Mensaje.MENSAJE_ERROR_VALOR);
			registroMovimiento.setCuenta(cuenta);
			double diferenciaValor = registroMovimiento.getValor() - movimiento.getValor();
			registroMovimiento.setFecha(movimiento.getFecha());
			registroMovimiento.setValor(movimiento.getValor());
			registroMovimiento.setSaldo(registroMovimiento.getSaldo() - diferenciaValor);
			registroMovimiento.setTipoTransaccion(obtenerTipoMovimiento(movimiento.getValor()));
			return movimientoRepositorio.save(registroMovimiento);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Movimiento actualizarMovimiento(long id, Map<String, Object> cambios) {
		final Movimiento movimiento = buscarMovimiento(id);
		if (movimiento != null) {
			cambios.forEach((campo, valor) -> validarCampoMovimiento(campo, valor.toString(), movimiento));
			Validador.validarEntidad(movimiento);
			return movimientoRepositorio.save(movimiento);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validarCampoMovimiento(String campo, String valor, Movimiento movimiento) {
		switch (campo) {
		case "cuenta":
			String numeroCuenta = valor.trim();
			Cuenta cuenta = cuentaServicio.buscarPorNumeroCuenta(numeroCuenta);
			if (cuenta == null)
				throw new CuentaNoEncontradaExcepcion(String.format(Mensaje.NUMERO_CUENTA_NO_ENCONTRADO, numeroCuenta));
			movimiento.setCuenta(cuenta);
			break;
		case "fecha":
			try {
				movimiento.setFecha(LocalDate.parse(valor.trim(), DateTimeFormatter.ofPattern("d/M/yyyy")));
			} catch (Exception e) {
				log.error("Error al parsear fecha", e);
				throw new IllegalArgumentException("Formato de fecha no válido.");
			}
			break;
		case "valor":
			double valorD = Double.parseDouble(valor);
			if (valorD == 0)
				throw new IllegalArgumentException(Mensaje.MENSAJE_ERROR_VALOR);
			double diferenciaValor = valorD - movimiento.getValor();
			movimiento.setValor(valorD);
			movimiento.setSaldo(movimiento.getSaldo() - diferenciaValor);
			movimiento.setTipoTransaccion(obtenerTipoMovimiento(valorD));
			break;
		default:
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean eliminarMovimiento(long id) {
		Movimiento movimiento = buscarMovimiento(id);
		if (movimiento != null) {
			movimientoRepositorio.delete(movimiento);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Movimiento convertirDtoEntidad(MovimientoDTO dto) {
		Cuenta cuenta = cuentaServicio.validarExistenciaNumeroCuenta(dto.getCuenta());
		Movimiento movimiento = modelMapper.map(dto, Movimiento.class);
		movimiento.setCuenta(cuenta);
		movimiento.setSaldo(cuenta.getSaldoInicial() + dto.getValor());
		movimiento.setTipoTransaccion(obtenerTipoMovimiento(dto.getValor()));
		return movimiento;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovimientoRespuestaDTO convertirEntidadDto(Movimiento entidad) {
		MovimientoRespuestaDTO dto = modelMapper.map(entidad, MovimientoRespuestaDTO.class);
		dto.setCliente(entidad.getCuenta().getCliente().getNombre());
		dto.setNumeroCuenta(entidad.getCuenta().getNumeroCuenta());
		dto.setTipo(entidad.getCuenta().getTipo());
		dto.setSaldoInicial(entidad.getSaldo() - entidad.getValor());
		dto.setEstado(entidad.getCuenta().isEstado());
		return dto;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String obtenerTipoMovimiento(double valor) {
		return valor > 0 ? "CR" : "DB";
	}

}
