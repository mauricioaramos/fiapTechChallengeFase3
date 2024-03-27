package br.com.resturante.reservas.external;

import br.com.resturante.reservas.entities.Restaurante;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends MongoRepository<Restaurante, String>{
}
