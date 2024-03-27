package br.com.resturante.reservas.usecases.service.impl;

import br.com.resturante.reservas.dto.ReservaDto;
import br.com.resturante.reservas.entities.*;
import br.com.resturante.reservas.external.ClienteRepository;
import br.com.resturante.reservas.external.RestauranteRepository;
import br.com.resturante.reservas.usecases.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestauranteServiceImpl implements RestauranteService{

    public static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante não encontrado";
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private final MongoTemplate mongoTemplate;

    public RestauranteServiceImpl( MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Restaurante cadastrarRestaurante(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @Override
    public Restaurante buscarRestaurantePorId(String id) {
        return restauranteRepository.findById(id).orElseThrow(() -> new RuntimeException(RESTAURANTE_NAO_ENCONTRADO));
    }

    @Override
    public Restaurante atualizarRestaurante(String id, Restaurante restaurante) {
        Restaurante restauranteSalvo = buscarRestaurantePorId(id);
        restaurante.setId(restauranteSalvo.getId());
        return restauranteRepository.save(restaurante);
    }



    @Override
    public List<Restaurante> listarRestaurantes() {
        return restauranteRepository.findAll();
    }

    @Override
    public void deletarRestaurante(String id) {
        Restaurante restaurante = buscarRestaurantePorId(id);
        restauranteRepository.delete(restaurante);
    }

    @Override
    public Restaurante buscarRestaurantePorNome(String nome) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").is(nome));
        Restaurante restaurante = mongoTemplate.findOne(query, Restaurante.class);
        if(restaurante == null){
            throw new Exception(RESTAURANTE_NAO_ENCONTRADO);
        }
        return restaurante;
    }

    @Override
    public Restaurante buscarRestaurantePorEndereco(String endereco) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("localizacao").is(endereco));
        Restaurante restaurante = mongoTemplate.findOne(query, Restaurante.class);
        if(restaurante == null){
            throw new Exception(RESTAURANTE_NAO_ENCONTRADO);
        }
        return restaurante;
    }

    @Override
    public Restaurante buscarRestaurantePorTipoCozinha(String tipo) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("tipoCozinha").is(tipo));
        Restaurante restaurante = mongoTemplate.findOne(query, Restaurante.class);
        if(restaurante == null){
            throw new Exception(RESTAURANTE_NAO_ENCONTRADO);
        }
        return restaurante;
    }

    @Override
    public Reserva reservarRestaurante(ReservaDto reservaDto) {
        Restaurante restaurante = restauranteRepository.findById(reservaDto.getIdRestaurante()).orElseThrow(() -> new RuntimeException(RESTAURANTE_NAO_ENCONTRADO));
        if(isIndisponivel(restaurante, reservaDto.getData(), reservaDto.getHora())){
            throw new RuntimeException("Restaurante sem mesas disponíveis");
        }

        Reserva reserva = new Reserva();
        Cliente cliente = new Cliente();//clienteRepository.findById(reservaDto.getClienteId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        reserva.setCliente(cliente);
        reserva.setData(reservaDto.getData());
        reserva.setHora(reservaDto.getHora());
        Mesa mesa = restaurante.getMesas().stream().filter(Mesa::isDisponivel).findFirst().orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
        reserva.setMesa(mesa);
        mesa.setDisponivel(false);
        restaurante.getReservas().add(reserva);
        restauranteRepository.save(restaurante);
        return reserva;
    }

    @Override
    public Reserva adicionarComentario(Restaurante restaurante, String idReserva, String textoComentario) {
        Reserva reserva = restaurante.getReservas().stream().filter(r -> r.getId().equals(Long.valueOf(idReserva))).findFirst().orElseThrow(() -> new RuntimeException(RESTAURANTE_NAO_ENCONTRADO));
        Comentario comentario = new Comentario();
        comentario.setTexto(textoComentario);
        reserva.setComentario(comentario);
        restauranteRepository.save(restaurante);
        return reserva;
    }

    @Override
    public List<Reserva> listarReservasPorRestaurante(String id) {
        Restaurante restaurante = buscarRestaurantePorId(id);
        return restaurante.getReservas();
    }

    @Override
    public Reserva atualizarReserva(String idRestaurante, Reserva reserva) {
        Restaurante restaurante = buscarRestaurantePorId(idRestaurante);
        Reserva reservaSalva = restaurante.getReservas().stream().filter(r -> r.getId().equals(reserva.getId())).findFirst().orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        reservaSalva.setComentario(reserva.getComentario());
        reservaSalva.setCliente(reserva.getCliente());
        reservaSalva.setData(reserva.getData());
        reservaSalva.setHora(reserva.getHora());
        reservaSalva.setMesa(reserva.getMesa());
        return reservaSalva;
    }

    @Override
    public void deletarReserva(String idRestaurante, String idReserva) {
        Restaurante restaurante = buscarRestaurantePorId(idRestaurante);
        Reserva reserva = restaurante.getReservas().stream().filter(r -> r.getId().equals(Long.valueOf(idReserva))).findFirst().orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        List<Reserva> reservas = new ArrayList<>(restaurante.getReservas());
        reservas.remove(reserva);
        restaurante.setReservas(reservas);
        restauranteRepository.save(restaurante);
    }


    public Boolean isIndisponivel(Restaurante restaurante, LocalDate data, LocalTime hora){
        return (restaurante.getReservas().size() >= restaurante.getQtdMesas()) && restaurante.getReservas().stream().anyMatch(r -> r.getData().equals(data) && r.getHora().equals(hora));
    }
}
