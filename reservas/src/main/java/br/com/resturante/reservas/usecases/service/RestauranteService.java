package br.com.resturante.reservas.usecases.service;

import br.com.resturante.reservas.dto.ReservaDto;
import br.com.resturante.reservas.entities.Reserva;
import br.com.resturante.reservas.entities.Restaurante;

import java.util.List;

public interface RestauranteService {
    Restaurante cadastrarRestaurante(Restaurante restaurante);
    Restaurante buscarRestaurantePorId(String id);
    Restaurante atualizarRestaurante(String id, Restaurante restaurante);
    List<Restaurante> listarRestaurantes();
    void deletarRestaurante(String id);

    Restaurante buscarRestaurantePorNome(String nome) throws Exception;

    Restaurante buscarRestaurantePorEndereco(String endereco) throws Exception;

    Restaurante buscarRestaurantePorTipoCozinha(String tipo) throws Exception;

    Reserva reservarRestaurante(ReservaDto reservaDto);


    Reserva adicionarComentario(Restaurante restaurante, String idReserva, String textoComentario);

    List<Reserva> listarReservasPorRestaurante(String id);

    Reserva atualizarReserva(String idRestaurante, Reserva reserva);

    void deletarReserva(String idRestaurante, String idReserva);
}
