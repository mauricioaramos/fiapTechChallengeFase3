package br.com.resturante.reservas.usecases.service;

import br.com.resturante.reservas.entities.Cliente;
import br.com.resturante.reservas.external.ClienteRepository;
import br.com.resturante.reservas.usecases.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @InjectMocks
    ClienteService clienteService = new ClienteServiceImpl();

    @Mock
    ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnSavedClienteWhenCadastrarClienteIsCalledWithValidCliente() {
        Cliente cliente = new Cliente();
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.save(any())).thenReturn(expectedCliente);

        Cliente result = clienteService.cadastrarCliente(cliente);

        assertEquals(expectedCliente, result);
        verify(clienteRepository, times(1)).save(any());
    }

    /*@Test
    void shouldThrowExceptionWhenCadastrarClienteIsCalledWithNullCliente() {
        assertThrows(NullPointerException.class, () -> clienteService.cadastrarCliente(null));
        verify(clienteRepository, times(0)).save(any(Cliente.class));
    }*/

    @Test
    void shouldReturnClienteWhenBuscarClienteIsCalledWithValidId() throws Exception {
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(expectedCliente));

        Cliente result = clienteService.listarClientePorId("ValidId");

        assertEquals(expectedCliente, result);
        verify(clienteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldThrowExceptionWhenBuscarClienteIsCalledWithInvalidId() {
        when(clienteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> clienteService.listarClientePorId("InvalidId"));
        verify(clienteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldReturnUpdatedClienteWhenAtualizarClienteIsCalledWithValidIdAndCliente() {
        Cliente cliente = new Cliente();
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(expectedCliente);

        Cliente result = clienteService.atualizarCliente("ValidId", cliente);

        assertEquals(expectedCliente, result);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    /*@Test
    void shouldThrowExceptionWhenAtualizarClienteIsCalledWithNullCliente() {
        assertThrows(NullPointerException.class, () -> clienteService.atualizarCliente("ValidId", null));
        verify(clienteRepository, times(0)).save(any(Cliente.class));
    }*/

    @Test
    void shouldReturnUpdatedClienteWhenAtualizarClienteIsCalledWithNullId() {
        Cliente cliente = new Cliente();
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(expectedCliente);

        Cliente result = clienteService.atualizarCliente(null, cliente);

        assertEquals(expectedCliente, result);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void shouldReturnClienteWhenListarClientePorIdIsCalledWithValidId() throws Exception {
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(expectedCliente));

        Cliente result = clienteService.listarClientePorId("ValidId");

        assertEquals(expectedCliente, result);
        verify(clienteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldThrowExceptionWhenListarClientePorIdIsCalledWithInvalidId() {
        when(clienteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> clienteService.listarClientePorId("InvalidId"));
        verify(clienteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldNotThrowExceptionWhenDeletarClienteIsCalledWithValidId() {
        doNothing().when(clienteRepository).deleteById(anyString());

        assertDoesNotThrow(() -> clienteService.deletarCliente("ValidId"));
        verify(clienteRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldThrowExceptionWhenDeletarClienteIsCalledWithInvalidId() {
        doThrow(new EmptyResultDataAccessException(1)).when(clienteRepository).deleteById(anyString());

        assertThrows(RuntimeException.class, () -> clienteService.deletarCliente("InvalidId"));
        verify(clienteRepository, times(1)).deleteById(anyString());
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorNomeIsCalledWithValidNome() throws Exception {
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.findByNome(anyString())).thenReturn(Optional.of(expectedCliente));

        Cliente result = clienteService.buscarClientePorNome("ValidNome");

        assertEquals(expectedCliente, result);
        verify(clienteRepository, times(1)).findByNome(anyString());
    }

    @Test
    void shouldThrowExceptionWhenBuscarClientePorNomeIsCalledWithInvalidNome() throws Exception {
        when(clienteRepository.findByNome(anyString())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> clienteService.buscarClientePorNome("InvalidNome"));
        verify(clienteRepository, times(1)).findByNome(anyString());
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorEnderecoIsCalledWithValidEndereco() throws Exception {
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.findByEndereco(anyString())).thenReturn(Optional.of(expectedCliente));

        Cliente result = clienteService.buscarClientePorEndereco("ValidEndereco");

        assertEquals(expectedCliente, result);
        verify(clienteRepository, times(1)).findByEndereco(anyString());
    }

    @Test
    void shouldThrowExceptionWhenBuscarClientePorEnderecoIsCalledWithInvalidEndereco() throws Exception {
        when(clienteRepository.findByEndereco(anyString())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> clienteService.buscarClientePorEndereco("InvalidEndereco"));
        verify(clienteRepository, times(1)).findByEndereco(anyString());
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorTelefoneIsCalledWithValidTelefone() throws Exception {
        Cliente expectedCliente = new Cliente();
        when(clienteRepository.findByTelefone(anyString())).thenReturn(Optional.of(expectedCliente));

        Cliente result = clienteService.buscarClientePorTelefone("ValidTelefone");

        assertEquals(expectedCliente, result);
        verify(clienteRepository, times(1)).findByTelefone(anyString());
    }

    @Test
    void shouldThrowExceptionWhenBuscarClientePorTelefoneIsCalledWithInvalidTelefone() throws Exception {
        when(clienteRepository.findByTelefone(anyString())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> clienteService.buscarClientePorTelefone("InvalidTelefone"));
        verify(clienteRepository, times(1)).findByTelefone(anyString());
    }

    @Test
    void shouldReturnAllClientesWhenGetAllClientesIsCalled() {
        List<Cliente> expectedClientes = Arrays.asList(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(expectedClientes);

        List<Cliente> result = clienteService.getAllClientes();

        assertEquals(expectedClientes, result);
        verify(clienteRepository, times(1)).findAll();
    }
}
