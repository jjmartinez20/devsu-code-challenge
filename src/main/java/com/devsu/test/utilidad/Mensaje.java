package com.devsu.test.utilidad;

public class Mensaje {

	public static final String ID_CLIENTE_NO_ENCONTRADO = "No se ha encontrado el cliente con id %d";

	public static final String IDENTIFICACION_CLIENTE_YA_REGISTRADA = "El cliente con número de identificación %s ya está registrado";

	/**
	 * Formato con el mensaje de la excepción de número de cuenta registrado
	 */
	public static final String NUMERO_CUENTA_REGISTRADO = "El número de cuenta %s ya se encuentra registrado";

	/**
	 * Formato con el mensaje de la excepción de número de cuenta registrado
	 */
	public static final String NUMERO_CUENTA_NO_ENCONTRADO = "No se ha encontrado el número de cuenta %s";

	public static final String MENSAJE_ERROR_VALOR = "El valor del movimiento debe ser distinto de cero";

	public static final String LIMITE_DIARIO_ALCANZADO = "Ya ha alcanzado el límite diario permitido ($%.2f)";

	public static final String LIMITE_DIARIO_EXCEDIDO = "No se puede realizar el retiro porque superaría por %.2f el límite diario permitido ($%.2f)";

	private Mensaje() {

	}

}
