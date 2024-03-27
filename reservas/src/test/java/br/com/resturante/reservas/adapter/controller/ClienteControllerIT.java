package br.com.resturante.reservas.adapter.controller;


import br.com.resturante.reservas.bd.MongoDBTestContainerConfig;
import br.com.resturante.reservas.entities.Cliente;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@AutoConfigureDataMongo
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    MongoTemplate mongoTemplate;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        var cliente = new Cliente();
        cliente.setId("151");
        cliente.setNome("ze da manga");
        cliente.setEndereco("Rua da mangueira");
        cliente.setEmail("email@teste.com");
        cliente.setTelefone("123456789");
        mongoTemplate.save(cliente);
    }

    @AfterEach
    void tearDown() throws Exception {
        mongoTemplate.dropCollection(Cliente.class);
    }

    @Test
    void shouldReturnClienteWhenBuscarClienteIsCalledWithValidInput() {

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cliente/{id}", "151")
                .then()
                .statusCode(200)
                .body("nome", equalTo("ze da manga"))
                .body("endereco", equalTo("Rua da mangueira"))
                .body("telefone", equalTo("123456789"));
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorNomeIsCalledWithValidInput() {

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cliente/nome/{nome}", "ze da manga")
                .then()
                .statusCode(200)
                .body("nome", equalTo("ze da manga"))
                .body("endereco", equalTo("Rua da mangueira"))
                .body("telefone", equalTo("123456789"));
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorEnderecoIsCalledWithValidInput() {

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cliente/endereco/{endereco}", "Rua da mangueira")
                .then()
                .statusCode(200)
                .body("nome", equalTo("ze da manga"))
                .body("endereco", equalTo("Rua da mangueira"))
                .body("telefone", equalTo("123456789"));
    }

    @Test
    void shouldReturnClienteWhenBuscarClientePorTelefoneIsCalledWithValidInput() {

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cliente/telefone/{telefone}", "123456789")
                .then()
                .statusCode(200)
                .body("nome", equalTo("ze da manga"))
                .body("endereco", equalTo("Rua da mangueira"))
                .body("telefone", equalTo("123456789"));
    }

    @Test
    void shouldReturnClienteWhenCadastrarClienteIsCalledWithValidInput() {
        var cliente = new Cliente();
        cliente.setNome("Test");
        cliente.setEndereco("Test");
        cliente.setEmail("email@teste.com");
        cliente.setTelefone("123456789");

        given()
                .filter(new AllureRestAssured())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cliente)
                .when()
                .post("/cliente")
                .then()
                .statusCode(201)
                .body("nome", equalTo("Test"))
                .body("endereco", equalTo("Test"))
                .body("id", notNullValue())
                .body("telefone", equalTo("123456789"));
    }

}
