package br.com.resturante.reservas.adapter.controller;

import br.com.resturante.reservas.entities.Cliente;
import br.com.resturante.reservas.usecases.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClienteControllerTest {

    private MockMvc mockMvc;

    AutoCloseable openMocks;

    @Mock//
    ClienteService clienteService;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        ClienteController clienteController1 = new ClienteController(clienteService);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController1)
                .setControllerAdvice(new org.example.handler.GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReturnClienteWhenCadastrarClienteIsCalledWithValidInput() throws Exception {
        Cliente expectedCliente = new Cliente();
        expectedCliente.setNome("Test");
        expectedCliente.setEndereco("Test");
        expectedCliente.setTelefone("123456789");

        when(clienteService.cadastrarCliente(any(Cliente.class))).thenReturn(expectedCliente);

        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCliente)))
                .andExpect(status().isCreated());

        verify(clienteService, times(1)).cadastrarCliente(any(Cliente.class));
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldThrowExceptionWhenCadastrarClienteIsCalledWithNullInput() throws Exception {
        Cliente expectedCliente = new Cliente();
        when(clienteService.cadastrarCliente(any(Cliente.class))).thenReturn(null);

        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCliente)))
                .andExpect(status().isBadRequest());

        verify(clienteService, times(1)).cadastrarCliente(any(Cliente.class));
    }


    @Test
    void shouldReturnEmptyListWhenNoClientesExist() throws Exception {


        Cliente expectedCliente = new Cliente();
        when(clienteService.getAllClientes()).thenReturn(new ArrayList<Cliente>());

        mockMvc.perform(get("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCliente)))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).getAllClientes();

    }


    @Test
    void shouldReturnClienteWhenBuscarClientePorIdIsCalledWithValidId() throws Exception {
        Cliente expectedCliente = new Cliente();
        expectedCliente.setId("1");
        when(clienteService.listarClientePorId(anyString())).thenReturn(expectedCliente);

        mockMvc.perform(get("/cliente/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCliente)))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedCliente.getId()));

        verify(clienteService, times(1)).listarClientePorId(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarClientePorIdIsCalledWithInvalidId() throws Exception {
        when(clienteService.listarClientePorId(anyString())).thenThrow(new RuntimeException("Cliente não encontrado"));

        mockMvc.perform(get("/cliente/{id}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).listarClientePorId(anyString());
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorNomeIsCalledWithValidName() throws Exception {
        Cliente expectedCliente = new Cliente();
        expectedCliente.setNome("Test");
        expectedCliente.setEndereco("Test");
        expectedCliente.setTelefone("123456789");

        when(clienteService.buscarClientePorNome(anyString())).thenReturn(expectedCliente);

        mockMvc.perform(get("/cliente/nome/{nome}", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(expectedCliente.getNome()))
                .andExpect(jsonPath("$.endereco").value(expectedCliente.getEndereco()))
                .andExpect(jsonPath("$.telefone").value(expectedCliente.getTelefone()));
        verify(clienteService, times(1)).buscarClientePorNome(anyString());
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorEnderecoIsCalledWithValidEndereco() throws Exception {
        Cliente expectedCliente = new Cliente();
        expectedCliente.setNome("Test");
        expectedCliente.setEndereco("Test");
        expectedCliente.setTelefone("123456789");

        when(clienteService.buscarClientePorEndereco(anyString())).thenReturn(expectedCliente);

        mockMvc.perform(get("/cliente/endereco/{endereco}", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(expectedCliente.getNome()))
                .andExpect(jsonPath("$.endereco").value(expectedCliente.getEndereco()))
                .andExpect(jsonPath("$.telefone").value(expectedCliente.getTelefone()));
        verify(clienteService, times(1)).buscarClientePorEndereco(anyString());
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorTelefoneIsCalledWithValidTelefone() throws Exception {
        Cliente expectedCliente = new Cliente();
        expectedCliente.setNome("Test");
        expectedCliente.setEndereco("Test");
        expectedCliente.setTelefone("123456789");

        when(clienteService.buscarClientePorTelefone(anyString())).thenReturn(expectedCliente);

        mockMvc.perform(get("/cliente/telefone/{telefone}", "123456789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(expectedCliente.getNome()))
                .andExpect(jsonPath("$.endereco").value(expectedCliente.getEndereco()))
                .andExpect(jsonPath("$.telefone").value(expectedCliente.getTelefone()));
        verify(clienteService, times(1)).buscarClientePorTelefone(anyString());
    }


    @Test
    void shouldThrowExceptionWhenBuscarClientePorIdIsCalledWithInvalidId() throws Exception {
        when(clienteService.listarClientePorId(anyString())).thenThrow(new RuntimeException("Cliente não encontrado"));

        mockMvc.perform(get("/cliente/{id}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(clienteService, times(1)).listarClientePorId(anyString());
    }

    @Test
    void shouldThrowExceptionWhenBuscarClientePorNomeIsCalledWithInvalidName() throws Exception {
        when(clienteService.buscarClientePorNome(anyString())).thenThrow(new RuntimeException("Cliente não encontrado"));

        mockMvc.perform(get("/cliente/nome/{nome}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(clienteService, times(1)).buscarClientePorNome(anyString());
    }

    @Test
    void shouldThrowExceptionWhenBuscarClientePorEnderecoIsCalledWithInvalidEndereco() throws Exception {
        when(clienteService.buscarClientePorEndereco(anyString())).thenThrow(new RuntimeException("Cliente não encontrado"));

        mockMvc.perform(get("/cliente/endereco/{endereco}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(clienteService, times(1)).buscarClientePorEndereco(anyString());
    }

    @Test
    void shouldThrowExceptionWhenBuscarClientePorTelefoneIsCalledWithInvalidTelefone() throws Exception {
        when(clienteService.buscarClientePorTelefone(anyString())).thenThrow(new RuntimeException("Cliente não encontrado"));

        mockMvc.perform(get("/cliente/telefone/{telefone}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(clienteService, times(1)).buscarClientePorTelefone(anyString());
    }

}