package br.com.resturante.reservas.external;

import br.com.resturante.reservas.bd.MongoDBTestContainerConfig;
import br.com.resturante.reservas.entities.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
class ClienteRepositoryIT {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ClienteRepository clienteRepository;


    @Test
    void deveObterUmClientePorNome() throws Exception {

        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        //Optional<Cliente> cliente = Optional.ofNullable(clienteService.buscarClientePorNome("nome"));
        Optional<Cliente> cliente = clienteRepository.findByNome("nome");
        assertTrue(cliente.isPresent());
        assertThat(cliente.get().getNome()).isEqualTo("nome");
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorNomeNaoExistente(){
        Optional<Cliente> cliente = clienteRepository.findByNome("zezin");
        assertTrue(cliente.isEmpty());
    }

    @Test
    void deveObterUmClientePorEndereco() {

        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        Optional<Cliente> cliente = clienteRepository.findByEndereco("rua x");
        assertTrue(cliente.isPresent());
        assertThat(cliente.get().getEndereco()).isEqualTo("rua x");
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorEnderecoNaoExistente(){
        Optional<Cliente> cliente = clienteRepository.findByEndereco("rua y");
        assertTrue(cliente.isEmpty());
    }

    @Test
    void deveObterUmClientePorTelefone() {

        Cliente clienteSaved = new Cliente("1","nome", "email", "12455656", "rua x");
        mongoTemplate.save(clienteSaved);
        Optional<Cliente> cliente = clienteRepository.findByTelefone("12455656");
        assertTrue(cliente.isPresent());
        assertThat(cliente.get().getTelefone()).isEqualTo("12455656");
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorTelefoneNaoExistente() {
        Optional<Cliente> cliente = clienteRepository.findByTelefone("123456");
        assertTrue(cliente.isEmpty());
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorTelefoneNulo() {
        Optional<Cliente> cliente = clienteRepository.findByTelefone(null);
        assertTrue(cliente.isEmpty());
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorEnderecoNulo() {
        Optional<Cliente> cliente = clienteRepository.findByEndereco(null);
        assertTrue(cliente.isEmpty());
    }

    @Test
    void deveRetornarClienteVazioQuandoBuscarPorNomeNulo() {
        Optional<Cliente> cliente = clienteRepository.findByNome(null);
        assertTrue(cliente.isEmpty());
    }

}
