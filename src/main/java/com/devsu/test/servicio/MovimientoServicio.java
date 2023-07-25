package com.devsu.test.servicio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devsu.test.dto.MovimientoDTO;
import com.devsu.test.dto.MovimientoRespuestaDTO;
import com.devsu.test.excepcion.ClienteNoEncontradoExcepcion;
import com.devsu.test.excepcion.CuentaNoActivaExcepcion;
import com.devsu.test.excepcion.CuentaNoEncontradaExcepcion;
import com.devsu.test.excepcion.LimiteDiarioExcepcion;
import com.devsu.test.excepcion.SaldoNoDisponibleExcepcion;
import com.devsu.test.modelo.Cuenta;
import com.devsu.test.modelo.Movimiento;
import com.devsu.test.repositorio.MovimientoRepositorio;
import com.devsu.test.utilidad.Validador;

/**
 * Servicio que se encarga de realizar las operaciones referentes a los
 * movimientos.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Service
public class MovimientoServicio {

	@Autowired
	private MovimientoRepositorio movimientoRepositorio;

	@Autowired
	private CuentaServicio cuentaServicio;

	@Autowired
	private ClienteServicio clienteServicio;

	/**
	 * Valor del límite de retiros diarios que puede realizar una cuenta.
	 */
	private static final double LIMITE_DIARIO = 1000.0;

	/**
	 * Busca todos los registros de los movimientos en la base de datos.
	 * 
	 * @return Todos los registros encontrados.
	 * 
	 */
	public List<Movimiento> obtenerMovimientos() {
		return movimientoRepositorio.findAll();
	}

	/**
	 * Busca todos los registros de los movimientos de un cliente en la base de
	 * datos.
	 * 
	 * @param id Id del cliente con el cual buscar los movimientos de todas sus
	 *           cuentas
	 * @return Todos los registros encontrados.
	 * @throws ClienteNoEncontradoExcepcion Si no se encuentra ningún cliente con el
	 *                                      id proporcionado
	 * 
	 */
	public List<Movimiento> obtenerMovimientosCliente(long clienteId) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return movimientoRepositorio.findByCuentaClienteIdOrderByFechaDesc(clienteId);
	}

	/**
	 * Busca los movimientos en las cuentas que tiene un cliente a partir de una
	 * fecha determinada.
	 * 
	 * @param id    Id del cliente con el cual buscar los movimientos de todas sus
	 *              cuentas
	 * @param fecha Fecha a partir de la cual se empezará a buscar los registros
	 * @return Listado con todos los movimientos registrados en todas las cuentas
	 *         del cliente a partir de la fecha proporcionada,
	 * @throws ClienteNoEncontradoExcepcion Si no se encuentra ningún cliente con el
	 *                                      id proporcionado
	 * 
	 */
	public List<Movimiento> estadoCuentaClienteDesdeFecha(long clienteId, LocalDate fecha) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return movimientoRepositorio.estadoCuentaClienteDesdeFecha(clienteId, fecha);
	}

	/**
	 * Busca los movimientos en las cuentas que tiene un cliente hasta una fecha
	 * determinada.
	 * 
	 * @param id    Id del cliente con el cual buscar los movimientos de todas sus
	 *              cuentas
	 * @param fecha Fecha hasta donde se empezará a buscar los registros
	 * @return Listado con todos los movimientos registrados en todas las cuentas
	 *         del cliente hasta la fecha proporcionada,
	 * @throws ClienteNoEncontradoExcepcion Si no se encuentra ningún cliente con el
	 *                                      id proporcionado
	 * 
	 */
	public List<Movimiento> estadoCuentaClienteHastaFecha(long clienteId, LocalDate fecha) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return movimientoRepositorio.estadoCuentaClienteHastaFecha(clienteId, fecha);
	}

	/**
	 * Busca los movimientos en las cuentas que tiene un cliente entre dos fechas
	 * determinadas
	 * 
	 * @param id         Id del cliente con el cual buscar los movimientos de todas
	 *                   sus cuentas
	 * @param fechaDesde Fecha a partir de la cual se empezará a buscar los
	 *                   registros
	 * @param fechaHasta Fecha hasta donde se empezará a buscar los registros
	 * @return Listado con todos los movimientos registrados en todas las cuentas
	 *         que están dentro del rango de fechas propocionadas.
	 * @throws ClienteNoEncontradoExcepcion Si no se encuentra ningún cliente con el
	 *                                      id proporcionado
	 * 
	 */
	public List<Movimiento> estadoCuentaClienteEntreFechas(long clienteId, LocalDate fechaDesde, LocalDate fechaHasta) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return movimientoRepositorio.estadoCuentaClienteEntreFechas(clienteId, fechaDesde, fechaHasta);
	}

	/**
	 * Busca todos los registros de los movimientos de una cuenta en la base de
	 * datos.
	 * 
	 * @param numeroCuenta Número de cuenta de la cual buscar sus movimientos
	 * @return Todos los registros encontrados.
	 * @throws ClienteNoEncontradoExcepcion Si no se encuentra ningún cliente con el
	 *                                      id proporcionado
	 * 
	 */
	public List<Movimiento> obtenerMovimientosCuenta(String numeroCuenta) {
		cuentaServicio.validarExistenciaNumeroCuenta(numeroCuenta);
		return movimientoRepositorio.findByCuentaNumeroCuentaOrderByFechaDesc(numeroCuenta);
	}

	/**
	 * Busca un movimiento a partir de un id.
	 * 
	 * @param id Id del movimiento a buscar
	 * @return Instancia del modelo con los datos del movimiento si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Movimiento buscarMovimiento(long id) {
		return movimientoRepositorio.findById(id).orElse(null);
	}

	/**
	 * Agrega un nuevo movimiento en la base de datos y actualiza el saldo en la
	 * cuenta.
	 * 
	 * @param movimiento Entidad a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la inserción en la
	 *         base de datos.
	 * @throws ConstraintViolationException Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * @throws CuentaNoEncontradaExcepcion  Si no se encuentra ninguna cuenta con el
	 *                                      número proporcionado
	 * @throws CuentaNoActivaExcepcion      Si la cuenta indicada para realizar el
	 *                                      movimiento se encuentra inactiva
	 * @throws IllegalArgumentException     Si el valor del movimiento es 0.
	 * @throws SaldoNoDisponibleExcepcion   Si el valor del retiro es superior al
	 *                                      saldo que tiene la cuenta
	 * @throws LimiteDiarioExcepcion        Si la cuenta ya ha alcanzado el límite
	 *                                      de retiro diario o superaría dicho
	 *                                      límite con el retiro que intenta
	 *                                      realizar
	 * 
	 */
	@Transactional(rollbackOn = Exception.class)
	public Movimiento nuevoMovimiento(Movimiento movimiento) {
		Validador.validarEntidad(movimiento);
		Cuenta cuenta = cuentaServicio.validarExistenciaNumeroCuenta(movimiento.getCuenta().getNumeroCuenta());
		if (!cuenta.isEstado())
			throw new CuentaNoActivaExcepcion("La cuenta está inactiva");
		if (movimiento.getValor() == 0)
			throw new IllegalArgumentException("El valor del movimiento debe ser distinto de cero");
		double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
		if (movimiento.getValor() < 0) {
			if (nuevoSaldo < 0)
				throw new SaldoNoDisponibleExcepcion("Saldo no disponible");
			double retiroDiario = Math.abs(
					movimientoRepositorio.obtenerRetiroDiarioCuenta(cuenta.getNumeroCuenta(), movimiento.getFecha()));
			if (retiroDiario >= LIMITE_DIARIO)
				throw new LimiteDiarioExcepcion(
						String.format("Ya ha alcanzado el límite diario permitido ($%.2f)", LIMITE_DIARIO));
			double diferenciaLimite = retiroDiario + Math.abs(movimiento.getValor()) - LIMITE_DIARIO;
			if (diferenciaLimite > 0)
				throw new LimiteDiarioExcepcion(String.format(
						"No se puede realizar el retiro porque superaría por %.2f el límite diario permitido ($%.2f)",
						diferenciaLimite, LIMITE_DIARIO));
		}
		Movimiento mov = movimientoRepositorio.save(movimiento);
		cuenta.setSaldoInicial(nuevoSaldo);
		cuentaServicio.actualizarCuenta(cuenta.getId(), cuenta);
		return mov;
	}

	/**
	 * Actualiza un movimiento en la base de datos.
	 * 
	 * @param id      Id del movimiento a actualizar
	 * @param cliente Entidad con los nuevos datos a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si el movimiento no fue encontrado.
	 * @throws ConstraintViolationException Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * @throws CuentaNoEncontradaExcepcion  Si no se encuentra ninguna cuenta con el
	 *                                      número proporcionado
	 * 
	 */
	public Movimiento actualizarMovimiento(long id, Movimiento movimiento) {
		Validador.validarEntidad(movimiento);
		Movimiento registroMovimiento = buscarMovimiento(id);
		if (registroMovimiento != null) {
			String numeroCuenta = movimiento.getCuenta().getNumeroCuenta();
			Cuenta cuenta = cuentaServicio.buscarPorNumeroCuenta(numeroCuenta);
			if (cuenta == null)
				throw new CuentaNoEncontradaExcepcion(
						String.format("No se ha encontrado la cuenta con número %s", numeroCuenta));
			registroMovimiento.setCuenta(cuenta);
			double diferenciaValor = registroMovimiento.getValor() - movimiento.getValor();
			registroMovimiento.setFecha(movimiento.getFecha());
			registroMovimiento.setValor(movimiento.getValor());
			registroMovimiento.setSaldo(registroMovimiento.getSaldo() - diferenciaValor);
			return movimientoRepositorio.save(registroMovimiento);
		} else {
			return null;
		}
	}

	/**
	 * Actualiza los atributos proporcionados de un movimiento en la base de datos.
	 * 
	 * @param id      Id del movimiento a actualizar
	 * @param cambios Mapa con los campos de los atributos y sus valores a
	 *                actualizar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si el movimiento no fue encontrado.
	 * @throws ConstraintViolationException Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * @throws CuentaNoEncontradaExcepcion  Si no se encuentra ninguna cuenta con el
	 *                                      número proporcionado
	 * 
	 */
	public Movimiento actualizarMovimiento(long id, Map<String, Object> cambios) {
		final Movimiento movimiento = buscarMovimiento(id);
		if (movimiento != null) {
			cambios.forEach((campo, valor) -> {
				switch (campo) {
				case "cuenta":
					String numeroCuenta = valor.toString().trim();
					Cuenta cuenta = cuentaServicio.buscarPorNumeroCuenta(numeroCuenta);
					if (cuenta == null)
						throw new CuentaNoEncontradaExcepcion(
								String.format("No se ha encontrado la cuenta con número %s", numeroCuenta));
					movimiento.setCuenta(cuenta);
					break;
				case "fecha":
					movimiento.setFecha(
							LocalDate.parse(valor.toString().trim(), DateTimeFormatter.ofPattern("d/M/yyyy")));
					break;
				case "valor":
					double diferenciaValor = Double.valueOf(valor.toString()) - movimiento.getValor();
					movimiento.setValor(Double.valueOf(valor.toString()));
					movimiento.setSaldo(movimiento.getSaldo() - diferenciaValor);
					break;
				default:
					break;
				}
			});
			Validador.validarEntidad(movimiento);
			return movimientoRepositorio.save(movimiento);
		} else {
			return null;
		}
	}

	/**
	 * Busca un movimiento e intenta eliminarlo si lo encuentra.
	 * 
	 * @param id Id del movimiento a eliminar
	 * @return true si el registro fue eliminado correctamente o false en caso
	 *         contrario.
	 * 
	 */
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
	 * Pasa los datos de un modelo DTO a un modelo de persistencia.
	 * 
	 * @param dto Modelo DTO a convertir
	 * @return Modelo de persistencia resultante a partir de los datos del modelo
	 *         DTO.
	 * 
	 */
	public Movimiento convertirDtoEntidad(MovimientoDTO dto) {
		Cuenta cuenta = cuentaServicio.validarExistenciaNumeroCuenta(dto.getCuenta());
		Movimiento movimiento = new Movimiento();
		movimiento.setCuenta(cuenta);
		movimiento.setFecha(dto.getFecha());
		movimiento.setSaldo(cuenta.getSaldoInicial() + dto.getValor());
		movimiento.setValor(dto.getValor());
		return movimiento;
	}

	/**
	 * Pasa los datos de un modelo de persistencia a un modelo DTO de respuesta.
	 * 
	 * @param entidad Modelo de persistencia a convertir
	 * @return Modelo DTO de respuesta a partir del modelo persistente.
	 * 
	 */
	public MovimientoRespuestaDTO convertirEntidadDto(Movimiento entidad) {
		MovimientoRespuestaDTO dto = new MovimientoRespuestaDTO();
		dto.setFecha(entidad.getFecha());
		dto.setCliente(entidad.getCuenta().getCliente().getNombre());
		dto.setNumeroCuenta(entidad.getCuenta().getNumeroCuenta());
		dto.setTipo(entidad.getCuenta().getTipo());
		dto.setSaldoInicial(entidad.getSaldo() - entidad.getValor());
		dto.setEstado(entidad.getCuenta().isEstado());
		dto.setMovimiento(entidad.getValor());
		dto.setSaldoDisponible(entidad.getSaldo());
		return dto;
	}

}
