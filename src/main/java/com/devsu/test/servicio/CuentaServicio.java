package com.devsu.test.servicio;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devsu.test.dto.CuentaDTO;
import com.devsu.test.dto.CuentaRespuestaDTO;
import com.devsu.test.excepcion.ClienteNoEncontradoExcepcion;
import com.devsu.test.excepcion.CuentaNoEncontradaExcepcion;
import com.devsu.test.excepcion.NumeroCuentaRegistradoExcepcion;
import com.devsu.test.modelo.Cliente;
import com.devsu.test.modelo.Cuenta;
import com.devsu.test.repositorio.CuentaRepositorio;
import com.devsu.test.utilidad.Validador;

/**
 * Servicio que se encarga de realizar las operaciones referentes a las cuentas.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Service
public class CuentaServicio {

	@Autowired
	private CuentaRepositorio cuentaRepositorio;

	@Autowired
	private ClienteServicio clienteServicio;

	/**
	 * Formato con el mensaje de la excepción de número de cuenta registrado
	 */
	public static final String MENSAJE_NUMERO_CUENTA_REGISTRADO = "El número de cuenta %s ya se encuentra registrado";

	/**
	 * Busca todos los registros de las cuentas en la base de datos.
	 * 
	 * @return Todos los registros encontrados.
	 * 
	 */
	public List<Cuenta> obtenerCuentas() {
		return cuentaRepositorio.findAll();
	}

	/**
	 * Busca todos los registros de las cuentas de un cliente en la base de datos.
	 * 
	 * @param id Id del cliente con el cual buscar los movimientos de todas sus
	 *           cuentas
	 * @return Todos los registros encontrados.
	 * @throws ClienteNoEncontradoExcepcion Si no se encuentra ningún cliente con el
	 *                                      id proporcionado
	 * 
	 */
	public List<Cuenta> obtenerCuentasCliente(long clienteId) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return cuentaRepositorio.findByClienteId(clienteId);
	}

	/**
	 * Busca una cuenta a partir de un id.
	 * 
	 * @param id Id de la cuenta a buscar
	 * @return Instancia del modelo con los datos de la cuenta si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cuenta buscarPorId(long id) {
		return cuentaRepositorio.findById(id).orElse(null);
	}

	/**
	 * Busca una cuenta a partir de un número de cuenta.
	 * 
	 * @param numeroCuenta Número de cuenta a buscar
	 * @return Instancia del modelo con los datos de la cuenta si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Cuenta buscarPorNumeroCuenta(String numeroCuenta) {
		return cuentaRepositorio.findByNumeroCuenta(numeroCuenta);
	}

	/**
	 * Agrega una nueva cuenta en la base de datos.
	 * 
	 * @param cuenta Entidad a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la inserción en la
	 *         base de datos.
	 * @throws ConstraintViolationException    Si alguno de los valores de los
	 *                                         atributos de la entidad tiene alguna
	 *                                         violación en las restricciones
	 * @throws NumeroCuentaRegistradoExcepcion Si se encuentra un registro con el
	 *                                         número de cuenta proporcionado
	 * 
	 */
	public Cuenta nuevaCuenta(Cuenta cuenta) {
		Validador.validarEntidad(cuenta);
		validarNumeroCuenta(cuenta.getNumeroCuenta());
		return cuentaRepositorio.save(cuenta);
	}

	/**
	 * Actualiza una cuenta en la base de datos.
	 * 
	 * @param id     Id de la cuenta a actualizar
	 * @param cuenta Entidad con los nuevos datos a registrar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si la cuenta no fue encontrada.
	 * @throws ConstraintViolationException    Si alguno de los valores de los
	 *                                         atributos de la entidad tiene alguna
	 *                                         violación en las restricciones
	 * @throws NumeroCuentaRegistradoExcepcion Si se encuentra un registro con el
	 *                                         número de cuenta proporcionado
	 * 
	 */
	public Cuenta actualizarCuenta(long id, Cuenta cuenta) {
		Validador.validarEntidad(cuenta);
		Cuenta registroCuenta = buscarPorId(id);
		if (registroCuenta != null) {
			Cuenta c = buscarPorNumeroCuenta(cuenta.getNumeroCuenta());
			if (c != null && c.getId() != id)
				throw new NumeroCuentaRegistradoExcepcion(
						String.format(MENSAJE_NUMERO_CUENTA_REGISTRADO, cuenta.getNumeroCuenta()));
			registroCuenta.setNumeroCuenta(cuenta.getNumeroCuenta());
			registroCuenta.setCliente(cuenta.getCliente());
			registroCuenta.setTipo(cuenta.getTipo());
			registroCuenta.setSaldoInicial(cuenta.getSaldoInicial());
			registroCuenta.setEstado(cuenta.isEstado());
			return cuentaRepositorio.save(registroCuenta);
		} else {
			return null;
		}
	}

	/**
	 * Actualiza los atributos proporcionados de una cuenta en la base de datos.
	 * 
	 * @param id      Id de la cuenta a actualizar
	 * @param cambios Mapa con los campos de los atributos y sus valores a
	 *                actualizar
	 * @return Entidad devuelta por el repositorio al ejecutar la actualización en
	 *         la base de datos o null si la cuenta no fue encontrada.
	 * @throws ConstraintViolationException    Si alguno de los valores de los
	 *                                         atributos de la entidad tiene alguna
	 *                                         violación en las restricciones
	 * @throws NumeroCuentaRegistradoExcepcion Si se encuentra un registro con el
	 *                                         número de cuenta proporcionado
	 * 
	 */
	public Cuenta actualizarCuenta(long id, Map<String, Object> cambios) {
		final Cuenta cuenta = buscarPorId(id);
		if (cuenta != null) {
			cambios.forEach((campo, valor) -> {
				switch (campo) {
				case "numeroCuenta":
					Cuenta c = buscarPorNumeroCuenta(cuenta.getNumeroCuenta());
					if (c != null && c.getId() != id)
						throw new NumeroCuentaRegistradoExcepcion(
								String.format(MENSAJE_NUMERO_CUENTA_REGISTRADO, cuenta.getNumeroCuenta()));
					cuenta.setNumeroCuenta(valor.toString());
					break;
				case "cliente":
					Cliente cliente = clienteServicio.verificarExistenciaCliente((long) valor);
					cuenta.setCliente(cliente);
					break;
				case "tipo":
					cuenta.setTipo(valor.toString());
					break;
				case "saldoInicial":
					cuenta.setSaldoInicial(Double.valueOf(valor.toString()));
					break;
				case "estado":
					cuenta.setEstado((boolean) valor);
					break;
				default:
					break;
				}
			});
			Validador.validarEntidad(cuenta);
			return cuentaRepositorio.save(cuenta);
		} else {
			return null;
		}
	}

	/**
	 * Busca una cuenta e intenta eliminarlo si lo encuentra.
	 * 
	 * @param id Id de la cuenta a eliminar
	 * @return true si el registro fue eliminado correctamente o false en caso
	 *         contrario.
	 * 
	 */
	public boolean eliminarCuenta(long id) {
		Cuenta cuenta = buscarPorId(id);
		if (cuenta != null) {
			cuentaRepositorio.delete(cuenta);
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
	public Cuenta convertirDtoEntidad(CuentaDTO dto) {
		Cliente cliente = clienteServicio.verificarExistenciaCliente(dto.getCliente());
		Cuenta cuenta = new Cuenta();
		cuenta.setNumeroCuenta(dto.getNumeroCuenta());
		cuenta.setCliente(cliente);
		cuenta.setTipo(dto.getTipo());
		cuenta.setSaldoInicial(dto.getSaldoInicial());
		cuenta.setEstado(dto.isEstado());
		return cuenta;
	}

	/**
	 * Pasa los datos de un modelo de persistencia a un modelo DTO de respuesta.
	 * 
	 * @param entidad Modelo de persistencia a convertir
	 * @return Modelo DTO de respuesta a partir del modelo persistente.
	 * 
	 */
	public CuentaRespuestaDTO convertirEntidadDto(Cuenta entidad) {
		CuentaRespuestaDTO dto = new CuentaRespuestaDTO();
		dto.setNumeroCuenta(entidad.getNumeroCuenta());
		dto.setTipo(entidad.getTipo());
		dto.setSaldoInicial(entidad.getSaldoInicial());
		dto.setEstado(entidad.isEstado());
		dto.setCliente(entidad.getCliente().getNombre());
		return dto;
	}

	/**
	 * Valida si un número de cuenta ya está registrado.
	 * 
	 * @param numeroCuenta Número de cuenta a buscar
	 * @throws NumeroCuentaRegistradoExcepcion Si se encuentra un registro con el
	 *                                         número de cuenta proporcionado
	 * 
	 */
	public void validarNumeroCuenta(String numeroCuenta) {
		Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);
		if (cuenta != null)
			throw new NumeroCuentaRegistradoExcepcion(String.format(MENSAJE_NUMERO_CUENTA_REGISTRADO, numeroCuenta));
	}

	/**
	 * Busca una cuenta a partir de un número de cuenta y lo retorna si lo
	 * encuentra.
	 * 
	 * @param numeroCuenta Número de cuenta a buscar
	 * @return Instancia del modelo con los datos de la cuenta si encuentra el
	 *         registro buscado.
	 * @throws CuentaNoEncontradaExcepcion Si no se encuentra ninguna cuenta con el
	 *                                     número proporcionado
	 * 
	 */
	public Cuenta validarExistenciaNumeroCuenta(String numeroCuenta) {
		Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);
		if (cuenta == null)
			throw new CuentaNoEncontradaExcepcion(
					String.format("No se ha encontrado el número de cuenta %s", numeroCuenta));
		return cuenta;
	}

}
