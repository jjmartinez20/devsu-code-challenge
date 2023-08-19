package com.devsu.test.controlador;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.devsu.test.interfaces.ClienteServicio;
import com.devsu.test.modelo.Cliente;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ClienteControlador.class)
class ClienteControladorTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClienteServicio clienteServicio;

	private List<Cliente> mockClientes;
	
	private Cliente cliente;

	@BeforeEach
	void setUp() throws Exception {
		mockClientes = new ArrayList<>();

		cliente = new Cliente();
		cliente.setId(1);
		cliente.setNombre("José Lema");
		cliente.setGenero('M');
		cliente.setEdad(30);
		cliente.setIdentificacion("123456");
		cliente.setDireccion("Otavalo sn y principal");
		cliente.setTelefono("098254785");
		cliente.setContraseña("1234");
		cliente.setEstado(true);
		mockClientes.add(cliente);
		
	}

	@Test
	void testObtenerClientes() throws Exception {		
		when(clienteServicio.obtenerClientes()).thenReturn(mockClientes);
		mockMvc.perform(get("/api/clientes")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
	}

}
