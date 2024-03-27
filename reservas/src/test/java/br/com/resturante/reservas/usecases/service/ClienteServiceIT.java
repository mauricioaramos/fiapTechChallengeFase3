package br.com.resturante.reservas.usecases.service;

import br.com.resturante.reservas.bd.MongoDBTestContainerConfig;
import br.com.resturante.reservas.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
class ClienteServiceIT {


    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ClienteService clienteService;

    @Test
    void daveBuscarClientePorId() throws Exception {
        // Arrange
        Cliente clienteSaved = new Cliente("4","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        // Act
        Optional<Cliente> cliente = Optional.ofNullable(clienteService.listarClientePorId("4"));
        //Cliente cliente = clienteService.listarClientePorId("4");
        // Assert
        assertThat(cliente).isNotNull();
        assertThat(cliente.get().getId()).isEqualTo("4");
    }

    @Test
    void deveObterUmClientePorNome() throws Exception {
        // Arrange
        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        // Act
        Optional<Cliente> cliente = Optional.ofNullable(clienteService.buscarClientePorNome("nome"));
        // Assert
        assertTrue(cliente.isPresent());
        assertThat(cliente.get().getNome()).isEqualTo("nome");
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorNomeNaoExistente() throws Exception {
        // Act
        assertThrows(Exception.class, () -> clienteService.buscarClientePorNome("zezin"));
    }

    @Test
    void deveObterUmClientePorEndereco() throws Exception {
        // Arrange
        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        // Act
        Optional<Cliente> cliente = Optional.ofNullable(clienteService.buscarClientePorEndereco("rua x"));
        // Assert
        assertTrue(cliente.isPresent());
        assertThat(cliente.get().getEndereco()).isEqualTo("rua x");
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorEnderecoNaoExistente() throws Exception {
        // Act
        assertThrows(Exception.class, () -> clienteService.buscarClientePorEndereco("rua y"));
    }

    @Test
    void deveObterUmClientePorTelefone() throws Exception {
        // Arrange
        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        // Act
        Optional<Cliente> cliente = Optional.ofNullable(clienteService.buscarClientePorTelefone("12455656"));
        // Assert
        assertTrue(cliente.isPresent());
        assertThat(cliente.get().getTelefone()).isEqualTo("12455656");
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorTelefoneNaoExistente() throws Exception {
        // Act
        assertThrows(Exception.class, () -> clienteService.buscarClientePorTelefone("123456"));
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorTelefoneNulo() throws Exception {
        assertThrows(Exception.class, () -> clienteService.buscarClientePorTelefone(null));
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorEnderecoNulo() throws Exception {
        // Act
        assertThrows(Exception.class, () -> clienteService.buscarClientePorEndereco(null));
    }

    @Test
    void deveObterUmClientePorId() {

        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);

        assertThrows(Exception.class, () -> clienteService.listarClientePorId("11"));
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorIdNaoExistente() {

        assertThrows(Exception.class, () -> clienteService.listarClientePorId("10"));


    }

    @Test
    void deveCadastrarUmCliente() {
        // Arrange
        Cliente cliente = new Cliente("1","nome", "email", "12455656", "rua x");
        // Act
        Cliente clienteSalvo = clienteService.cadastrarCliente(cliente);
        // Assert
        assertThat(clienteSalvo).isNotNull();
        assertThat(clienteSalvo.getId()).isEqualTo("1");
    }

    @Test
    void deveAtualizarUmCliente() {
        // Arrange
        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        Cliente cliente = new Cliente("1","nome", "email", "12455656", "rua y");
        // Act
        Cliente clienteAtualizado = clienteService.atualizarCliente("1", cliente);
        // Assert
        assertThat(clienteAtualizado).isNotNull();
        assertThat(clienteAtualizado.getEndereco()).isEqualTo("rua y");
    }

    @Test
    void deveDeletarUmCliente() {
        // Arrange
        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        // Act
        clienteService.deletarCliente("1");
        // Assert
        assertThrows(Exception.class, () -> clienteService.listarClientePorId("1"));
    }

    @Test
    void deveListarTodosOsClientes() {
        // Arrange
        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        // Act
        clienteService.getAllClientes();
        // Assert
        assertThat(clienteService.getAllClientes()).isNotEmpty();
    }


    @Test
    void deveRetornarClienteVazioQuandoBuscarClientePorIdNaoExistente() {
        assertThrows(Exception.class, () -> clienteService.listarClientePorId("21"));
    }



}
