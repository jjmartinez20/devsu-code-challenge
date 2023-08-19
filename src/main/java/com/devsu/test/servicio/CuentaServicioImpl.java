package com.devsu.test.servicio;

import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devsu.test.dto.CuentaDTO;
import com.devsu.test.dto.CuentaRespuestaDTO;
import com.devsu.test.excepcion.CuentaNoEncontradaExcepcion;
import com.devsu.test.excepcion.NumeroCuentaRegistradoExcepcion;
import com.devsu.test.interfaces.CuentaServicio;
import com.devsu.test.modelo.Cliente;
import com.devsu.test.modelo.Cuenta;
import com.devsu.test.repositorio.CuentaRepositorio;
import com.devsu.test.utilidad.Mensaje;
import com.devsu.test.utilidad.Validador;

/**
 * Servicio que se encarga de realizar las operaciones referentes a las cuentas.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Service
public class CuentaServicioImpl implements CuentaServicio {

	@Autowired
	private CuentaRepositorio cuentaRepositorio;

	@Autowired
	private ClienteServicioImpl clienteServicio;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cuenta> obtenerCuentas() {
		return cuentaRepositorio.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Cuenta> obtenerCuentasCliente(long clienteId) {
		clienteServicio.verificarExistenciaCliente(clienteId);
		return cuentaRepositorio.findByClienteId(clienteId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cuenta buscarPorId(long id) {
		return cuentaRepositorio.findById(id).orElse(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cuenta buscarPorNumeroCuenta(String numeroCuenta) {
		return cuentaRepositorio.findByNumeroCuenta(numeroCuenta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cuenta nuevaCuenta(Cuenta cuenta) {
		Validador.validarEntidad(cuenta);
		validarNumeroCuenta(cuenta.getNumeroCuenta());
		return cuentaRepositorio.save(cuenta);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cuenta actualizarCuenta(long id, Cuenta cuenta) {
		Validador.validarEntidad(cuenta);
		Cuenta registroCuenta = buscarPorId(id);
		if (registroCuenta != null) {
			Cuenta c = buscarPorNumeroCuenta(cuenta.getNumeroCuenta());
			if (c != null && c.getId() != id)
				throw new NumeroCuentaRegistradoExcepcion(
						String.format(Mensaje.NUMERO_CUENTA_REGISTRADO, cuenta.getNumeroCuenta()));
			BeanUtils.copyProperties(cuenta, registroCuenta, "id");
			return cuentaRepositorio.save(registroCuenta);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cuenta actualizarCuenta(long id, Map<String, Object> cambios) {
		final Cuenta cuenta = buscarPorId(id);
		if (cuenta != null) {
			cambios.forEach((campo, valor) -> validarCampoCuenta(id, campo, valor.toString(), cuenta));
			Validador.validarEntidad(cuenta);
			return cuentaRepositorio.save(cuenta);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validarCampoCuenta(long id, String campo, String valor, Cuenta cuenta) {
		switch (campo) {
		case "numeroCuenta":
			Cuenta c = buscarPorNumeroCuenta(cuenta.getNumeroCuenta());
			if (c != null && c.getId() != id)
				throw new NumeroCuentaRegistradoExcepcion(
						String.format(Mensaje.NUMERO_CUENTA_REGISTRADO, cuenta.getNumeroCuenta()));
			cuenta.setNumeroCuenta(valor);
			break;
		case "cliente":
			Cliente cliente = clienteServicio.verificarExistenciaCliente(Long.valueOf(valor));
			cuenta.setCliente(cliente);
			break;
		case "tipo":
			cuenta.setTipo(valor);
			break;
		case "saldoInicial":
			cuenta.setSaldoInicial(Double.parseDouble(valor));
			break;
		case "estado":
			cuenta.setEstado(Boolean.valueOf(valor));
			break;
		default:
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
	 * {@inheritDoc}
	 */
	@Override
	public Cuenta convertirDtoEntidad(CuentaDTO dto) {
		Cliente cliente = clienteServicio.verificarExistenciaCliente(dto.getCliente());
		Cuenta cuenta = modelMapper.map(dto, Cuenta.class);
		cuenta.setCliente(cliente);
		return cuenta;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CuentaRespuestaDTO convertirEntidadDto(Cuenta entidad) {
		CuentaRespuestaDTO dto = modelMapper.map(entidad, CuentaRespuestaDTO.class);
		dto.setCliente(entidad.getCliente().getNombre());
		return dto;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validarNumeroCuenta(String numeroCuenta) {
		Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);
		if (cuenta != null)
			throw new NumeroCuentaRegistradoExcepcion(String.format(Mensaje.NUMERO_CUENTA_REGISTRADO, numeroCuenta));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cuenta validarExistenciaNumeroCuenta(String numeroCuenta) {
		Cuenta cuenta = buscarPorNumeroCuenta(numeroCuenta);
		if (cuenta == null)
			throw new CuentaNoEncontradaExcepcion(String.format(Mensaje.NUMERO_CUENTA_NO_ENCONTRADO, numeroCuenta));
		return cuenta;
	}

}
