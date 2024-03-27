package br.com.resturante.reservas.bdd;

import br.com.resturante.reservas.dto.ReservaDto;
import br.com.resturante.reservas.entities.Cliente;
import br.com.resturante.reservas.entities.Mesa;
import br.com.resturante.reservas.entities.Reserva;
import br.com.resturante.reservas.entities.Restaurante;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;

    private Restaurante restauranteResponse;

    private String ENDPOINT_CADASTRAR_RESTAURANTE = "http://localhost:80/restaurante";
    private String ENDPOINT_CADASTRAR_RESERVAR = "http://localhost:80/restaurante/reservar";

    @Dado("que o restaurante tem uma mesa disponível")
    public Restaurante que_o_restaurante_tem_uma_mesa_disponível() {
        Reserva reserva = new Reserva();
        reserva.setCliente(new Cliente());
        reserva.getCliente().setNome("Teste");
        reserva.getCliente().setEndereco("Rua Teste");
        reserva.getCliente().setTelefone("123456789");
        reserva.setData(LocalDate.now());
        reserva.setHora(LocalTime.of(13, 30));

        Mesa mesa = new Mesa();
        mesa.setNumero(1);
        mesa.setCapacidade(4);
        mesa.setDisponivel(true);
        reserva.setMesa(mesa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Restaurante Teste");
        restaurante.setLocalizacao("Rua Teste");
        restaurante.getReservas().add(reserva);
        restaurante.setQtdMesas(10);
        restaurante.getMesas().add(mesa);

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restaurante)
                .when()
                .post(ENDPOINT_CADASTRAR_RESTAURANTE);
        restauranteResponse = response.then().extract().as(Restaurante.class);
        return response.then().extract().as(Restaurante.class);
    }

    @Quando("eu reservo a mesa")
    public Reserva eu_reservo_a_mesa() {
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.setNome("Teste");
        reservaDto.setIdRestaurante(restauranteResponse.getId());
        reservaDto.setTelefone("123456789");
        reservaDto.setData(LocalDate.now());
        reservaDto.setHora(LocalTime.of(13, 30));

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservaDto)
                .when()
                .post(ENDPOINT_CADASTRAR_RESERVAR);

        return response.then().extract().as(Reserva.class);

    }
    @Então("a reserva é feita com sucesso")
    public void a_reserva_é_feita_com_sucesso() {
        response.then().statusCode(201);

    }

    @Dado("que o restaurante nao tem uma mesa disponível")
    public Restaurante que_o_restaurante_nao_tem_mesa_disponível() {
        Reserva reserva = new Reserva();
        reserva.setCliente(new Cliente());
        reserva.getCliente().setNome("Teste");
        reserva.getCliente().setEndereco("Rua Teste");
        reserva.getCliente().setTelefone("123456789");
        reserva.setData(LocalDate.now());
        reserva.setHora(LocalTime.of(13, 30));

        Mesa mesa = new Mesa();
        mesa.setNumero(1);
        mesa.setCapacidade(4);
        mesa.setDisponivel(false);
        reserva.setMesa(mesa);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Restaurante Teste");
        restaurante.setLocalizacao("Rua Teste");
        restaurante.getReservas().add(reserva);
        restaurante.setQtdMesas(1);
        restaurante.getMesas().add(mesa);

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restaurante)
                .when()
                .post(ENDPOINT_CADASTRAR_RESTAURANTE);
        restauranteResponse = response.then().extract().as(Restaurante.class);
        return response.then().extract().as(Restaurante.class);
    }

    @Quando("eu tento reservar a mesa")
    public void eu_tento_reservar_a_mesa() {
        ReservaDto reservaDto = new ReservaDto();
        reservaDto.setNome("Teste");
        reservaDto.setIdRestaurante(restauranteResponse.getId());
        reservaDto.setTelefone("123456789");
        reservaDto.setData(LocalDate.now());
        reservaDto.setHora(LocalTime.of(13, 30));

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(reservaDto)
                .when()
                .post(ENDPOINT_CADASTRAR_RESERVAR);
    }

    @Então("a reserva não é feita")
    public void a_reserva_não_é_feita() {
        response.then().statusCode(500);
    }


}
