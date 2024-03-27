package br.com.resturante.reservas.adapter.controller;

import br.com.resturante.reservas.dto.ReservaDto;
import br.com.resturante.reservas.entities.Reserva;
import br.com.resturante.reservas.entities.Restaurante;
import br.com.resturante.reservas.usecases.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }


    @PostMapping
    public ResponseEntity<Restaurante> cadastrarRestaurante(@RequestBody Restaurante restaurante) {
        if (restaurante == null) {
            return ResponseEntity.badRequest().build();
        }
        Restaurante novoRestaurante = restauranteService.cadastrarRestaurante(restaurante);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
    }

    @GetMapping
    public ResponseEntity<List<Restaurante>> listarRestaurantes() {
        List<Restaurante> restaurantes = restauranteService.listarRestaurantes();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarRestaurantePorId(@PathVariable String id) {
        Restaurante restaurante = restauranteService.buscarRestaurantePorId(id);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Restaurante> buscarRestaurantePorNome(@PathVariable String nome) throws Exception {
        Restaurante restaurante = restauranteService.buscarRestaurantePorNome(nome);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/endereco/{endereco}")
    public ResponseEntity<Restaurante> buscarRestaurantePorEndereco(@PathVariable String endereco) throws Exception {
        Restaurante restaurante = restauranteService.buscarRestaurantePorEndereco(endereco);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<Restaurante> buscarRestaurantePorTipoCozinha(@PathVariable String tipo) throws Exception {
        Restaurante restaurante = restauranteService.buscarRestaurantePorTipoCozinha(tipo);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizarRestaurante(@PathVariable String id, @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtualizado = restauranteService.atualizarRestaurante(id, restaurante);
        if (restauranteAtualizado != null) {
            return ResponseEntity.ok(restauranteAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable String id) {
        restauranteService.deletarRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reservar")
    public ResponseEntity<Reserva> reservarRestaurante(@RequestBody ReservaDto reservaDto) {
        Reserva novoRestaurante = restauranteService.reservarRestaurante(reservaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRestaurante);
    }

    @PutMapping("/restaurante/{idRestaurante}/comentario/{textoComentario}/reserva/{idReserva}")
    public ResponseEntity<Reserva> adicionarComentario(@PathVariable String idRestaurante, @PathVariable String idReserva, @PathVariable String textoComentario) {
        Restaurante restaurante = restauranteService.buscarRestaurantePorId(idRestaurante);

        Reserva reservaAtualizada = restauranteService.adicionarComentario(restaurante, idReserva, textoComentario);
        if (reservaAtualizada != null) {
            return ResponseEntity.ok(reservaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/restaurante/{idRestaurante}/reserva")
    public ResponseEntity<Reserva> atualizarReserva(@PathVariable String idRestaurante, @RequestBody Reserva reserva) {
        Reserva reservaAtualizada = restauranteService.atualizarReserva(idRestaurante, reserva);
        if (reservaAtualizada != null) {
            return ResponseEntity.ok(reservaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/reservas/{id}")
    public ResponseEntity<List<Reserva>> listarReservasPorRestaurante(@PathVariable String id) {
        List<Reserva> reservas = restauranteService.listarReservasPorRestaurante(id);
        return ResponseEntity.ok(reservas);
    }
}
