package br.com.resturante.reservas.usecases.service;

import br.com.resturante.reservas.dto.ReservaDto;
import br.com.resturante.reservas.entities.Cliente;
import br.com.resturante.reservas.entities.Mesa;
import br.com.resturante.reservas.entities.Reserva;
import br.com.resturante.reservas.entities.Restaurante;
import br.com.resturante.reservas.external.ClienteRepository;
import br.com.resturante.reservas.external.RestauranteRepository;
import br.com.resturante.reservas.usecases.service.impl.RestauranteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static br.com.resturante.reservas.usecases.service.impl.RestauranteServiceImpl.RESTAURANTE_NAO_ENCONTRADO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteServiceTest {

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    RestauranteService restauranteService = new RestauranteServiceImpl(mongoTemplate);

    @Mock
    RestauranteRepository restauranteRepository;

    @Mock
    ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldReturnRestauranteWhenCadastrarRestauranteIsCalledWithValidRestaurante() {
        Restaurante expectedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(expectedRestaurante);

        Restaurante result = restauranteService.cadastrarRestaurante(expectedRestaurante);

        assertEquals(expectedRestaurante, result);
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    /*@Test
    void shouldThrowExceptionWhenCadastrarRestauranteIsCalledWithNullRestaurante() {
        assertThrows(NullPointerException.class, () -> restauranteService.cadastrarRestaurante(null));
        verify(restauranteRepository, times(0)).save(any(Restaurante.class));
    }*/

    @Test
    void shouldReturnRestauranteWhenBuscarRestauranteIsCalledWithValidId() {
        Restaurante expectedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(expectedRestaurante));

        Restaurante result = restauranteService.buscarRestaurantePorId("4");

        assertEquals(expectedRestaurante, result);
        verify(restauranteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldThrowExceptionWhenBuscarRestauranteIsCalledWithInvalidId() {
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> restauranteService.buscarRestaurantePorId("InvalidId"));
        verify(restauranteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldReturnUpdatedRestauranteWhenAtualizarRestauranteIsCalledWithValidIdAndRestaurante() {
        Restaurante existingRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        Restaurante updatedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(existingRestaurante));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(updatedRestaurante);

        Restaurante result = restauranteService.atualizarRestaurante("4", updatedRestaurante);

        assertEquals(updatedRestaurante, result);
        verify(restauranteRepository, times(1)).findById(anyString());
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    @Test
    void shouldThrowExceptionWhenAtualizarRestauranteIsCalledWithInvalidId() {
        Restaurante updatedRestaurante = new Restaurante("41", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> restauranteService.atualizarRestaurante("100", updatedRestaurante));
        verify(restauranteRepository, times(1)).findById(anyString());
        verify(restauranteRepository, times(0)).save(any(Restaurante.class));
    }

    @Test
    void shouldReturnAllRestaurantesWhenListarRestaurantesPorIdIsCalled() {
        List<Restaurante> expectedRestaurantes = Arrays.asList(new Restaurante(), new Restaurante());
        when(restauranteRepository.findAll()).thenReturn(expectedRestaurantes);

        List<Restaurante> result = restauranteService.listarRestaurantes();

        assertEquals(expectedRestaurantes, result);
        verify(restauranteRepository, times(1)).findAll();
    }

    @Test
    void shouldDeleteRestauranteWhenDeletarRestauranteIsCalledWithValidId() {
        Restaurante existingRestaurante = new Restaurante("50", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(existingRestaurante));
        doNothing().when(restauranteRepository).delete(any(Restaurante.class));

        restauranteService.deletarRestaurante("50");

        verify(restauranteRepository, times(1)).findById(anyString());
        verify(restauranteRepository, times(1)).delete(any(Restaurante.class));
    }

    @Test
    void shouldThrowExceptionWhenDeletarRestauranteIsCalledWithInvalidId() {
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> restauranteService.deletarRestaurante("InvalidId"));
        verify(restauranteRepository, times(1)).findById(anyString());
        verify(restauranteRepository, times(0)).delete(any(Restaurante.class));
    }

    /*@Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorNomeIsCalledWithValidNome() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").is("ValidNome"));
        when(mongoTemplate.findOne(query, Restaurante.class)).thenReturn(expectedRestaurante);

        Restaurante result = restauranteService.buscarRestaurantePorNome("ValidNome");

        assertEquals(expectedRestaurante, result);
    }*/

    @Test
    void shouldThrowExceptionWhenBuscarRestaurantePorNomeIsCalledWithInvalidNome() {
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").is("InvalidNome"));
        when(mongoTemplate.findOne(query, Restaurante.class)).thenReturn(null);

        assertThrows(Exception.class, () -> restauranteService.buscarRestaurantePorNome("InvalidNome"));
    }

    /*@Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorEnderecoIsCalledWithValidEndereco() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        Query query = new Query();
        query.addCriteria(Criteria.where("endereco").is("ValidEndereco"));
        when(mongoTemplate.findOne(query, Restaurante.class)).thenReturn(expectedRestaurante);

        Restaurante result = restauranteService.buscarRestaurantePorEndereco("ValidEndereco");

        assertEquals(expectedRestaurante, result);
    }*/

    @Test
    void shouldThrowExceptionWhenBuscarRestaurantePorEnderecoIsCalledWithInvalidEndereco() {
        Query query = new Query();
        query.addCriteria(Criteria.where("endereco").is("InvalidEndereco"));
        when(mongoTemplate.findOne(query, Restaurante.class)).thenReturn(null);

        assertThrows(Exception.class, () -> restauranteService.buscarRestaurantePorEndereco("InvalidEndereco"));
    }

    /*@Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorTipoCozinhaIsCalledWithValidTipo() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        Query query = new Query();
        query.addCriteria(Criteria.where("tipo").is("ValidTipo"));
        when(mongoTemplate.findOne(query, Restaurante.class)).thenReturn(expectedRestaurante);

        Restaurante result = restauranteService.buscarRestaurantePorTipoCozinha("ValidTipo");

        assertEquals(expectedRestaurante, result);
    }*/

    @Test
    void shouldThrowExceptionWhenBuscarRestaurantePorTipoCozinhaIsCalledWithInvalidTipo() {
        Query query = new Query();
        query.addCriteria(Criteria.where("tipo").is("InvalidTipo"));
        when(mongoTemplate.findOne(query, Restaurante.class)).thenReturn(null);

        assertThrows(Exception.class, () -> restauranteService.buscarRestaurantePorTipoCozinha("InvalidTipo"));
    }

    @Test
    void shouldReturnReservaWhenReservarRestauranteIsCalledWithValidReservaDto() {
        Restaurante restaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        restaurante.setReservas(new ArrayList<>());
        restaurante.setQtdMesas(0);
        Cliente cliente = new Cliente();
        Mesa mesa = new Mesa();
        mesa.setDisponivel(true);
        restaurante.setMesas(Arrays.asList(mesa));
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.setNome("Teste");
        reservaDto.setEmail("email@teste.com");
        reservaDto.setTelefone("1234567890");
        LocalDate data = LocalDate.now();
        reservaDto.setData(data);
        reservaDto.setHora( LocalTime.of(10, 0));
        reservaDto.setIdRestaurante("ValidId");
        reservaDto.setClienteId("ValidClienteId");
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(restaurante));
        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));

        Reserva result = restauranteService.reservarRestaurante(reservaDto);

        assertNotNull(result);
        //assertEquals(cliente, result.getCliente());
        assertFalse(result.getMesa().isDisponivel());
        verify(restauranteRepository, times(1)).findById(anyString());
        //verify(clienteRepository, times(1)).findById(anyString());
        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    @Test
    void shouldThrowExceptionWhenReservarRestauranteIsCalledWithInvalidRestauranteId() {
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.getTelefone();
        reservaDto.getEmail();
        reservaDto.getNome();
        reservaDto.setIdRestaurante("InvalidId");
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> restauranteService.reservarRestaurante(reservaDto));
        verify(restauranteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldThrowExceptionWhenReservarRestauranteIsCalledWithInvalidClienteId() {
        Restaurante restaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        restaurante.setQtdMesas(0);
        Mesa mesa = new Mesa();
        mesa.setDisponivel(true);
        restaurante.setMesas(List.of(mesa));
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.setIdRestaurante("ValidId");
        reservaDto.setClienteId("InvalidClienteId");
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(restaurante));
        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(new Cliente()));

        //assertThrows(RuntimeException.class, () -> restauranteService.reservarRestaurante(reservaDto));
        restauranteService.reservarRestaurante(reservaDto);
        verify(restauranteRepository, times(1)).findById(anyString());
        //verify(clienteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldThrowExceptionWhenReservarRestauranteIsCalledWithNoAvailableMesa() {
        Restaurante restaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        restaurante.setQtdMesas(0);
        Mesa mesa = new Mesa();
        mesa.setDisponivel(false);
        restaurante.setMesas(List.of(mesa));
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.setIdRestaurante("ValidId");
        reservaDto.setClienteId("InvalidClienteId");
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(restaurante));
        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(new Cliente()));

        assertThrows(RuntimeException.class, () ->  restauranteService.reservarRestaurante(reservaDto));
        verify(restauranteRepository, times(1)).findById(anyString());
        //verify(clienteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldAddCommentToReservationWhenAdicionarComentarioIsCalledWithValidParameters() {
        Restaurante restaurante = new Restaurante("3", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        restaurante.setReservas(Arrays.asList(reserva));
        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        Reserva result = restauranteService.adicionarComentario(restaurante, "1", "Test Comment");

        assertNotNull(result.getComentario());
        assertEquals("Test Comment", result.getComentario().getTexto());
    }

    @Test
    void shouldThrowExceptionWhenAdicionarComentarioIsCalledWithInvalidReservationId() {
        Restaurante restaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        restaurante.setReservas(Arrays.asList(reserva));

        assertThrows(RuntimeException.class, () -> restauranteService.adicionarComentario(restaurante, "2", "Test Comment"));
    }

    @Test
    void shouldReturnReservationsWhenListarReservasPorRestauranteIsCalledWithValidId() {
        Restaurante restaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        Reserva reserva = new Reserva();
        restaurante.setReservas(Arrays.asList(reserva));
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(restaurante));

        List<Reserva> result = restauranteService.listarReservasPorRestaurante("ValidId");

        assertEquals(1, result.size());
        assertEquals(reserva, result.get(0));
    }


    @Test
    void shouldReturnUpdatedReservaWhenAtualizarReservaIsCalledWithValidIdAndReserva() {
        Restaurante existingRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        existingRestaurante.setId("1");
        Reserva existingReserva = new Reserva();
        existingReserva.setId(1L);
        existingRestaurante.setReservas(Arrays.asList(existingReserva));
        Reserva updatedReserva = new Reserva();
        updatedReserva.setId(1L);
        when(restauranteRepository.findById(any())).thenReturn(Optional.of(existingRestaurante));

        Reserva result = restauranteService.atualizarReserva("1", updatedReserva);

        assertEquals(updatedReserva, result);
        verify(restauranteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldThrowExceptionWhenAtualizarReservaIsCalledWithInvalidRestauranteId() {
        Reserva updatedReserva = new Reserva();
        when(restauranteRepository.findById(anyString())).thenThrow(new RuntimeException(RESTAURANTE_NAO_ENCONTRADO));

        assertThrows(RuntimeException.class, () -> restauranteService.atualizarReserva("InvalidId", updatedReserva));
        verify(restauranteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldThrowExceptionWhenAtualizarReservaIsCalledWithInvalidReservaId() {
        Restaurante existingRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        Reserva existingReserva = new Reserva();
        existingReserva.setId(1L);
        existingRestaurante.setReservas(Arrays.asList(existingReserva));
        Reserva updatedReserva = new Reserva();
        updatedReserva.setId(2L);
        when(restauranteRepository.findById(any())).thenReturn(Optional.of(existingRestaurante));

        assertThrows(RuntimeException.class, () -> restauranteService.atualizarReserva("1", updatedReserva));
        verify(restauranteRepository, times(1)).findById(anyString());
    }

    @Test
    void shouldDeleteReservaWhenDeletarReservaIsCalledWithValidId() {
        Restaurante existingRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        Reserva existingReserva = new Reserva();
        existingReserva.setId(1L);
        existingRestaurante.setReservas(Arrays.asList(existingReserva));
        when(restauranteRepository.findById(anyString())).thenReturn(Optional.of(existingRestaurante));

        restauranteService.deletarReserva("ValidId", "1");

        verify(restauranteRepository, times(1)).save(any(Restaurante.class));
    }

    @Test
    void shouldThrowExceptionWhenDeletarReservaIsCalledWithInvalidRestauranteId() {
        when(restauranteRepository.findById(anyString())).thenThrow(new RuntimeException(RESTAURANTE_NAO_ENCONTRADO));

        assertThrows(RuntimeException.class, () -> restauranteService.deletarReserva("InvalidId", "1"));
        verify(restauranteRepository, times(1)).findById(anyString());
    }

    /*@Test
    void shouldThrowExceptionWhenDeletarReservaIsCalledWithInvalidReservaId() {
        Restaurante existingRestaurante = new Restaurante();
        Reserva existingReserva = new Reserva();
        existingReserva.setId(1L);
        existingRestaurante.setReservas(Arrays.asList(existingReserva));
        when(restauranteService.buscarRestaurante(anyString())).thenReturn(existingRestaurante);

        assertThrows(RuntimeException.class, () -> restauranteService.deletarReserva("ValidId", "2"));
        verify(restauranteService, times(1)).buscarRestaurante(anyString());
    }*/

}
