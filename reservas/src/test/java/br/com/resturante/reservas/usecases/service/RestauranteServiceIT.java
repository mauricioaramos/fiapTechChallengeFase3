package br.com.resturante.reservas.usecases.service;

import br.com.resturante.reservas.bd.MongoDBTestContainerConfig;
import br.com.resturante.reservas.entities.Restaurante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
class RestauranteServiceIT {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    RestauranteService restauranteService;

    @Test
    void deveBuscarRestaurantePorId() {
        // Arrange
        Restaurante restauranteSaved = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        mongoTemplate.save(restauranteSaved);
        // Act
        Restaurante restaurante = restauranteService.buscarRestaurantePorId("4");
        // Assert
        assertThat(restaurante).isNotNull();
        assertThat(restaurante.getId()).isEqualTo("4");
    }

    @Test
    void deveObterUmRestaurantePorNome() throws Exception {
        // Arrange
        Restaurante restauranteSaved = new Restaurante("1", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        mongoTemplate.save(restauranteSaved);
        // Act
        Restaurante restaurante = restauranteService.buscarRestaurantePorNome("nome");
        // Assert
        assertThat(restaurante).isNotNull();
        assertThat(restaurante.getNome()).isEqualTo("nome");
    }

    @Test
    void deveRetornarRestauranteVazioQuandoBuscarPorNomeNaoExistente() throws Exception {
        // Act
        assertThatThrownBy(() -> restauranteService.buscarRestaurantePorNome("zezin"))
                .isInstanceOf(Exception.class)
                .hasMessage("Restaurante não encontrado");
    }

    @Test
    void deveRetornarRestauranteVazioQuandoBuscarPorEnderecoNaoExistente() throws Exception {
        // Act
        assertThatThrownBy(() -> restauranteService.buscarRestaurantePorEndereco("rua x"))
                .isInstanceOf(Exception.class)
                .hasMessage("Restaurante não encontrado");
    }

    @Test
    void deveRetornarRestauranteVazioQuandoBuscarPorTipoCozinhaNaoExistente() throws Exception {
        // Act
        assertThatThrownBy(() -> restauranteService.buscarRestaurantePorTipoCozinha("mexicana"))
                .isInstanceOf(Exception.class)
                .hasMessage("Restaurante não encontrado");
    }

    @Test
    void deveObterUmRestaurantePorEndereco() throws Exception {
        // Arrange
        Restaurante restauranteSaved = new Restaurante("1", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        mongoTemplate.save(restauranteSaved);
        // Act
        Restaurante restaurante = restauranteService.buscarRestaurantePorEndereco("localizacao");
        // Assert
        assertThat(restaurante).isNotNull();
        assertThat(restaurante.getLocalizacao()).isEqualTo("localizacao");
    }

    @Test
    void deveObterUmRestaurantePorTipoCozinha() throws Exception {
        // Arrange
        Restaurante restauranteSaved = new Restaurante("1", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        mongoTemplate.save(restauranteSaved);
        // Act
        Restaurante restaurante = restauranteService.buscarRestaurantePorTipoCozinha("tipoCozinha");
        // Assert
        assertThat(restaurante).isNotNull();
        assertThat(restaurante.getTipoCozinha()).isEqualTo("tipoCozinha");
    }

    @Test
    void deveAtualizarRestaurante() {
        // Arrange
        Restaurante restauranteSaved = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        mongoTemplate.save(restauranteSaved);
        Restaurante restaurante = new Restaurante("4", "nome atualizado", "localizacao atualizada", "tipoCozinha atualizada", "horarioFuncionamento atualizado", 10, 10, new ArrayList<>(), new ArrayList<>());
        // Act
        Restaurante restauranteAtualizado = restauranteService.atualizarRestaurante("4", restaurante);
        // Assert
        assertThat(restauranteAtualizado).isNotNull();
        assertThat(restauranteAtualizado.getNome()).isEqualTo("nome atualizado");
        assertThat(restauranteAtualizado.getLocalizacao()).isEqualTo("localizacao atualizada");
        assertThat(restauranteAtualizado.getTipoCozinha()).isEqualTo("tipoCozinha atualizada");
        assertThat(restauranteAtualizado.getHorarioFuncionamento()).isEqualTo("horarioFuncionamento atualizado");
    }

    @Test
    void deveDeletarRestaurante() {
        // Arrange
        Restaurante restauranteSaved = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        mongoTemplate.save(restauranteSaved);
        // Act
        restauranteService.deletarRestaurante("4");
        // Assert
        assertThat(mongoTemplate.findById("4", Restaurante.class)).isNull();
    }

    @Test
    void deveListarRestaurantes() {
        // Arrange
        Restaurante restauranteSaved = new Restaurante("4", "nome", "localizacao", "tipoCozinha", "horarioFuncionamento", 10, 10, new ArrayList<>(), new ArrayList<>());
        mongoTemplate.save(restauranteSaved);
        // Act
        var restaurantes = restauranteService.listarRestaurantes();
        // Assert
        assertThat(restaurantes).isNotNull();
        assertThat(restaurantes.size()).isGreaterThan(0);
    }

}
