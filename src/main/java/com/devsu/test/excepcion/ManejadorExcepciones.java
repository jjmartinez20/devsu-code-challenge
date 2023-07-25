package com.devsu.test.excepcion;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Clase que manejará de manera global las excepciones que puedan ser lanzadas
 * por alguno de los controladores o servicios de la aplicación.
 * 
 * @author Jefry Martínez
 * @version 1.0.0
 *
 */
@ControllerAdvice
public class ManejadorExcepciones {

	/**
	 * Instancia del logger que registrará las excepciones que maneja la clase
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ManejadorExcepciones.class);

	/**
	 * Maneja los errores que puedan ocurrir al validar el valor de los atributos de
	 * los modelos con sus respectivas anotaciones de validación.
	 * 
	 * @param e Excepción lanzada al validar los atributos de un modelo
	 * @return Respuesta que se le envía al cliente
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<Object> manejarExcepcionValidacionDeCampos(ConstraintViolationException e) {
		LOGGER.error("Campo(s) no válido(s): ", e);
		String mensaje = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));
		Map<String, Object> respuesta = generarEstructuraRespuesta(mensaje, HttpStatus.BAD_REQUEST);
		return ResponseEntity.badRequest().body(respuesta);
	}

	/**
	 * Maneja los errores que se originan al tratar de acceder a un recurso que no
	 * se encuentra registrado
	 * 
	 * @param e Excepción lanzada al intentar realizar una operación con un recurso
	 *          inexistente
	 * @return Respuesta que se le envía al cliente
	 */
	@ExceptionHandler(value = { ClienteRegistradoExcepcion.class, ClienteNoEncontradoExcepcion.class,
			CuentaNoEncontradaExcepcion.class })
	public ResponseEntity<Object> manejarExcepcionRecursoNoEncontrado(Exception e) {
		String mensaje = e.getMessage();
		LOGGER.error(mensaje, e);
		Map<String, Object> respuesta = generarEstructuraRespuesta(mensaje, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	}

	/**
	 * Maneja los errores que se originan al tratar de realizar una transacción con
	 * un valor faltante o que rompa una de las reglas de negocio.
	 * 
	 * @param e Excepción lanzada al intentar realizar una operación con un dato que
	 *          genere algún tipo de conflicto.
	 * @return Respuesta que se le envía al cliente
	 */
	@ExceptionHandler(value = { NumeroCuentaRegistradoExcepcion.class, LimiteDiarioExcepcion.class,
			CuentaNoActivaExcepcion.class, SaldoNoDisponibleExcepcion.class, IllegalArgumentException.class,
			MissingServletRequestParameterException.class })
	public ResponseEntity<Object> manejarExcepcionDatoConflictivo(Exception e) {
		String mensaje = e.getMessage();
		LOGGER.error(mensaje, e);
		Map<String, Object> respuesta = generarEstructuraRespuesta(mensaje, HttpStatus.CONFLICT);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
	}

	/**
	 * Maneja los errores genéricos que aún no han sido mapeados.
	 * 
	 * @param e Excepción lanzada al intentar realizar cualquier operación y genere un error
	 * @return Respuesta que se le envía al cliente
	 */
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> manejarExcepcionGenerica(Exception e) {
		String mensaje = "Error desconocido";
		LOGGER.error(mensaje, e);
		Map<String, Object> respuesta = generarEstructuraRespuesta(mensaje, HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.internalServerError().body(respuesta);
	}

	/**
	 * Genera una estructura estándar con campos que pueden ser de interés del
	 * cliente para señalar con más detalle el error ocurrido al procesar la
	 * petición.
	 * 
	 * @param mensaje Mensaje que se le enviará al cliente indicando el error
	 * @param estado  Estado HTTP que indica el verbo del error ocurrido durante el
	 *                procesamiento de la petición
	 * @return Estructura que se le enviará al cliente como respuesta a la petición
	 */
	private Map<String, Object> generarEstructuraRespuesta(String mensaje, HttpStatus estado) {
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("fecha", LocalDateTime.now());
		respuesta.put("codigo", estado.value());
		respuesta.put("mensaje", mensaje);
		return respuesta;
	}

}
