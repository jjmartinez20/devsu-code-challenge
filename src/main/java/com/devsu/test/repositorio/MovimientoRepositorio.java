package com.devsu.test.repositorio;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.devsu.test.modelo.Movimiento;

/**
 * Repositorio del modelo de movimientos.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@Repository
public interface MovimientoRepositorio extends JpaRepository<Movimiento, Long> {

	/**
	 * Busca los movimientos en las cuentas que tiene un cliente a partir del id de
	 * este último.
	 * 
	 * @param id Id del cliente con el cual buscar los movimientos de todas sus
	 *           cuentas
	 * @return Listado con todos los movimientos registrados en todas las cuentas
	 *         del cliente.
	 * 
	 */
	public List<Movimiento> findByCuentaClienteIdOrderByFechaDesc(long id);

	/**
	 * Busca los movimientos de una cuenta a partir del número de cuenta de este
	 * último.
	 * 
	 * @param numeroCuenta Número de cuenta del cual se buscarán sus movimientos
	 * @return Listado con todas los movimientos asociados a la cuenta
	 *         proporcionada.
	 * 
	 */
	public List<Movimiento> findByCuentaNumeroCuentaOrderByFechaDesc(String numeroCuenta);

	/**
	 * Busca la cantidad total de retiros que ha realizado una cuenta en una fecha
	 * en específico.
	 * 
	 * @param numeroCuenta Número de cuenta a buscar.
	 * @param fecha        Fecha que se quiere validar.
	 * @return Total de los retiros realizados en la fecha indicada.
	 * 
	 */
	@Query("SELECT COALESCE(SUM(m.valor), 0) FROM Movimiento m WHERE m.cuenta.numeroCuenta = ?1 AND m.fecha = ?2 AND m.valor < 0")
	public double obtenerRetiroDiarioCuenta(String numeroCuenta, LocalDate fecha);

	/**
	 * Busca los movimientos en las cuentas que tiene un cliente a partir de una
	 * fecha determinada.
	 * 
	 * @param id    Id del cliente con el cual buscar los movimientos de todas sus
	 *              cuentas
	 * @param fecha Fecha a partir de la cual se empezará a buscar los registros
	 * @return Listado con todos los movimientos registrados en todas las cuentas
	 *         del cliente a partir de la fecha proporcionada,
	 * 
	 */
	@Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.id = ?1 AND m.fecha >= ?2 ORDER BY fecha DESC")
	public List<Movimiento> estadoCuentaClienteDesdeFecha(long id, LocalDate fecha);

	/**
	 * Busca los movimientos en las cuentas que tiene un cliente hasta una fecha
	 * determinada.
	 * 
	 * @param id    Id del cliente con el cual buscar los movimientos de todas sus
	 *              cuentas
	 * @param fecha Fecha hasta donde se empezará a buscar los registros
	 * @return Listado con todos los movimientos registrados en todas las cuentas
	 *         del cliente hasta la fecha proporcionada,
	 * 
	 */
	@Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.id = ?1 AND m.fecha <= ?2 ORDER BY fecha DESC")
	public List<Movimiento> estadoCuentaClienteHastaFecha(long id, LocalDate fecha);

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
	 * 
	 */
	@Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.id = ?1 AND m.fecha BETWEEN ?2 AND ?3 ORDER BY fecha DESC")
	public List<Movimiento> estadoCuentaClienteEntreFechas(long id, LocalDate fechaDesde, LocalDate fechaHasta);

}
