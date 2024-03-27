package br.com.resturante.reservas.performance;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
public class ApiPerformanceSimulation extends Simulation {
    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:80")
            .header("Content-Type", "application/json");

    ActionBuilder adicinarClienteRequest = http("Cadastrar cliente")
            .post("/cliente")
            .body(StringBody("{\"nome\":\"Test\",\"endereco\":\"Test\",\"telefone\":\"123456789\"}"))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("id"));

    ActionBuilder buscarClienteEncontrado = http("Buscar cliente")
            .get("/cliente/#{id}")
            .check(status().is(200));

    ActionBuilder buscarClienteNaoEncontrado = http("Buscar cliente nao encontrado")
            .get("/cliente/1")
            .check(status().is(404));

    private final ScenarioBuilder cenarioCadastrarCliente = scenario("cenário de cadastro de cliente")
            .exec(adicinarClienteRequest);

    private final ScenarioBuilder cenarioBuscarClienteNaoEncontrado = scenario("cenario de busca de cliente nao encontrado")
            .exec(buscarClienteNaoEncontrado);

    private final ScenarioBuilder cenarioBuscarClienteEncontrado = scenario("cenario de busca de cliente")
            .exec(buscarClienteEncontrado);


    {
        setUp(
                cenarioCadastrarCliente.injectOpen(
                                        rampUsersPerSec(1)
                                                .to(10)
                                                .during(Duration.ofSeconds(10)),
                                        constantUsersPerSec(10)
                                                .during(Duration.ofSeconds(60)),
                                        rampUsersPerSec(10)
                                                .to(1)
                                                .during(Duration.ofSeconds(10))),
                cenarioBuscarClienteNaoEncontrado.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(60)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10)))
        )
                .protocols(httpProtocol)
                .assertions(global().responseTime().max().lt(50),
                        global().failedRequests().count().is(0L));
    }

}
