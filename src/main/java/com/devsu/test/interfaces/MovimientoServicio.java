package com.devsu.test.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.devsu.test.dto.MovimientoDTO;
import com.devsu.test.dto.MovimientoRespuestaDTO;
import com.devsu.test.excepcion.ClienteNoEncontradoExcepcion;
import com.devsu.test.excepcion.CuentaNoActivaExcepcion;
import com.devsu.test.excepcion.CuentaNoEncontradaExcepcion;
import com.devsu.test.excepcion.LimiteDiarioExcepcion;
import com.devsu.test.excepcion.SaldoNoDisponibleExcepcion;
import com.devsu.test.modelo.Movimiento;

public interface MovimientoServicio {

	/**
	 * Busca todos los registros de los movimientos en la base de datos.
	 * 
	 * @return Todos los registros encontrados.
	 * 
	 */
	public List<Movimiento> obtenerMovimientos();

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
	public List<Movimiento> obtenerMovimientosCliente(long clienteId);

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
	public List<Movimiento> estadoCuentaClienteDesdeFecha(long clienteId, LocalDate fecha);

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
	public List<Movimiento> estadoCuentaClienteHastaFecha(long clienteId, LocalDate fecha);

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
	public List<Movimiento> estadoCuentaClienteEntreFechas(long clienteId, LocalDate fechaDesde, LocalDate fechaHasta);

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
	public List<Movimiento> obtenerMovimientosCuenta(String numeroCuenta);

	/**
	 * Busca un movimiento a partir de un id.
	 * 
	 * @param id Id del movimiento a buscar
	 * @return Instancia del modelo con los datos del movimiento si encuentra el
	 *         registro buscado o null en caso contrario.
	 * 
	 */
	public Movimiento buscarMovimiento(long id);

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
	public Movimiento nuevoMovimiento(Movimiento movimiento);

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
	public Movimiento actualizarMovimiento(long id, Movimiento movimiento);

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
	 * @throws IllegalArgumentExcepcion     Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 * 
	 */
	public Movimiento actualizarMovimiento(long id, Map<String, Object> cambios);

	/**
	 * Valida que el campo mapeado exista en la entidad y que su valor sea correcto
	 * antes de asignarlo a la instancia que será registrada en la base de datos.
	 * 
	 * @param campo      Nombre del campo de la entidad a evaluar
	 * @param valor      Valor del campo a evaluar
	 * @param movimiento Instancia de la entidad a la que se le asignará el valor
	 *                   validado
	 * @throws CuentaNoEncontradaExcepcion  Si no se encuentra ninguna cuenta con el
	 *                                      número proporcionado
	 * @throws IllegalArgumentExcepcion     Si alguno de los valores de los
	 *                                      atributos de la entidad tiene alguna
	 *                                      violación en las restricciones
	 */
	public void validarCampoMovimiento(String campo, String valor, Movimiento movimiento);

	/**
	 * Busca un movimiento e intenta eliminarlo si lo encuentra.
	 * 
	 * @param id Id del movimiento a eliminar
	 * @return true si el registro fue eliminado correctamente o false en caso
	 *         contrario.
	 * 
	 */
	public boolean eliminarMovimiento(long id);

	/**
	 * Pasa los datos de un modelo DTO a un modelo de persistencia.
	 * 
	 * @param dto Modelo DTO a convertir
	 * @return Modelo de persistencia resultante a partir de los datos del modelo
	 *         DTO.
	 * 
	 */
	public Movimiento convertirDtoEntidad(MovimientoDTO dto);

	/**
	 * Pasa los datos de un modelo de persistencia a un modelo DTO de respuesta.
	 * 
	 * @param entidad Modelo de persistencia a convertir
	 * @return Modelo DTO de respuesta a partir del modelo persistente.
	 * 
	 */
	public MovimientoRespuestaDTO convertirEntidadDto(Movimiento entidad);
	
	public String obtenerTipoMovimiento(double valor);

}
