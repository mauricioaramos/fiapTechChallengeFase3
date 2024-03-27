package br.com.resturante.reservas.external;

import br.com.resturante.reservas.entities.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String>{
    Optional<Cliente> findByNome(String nome);

    Optional<Cliente> findByEndereco(String endereco);

    Optional<Cliente> findByTelefone(String telefone);
}
