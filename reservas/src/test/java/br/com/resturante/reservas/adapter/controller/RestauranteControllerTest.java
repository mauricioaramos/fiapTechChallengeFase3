package br.com.resturante.reservas.adapter.controller;

import br.com.resturante.reservas.dto.ReservaDto;
import br.com.resturante.reservas.entities.Reserva;
import br.com.resturante.reservas.entities.Restaurante;
import br.com.resturante.reservas.usecases.service.RestauranteService;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestauranteControllerTest {

    private MockMvc mockMvc;

    AutoCloseable openMocks;

    @Mock//
    RestauranteService restauranteService;

    @BeforeEach
    public void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        RestauranteController restauranteController = new RestauranteController(restauranteService);
        mockMvc = MockMvcBuilders.standaloneSetup(restauranteController)
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
    void shouldReturnCreatedRestauranteWhenCadastrarRestauranteIsCalledWithValidRestaurante() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        when(restauranteService.cadastrarRestaurante(any(Restaurante.class))).thenReturn(expectedRestaurante);

        mockMvc.perform(post("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(expectedRestaurante)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());

        verify(restauranteService, times(1)).cadastrarRestaurante(any(Restaurante.class));
    }

    @Test
    void shouldReturnBadRequestWhenCadastrarRestauranteIsCalledWithInvalidRestaurante() throws Exception {
        Restaurante invalidRestaurante = null;
        when(restauranteService.cadastrarRestaurante(any(Restaurante.class))).thenReturn(null);
        mockMvc.perform(post("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRestaurante)))
                .andExpect(status().isBadRequest());

        verify(restauranteService, times(0)).cadastrarRestaurante(any(Restaurante.class));
    }

    @Test
    void shouldReturnListOfRestaurantesWhenListarRestaurantesIsCalled() throws Exception {
        List<Restaurante> expectedRestaurantes = Arrays.asList(new Restaurante(), new Restaurante());
        when(restauranteService.listarRestaurantes()).thenReturn(expectedRestaurantes);

        mockMvc.perform(get("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(restauranteService, times(1)).listarRestaurantes();
    }

    @Test
    void shouldReturnEmptyListWhenNoRestaurantesExist() throws Exception {
        when(restauranteService.listarRestaurantes()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/restaurante")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(restauranteService, times(1)).listarRestaurantes();
    }

    @Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorIdIsCalledWithValidId() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        expectedRestaurante.setId("1");
        when(restauranteService.buscarRestaurantePorId(anyString())).thenReturn(expectedRestaurante);

        mockMvc.perform(get("/restaurante/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedRestaurante.getId()));

        verify(restauranteService, times(1)).buscarRestaurantePorId(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarRestaurantePorIdIsCalledWithInvalidId() throws Exception {
        when(restauranteService.buscarRestaurantePorId(anyString())).thenReturn(null);

        mockMvc.perform(get("/restaurante/{id}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(restauranteService, times(1)).buscarRestaurantePorId(anyString());
    }

    @Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorNomeIsCalledWithValidName() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        expectedRestaurante.setNome("ValidName");
        when(restauranteService.buscarRestaurantePorNome(anyString())).thenReturn(expectedRestaurante);

        mockMvc.perform(get("/restaurante/nome/{nome}", "ValidName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(expectedRestaurante.getNome()));

        verify(restauranteService, times(1)).buscarRestaurantePorNome(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarRestaurantePorNomeIsCalledWithInvalidName() throws Exception {
        when(restauranteService.buscarRestaurantePorNome(anyString())).thenReturn(null);

        mockMvc.perform(get("/restaurante/nome/{nome}", "InvalidName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(restauranteService, times(1)).buscarRestaurantePorNome(anyString());
    }

    @Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorEnderecoIsCalledWithValidEndereco() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        expectedRestaurante.setLocalizacao("ValidEndereco");
        when(restauranteService.buscarRestaurantePorEndereco(anyString())).thenReturn(expectedRestaurante);

        mockMvc.perform(get("/restaurante/endereco/{endereco}", "ValidEndereco")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(expectedRestaurante)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.localizacao").value(expectedRestaurante.getLocalizacao()));

        verify(restauranteService, times(1)).buscarRestaurantePorEndereco(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarRestaurantePorEnderecoIsCalledWithInvalidEndereco() throws Exception {
        when(restauranteService.buscarRestaurantePorEndereco(anyString())).thenReturn(null);

        mockMvc.perform(get("/restaurante/endereco/{endereco}", "InvalidEndereco")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(restauranteService, times(1)).buscarRestaurantePorEndereco(anyString());
    }

    @Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorTipoCozinhaIsCalledWithValidTipo() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        expectedRestaurante.setTipoCozinha("ValidTipo");
        when(restauranteService.buscarRestaurantePorTipoCozinha(anyString())).thenReturn(expectedRestaurante);

        mockMvc.perform(get("/restaurante/tipo/{tipo}", "ValidTipo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoCozinha").value(expectedRestaurante.getTipoCozinha()));

        verify(restauranteService, times(1)).buscarRestaurantePorTipoCozinha(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarRestaurantePorTipoCozinhaIsCalledWithInvalidTipo() throws Exception {
        when(restauranteService.buscarRestaurantePorTipoCozinha(anyString())).thenReturn(null);

        mockMvc.perform(get("/restaurante/tipo/{tipo}", "InvalidTipo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(restauranteService, times(1)).buscarRestaurantePorTipoCozinha(anyString());
    }

    @Test
    void shouldReturnUpdatedRestauranteWhenAtualizarRestauranteIsCalledWithValidIdAndRestaurante() throws Exception {
        Restaurante expectedRestaurante = new Restaurante();
        expectedRestaurante.setId("1");
        when(restauranteService.atualizarRestaurante(anyString(), any(Restaurante.class))).thenReturn(expectedRestaurante);

        mockMvc.perform(put("/restaurante/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(expectedRestaurante)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedRestaurante.getId()));

        verify(restauranteService, times(1)).atualizarRestaurante(anyString(), any(Restaurante.class));
    }

    @Test
    void shouldReturnNotFoundWhenAtualizarRestauranteIsCalledWithInvalidId() throws Exception {
        when(restauranteService.atualizarRestaurante(anyString(), any(Restaurante.class))).thenReturn(null);

        mockMvc.perform(put("/restaurante/{id}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Restaurante())))
                .andExpect(status().isNotFound());

        verify(restauranteService, times(1)).atualizarRestaurante(anyString(), any(Restaurante.class));
    }

    @Test
    void shouldReturnNoContentWhenDeletarRestauranteIsCalledWithValidId() throws Exception {
        doNothing().when(restauranteService).deletarRestaurante(anyString());

        mockMvc.perform(delete("/restaurante/{id}", "validId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(restauranteService, times(1)).deletarRestaurante(anyString());
    }

    @Test
    void shouldReturnCreatedReservaWhenReservarRestauranteIsCalledWithValidReservaDto() throws Exception {
        ReservaDto reservaDto = new ReservaDto();
        Reserva expectedReserva = new Reserva();
        when(restauranteService.reservarRestaurante(any(ReservaDto.class))).thenReturn(expectedReserva);

        mockMvc.perform(post("/restaurante/reservar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservaDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists());

        verify(restauranteService, times(1)).reservarRestaurante(any(ReservaDto.class));
    }

    @Test
    void shouldReturnBadRequestWhenReservarRestauranteIsCalledWithInvalidReservaDto() throws Exception {
        ReservaDto invalidReservaDto = null;
        when(restauranteService.reservarRestaurante(any(ReservaDto.class))).thenReturn(null);

        mockMvc.perform(post("/restaurante/reservar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidReservaDto)))
                .andExpect(status().isBadRequest());

        verify(restauranteService, times(0)).reservarRestaurante(any(ReservaDto.class));
    }

    @Test
    void shouldReturnListOfReservasWhenListarReservasPorRestauranteIsCalledWithValidId() throws Exception {
        List<Reserva> expectedReservas = Arrays.asList(new Reserva(), new Reserva());
        when(restauranteService.listarReservasPorRestaurante(anyString())).thenReturn(expectedReservas);

        mockMvc.perform(get("/restaurante/reservas/{id}", "validId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(restauranteService, times(1)).listarReservasPorRestaurante(anyString());
    }

    @Test
    void shouldReturnEmptyListWhenListarReservasPorRestauranteIsCalledAndNoReservasExist() throws Exception {
        when(restauranteService.listarReservasPorRestaurante(anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/restaurante/reservas/{id}", "validId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(restauranteService, times(1)).listarReservasPorRestaurante(anyString());
    }









    /*@Test
    void shouldReturnCreatedRestauranteWhenCadastrarRestauranteIsCalledWithValidRestaurante() {
        Restaurante expectedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteService.cadastrarRestaurante(any(Restaurante.class))).thenReturn(expectedRestaurante);

        ResponseEntity<Restaurante> responseEntity = restauranteController.cadastrarRestaurante(new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>()));

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedRestaurante, responseEntity.getBody());
        verify(restauranteService, times(1)).cadastrarRestaurante(any(Restaurante.class));
    }

    *//*@Test
    void shouldThrowExceptionWhenCadastrarRestauranteIsCalledWithInvalidRestaurante() {
        when(restauranteService.cadastrarRestaurante(any(Restaurante.class))).thenThrow(new RestauranteNotFoundException());

        assertThrows(RestauranteNotFoundException.class, () -> restauranteController.cadastrarRestaurante(new Restaurante()));
        verify(restauranteService, times(1)).cadastrarRestaurante(any(Restaurante.class));
    }*//*


    @Test
    void shouldReturnListOfRestaurantesWhenListarRestaurantesIsCalled() {
        List<Restaurante> expectedRestaurantes = Arrays.asList(new Restaurante(), new Restaurante());
        when(restauranteService.listarRestaurantes()).thenReturn(expectedRestaurantes);

        ResponseEntity<List<Restaurante>> responseEntity = restauranteController.listarRestaurantes();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRestaurantes, responseEntity.getBody());
        verify(restauranteService, times(1)).listarRestaurantes();
    }

    @Test
    void shouldReturnEmptyListWhenListarRestaurantesIsCalledAndNoRestaurantesExist() {
        when(restauranteService.listarRestaurantes()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Restaurante>> responseEntity = restauranteController.listarRestaurantes();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(restauranteService, times(1)).listarRestaurantes();
    }

    @Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorIdIsCalledWithValidId() {
        Restaurante expectedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteService.buscarRestaurantePorId(anyString())).thenReturn(expectedRestaurante);

        ResponseEntity<Restaurante> responseEntity = restauranteController.buscarRestaurantePorId("ValidId");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRestaurante, responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorId(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarRestaurantePorIdIsCalledWithInvalidId() {
        when(restauranteService.buscarRestaurantePorId(anyString())).thenReturn(null);

        ResponseEntity<Restaurante> responseEntity = restauranteController.buscarRestaurantePorId("InvalidId");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorId(anyString());
    }

    @Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorNomeIsCalledWithValidName() throws Exception {
        Restaurante expectedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteService.buscarRestaurantePorNome(anyString())).thenReturn(expectedRestaurante);

        ResponseEntity<Restaurante> responseEntity = restauranteController.buscarRestaurantePorNome("ValidName");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRestaurante, responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorNome(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarRestaurantePorNomeIsCalledWithInvalidName() throws Exception {
        when(restauranteService.buscarRestaurantePorNome(anyString())).thenReturn(null);

        ResponseEntity<Restaurante> responseEntity = restauranteController.buscarRestaurantePorNome("InvalidName");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorNome(anyString());
    }

    @Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorEnderecoIsCalledWithValidEndereco() throws Exception {
        Restaurante expectedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteService.buscarRestaurantePorEndereco(anyString())).thenReturn(expectedRestaurante);

        ResponseEntity<Restaurante> responseEntity = restauranteController.buscarRestaurantePorEndereco("ValidEndereco");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRestaurante, responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorEndereco(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarRestaurantePorEnderecoIsCalledWithInvalidEndereco() throws Exception {
        when(restauranteService.buscarRestaurantePorEndereco(anyString())).thenReturn(null);

        ResponseEntity<Restaurante> responseEntity = restauranteController.buscarRestaurantePorEndereco("InvalidEndereco");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorEndereco(anyString());
    }

    @Test
    void shouldReturnRestauranteWhenBuscarRestaurantePorTipoCozinhaIsCalledWithValidTipo() throws Exception {
        Restaurante expectedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteService.buscarRestaurantePorTipoCozinha(anyString())).thenReturn(expectedRestaurante);

        ResponseEntity<Restaurante> responseEntity = restauranteController.buscarRestaurantePorTipoCozinha("ValidTipo");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRestaurante, responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorTipoCozinha(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenBuscarRestaurantePorTipoCozinhaIsCalledWithInvalidTipo() throws Exception {
        when(restauranteService.buscarRestaurantePorTipoCozinha(anyString())).thenReturn(null);

        ResponseEntity<Restaurante> responseEntity = restauranteController.buscarRestaurantePorTipoCozinha("InvalidTipo");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorTipoCozinha(anyString());
    }

    @Test
    void shouldReturnUpdatedRestauranteWhenAtualizarRestauranteIsCalledWithValidIdAndRestaurante() {
        Restaurante expectedRestaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteService.atualizarRestaurante(anyString(), any(Restaurante.class))).thenReturn(expectedRestaurante);

        ResponseEntity<Restaurante> responseEntity = restauranteController.atualizarRestaurante("4", new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>()));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedRestaurante, responseEntity.getBody());
        verify(restauranteService, times(1)).atualizarRestaurante(anyString(), any(Restaurante.class));
    }

    @Test
    void shouldReturnNotFoundWhenAtualizarRestauranteIsCalledWithInvalidId() {
        when(restauranteService.atualizarRestaurante(anyString(), any(Restaurante.class))).thenReturn(null);

        ResponseEntity<Restaurante> responseEntity = restauranteController.atualizarRestaurante("InvalidId", new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>()));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(restauranteService, times(1)).atualizarRestaurante(anyString(), any(Restaurante.class));
    }

    @Test
    void shouldReturnNoContentWhenDeletarRestauranteIsCalledWithValidId() {
        doNothing().when(restauranteService).deletarRestaurante(anyString());

        ResponseEntity<Void> responseEntity = restauranteController.deletarRestaurante("ValidId");

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(restauranteService, times(1)).deletarRestaurante(anyString());
    }

    @Test
    void shouldThrowExceptionWhenDeletarRestauranteIsCalledWithInvalidId() {
        doThrow(new RuntimeException()).when(restauranteService).deletarRestaurante(anyString());

        assertThrows(RuntimeException.class, () -> restauranteController.deletarRestaurante("InvalidId"));
        verify(restauranteService, times(1)).deletarRestaurante(anyString());
    }

    @Test
    void shouldReturnCreatedReservaWhenReservarRestauranteIsCalledWithValidReservaDto() {
        ReservaDto reservaDto = new ReservaDto();
        Reserva expectedReserva = new Reserva();
        when(restauranteService.reservarRestaurante(any(ReservaDto.class))).thenReturn(expectedReserva);

        ResponseEntity<Reserva> responseEntity = restauranteController.reservarRestaurante(reservaDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedReserva, responseEntity.getBody());
        verify(restauranteService, times(1)).reservarRestaurante(any(ReservaDto.class));
    }

    @Test
    void shouldThrowExceptionWhenReservarRestauranteIsCalledWithInvalidReservaDto() {
        ReservaDto reservaDto = new ReservaDto();
        when(restauranteService.reservarRestaurante(any(ReservaDto.class))).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> restauranteController.reservarRestaurante(reservaDto));
        verify(restauranteService, times(1)).reservarRestaurante(any(ReservaDto.class));
    }

    @Test
    void shouldReturnUpdatedReservaWhenAdicionarComentarioIsCalledWithValidIdAndComentario() {
        Restaurante restaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        Reserva expectedReserva = new Reserva();
        when(restauranteService.buscarRestaurantePorId(anyString())).thenReturn(restaurante);
        when(restauranteService.adicionarComentario(any(Restaurante.class), anyString(), anyString())).thenReturn(expectedReserva);

        ResponseEntity<Reserva> responseEntity = restauranteController.adicionarComentario("ValidIdRestaurante", "ValidIdReserva", "ValidComentario");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedReserva, responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorId(anyString());
        verify(restauranteService, times(1)).adicionarComentario(any(Restaurante.class), anyString(), anyString());
    }

    @Test
    void shouldReturnNotFoundWhenAdicionarComentarioIsCalledWithInvalidIdRestaurante() {
        when(restauranteService.buscarRestaurantePorId(anyString())).thenReturn(null);

        ResponseEntity<Reserva> responseEntity = restauranteController.adicionarComentario("InvalidIdRestaurante", "ValidIdReserva", "ValidComentario");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorId(anyString());
    }

    @Test
    void shouldReturnNotFoundWhenAdicionarComentarioIsCalledWithInvalidReserva() {
        Restaurante restaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        when(restauranteService.buscarRestaurantePorId(anyString())).thenReturn(restaurante);
        when(restauranteService.adicionarComentario(any(Restaurante.class), anyString(), anyString())).thenReturn(null);

        ResponseEntity<Reserva> responseEntity = restauranteController.adicionarComentario("ValidIdRestaurante", "InvalidIdReserva", "ValidComentario");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(restauranteService, times(1)).buscarRestaurantePorId(anyString());
        verify(restauranteService, times(1)).adicionarComentario(any(Restaurante.class), anyString(), anyString());
    }

    @Test
    void shouldReturnNoContentWhenDeletarReservaIsCalledWithValidIdRestauranteAndIdReserva() {
        doNothing().when(restauranteService).deletarReserva(anyString(), anyString());

        ResponseEntity<Void> responseEntity = restauranteController.deletarReserva("ValidIdRestaurante", "ValidIdReserva");

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(restauranteService, times(1)).deletarReserva(anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenDeletarReservaIsCalledWithInvalidIdRestaurante() {
        doThrow(new RuntimeException()).when(restauranteService).deletarReserva(anyString(), anyString());

        assertThrows(RuntimeException.class, () -> restauranteController.deletarReserva("InvalidIdRestaurante", "ValidIdReserva"));
        verify(restauranteService, times(1)).deletarReserva(anyString(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenDeletarReservaIsCalledWithInvalidIdReserva() {
        doThrow(new RuntimeException()).when(restauranteService).deletarReserva(anyString(), anyString());

        assertThrows(RuntimeException.class, () -> restauranteController.deletarReserva("ValidIdRestaurante", "InvalidIdReserva"));
        verify(restauranteService, times(1)).deletarReserva(anyString(), anyString());
    }

    @Test
    void shouldReturnUpdatedReservaWhenAtualizarReservaIsCalledWithValidIdRestauranteAndReserva() {
        Restaurante restaurante = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        Reserva expectedReserva = new Reserva();
        when(restauranteService.atualizarReserva(anyString(), any(Reserva.class))).thenReturn(expectedReserva);

        ResponseEntity<Reserva> responseEntity = restauranteController.atualizarReserva("ValidIdRestaurante", new Reserva());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedReserva, responseEntity.getBody());
        verify(restauranteService, times(1)).atualizarReserva(anyString(), any(Reserva.class));
    }

    @Test
    void shouldReturnNotFoundWhenAtualizarReservaIsCalledWithInvalidIdRestaurante() {
        when(restauranteService.atualizarReserva(anyString(), any(Reserva.class))).thenReturn(null);

        ResponseEntity<Reserva> responseEntity = restauranteController.atualizarReserva("InvalidIdRestaurante", new Reserva());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(restauranteService, times(1)).atualizarReserva(anyString(), any(Reserva.class));
    }

    @Test
    void shouldReturnListOfReservasWhenListarReservasPorRestauranteIsCalledWithValidId() {
        List<Reserva> expectedReservas = Arrays.asList(new Reserva(), new Reserva());
        when(restauranteService.listarReservasPorRestaurante(anyString())).thenReturn(expectedReservas);

        ResponseEntity<List<Reserva>> responseEntity = restauranteController.listarReservasPorRestaurante("ValidId");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedReservas, responseEntity.getBody());
        verify(restauranteService, times(1)).listarReservasPorRestaurante(anyString());
    }

    @Test
    void shouldReturnEmptyListWhenListarReservasPorRestauranteIsCalledAndNoReservasExist() {
        when(restauranteService.listarReservasPorRestaurante(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<Reserva>> responseEntity = restauranteController.listarReservasPorRestaurante("ValidId");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isEmpty());
        verify(restauranteService, times(1)).listarReservasPorRestaurante(anyString());
    }*/

}
