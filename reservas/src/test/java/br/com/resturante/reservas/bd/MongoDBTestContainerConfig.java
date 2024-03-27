package br.com.resturante.reservas.bd;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MongoDBContainer;

@Configuration
//@EnableMongoRepositories(basePackages = "br.com.resturante.reservas.usecases.service")
@ComponentScan(basePackages = "br.com.resturante.reservas")
public class MongoDBTestContainerConfig {
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.2")
            .withExposedPorts(27017);

    static {
        mongoDBContainer.start();
        var mappedPort = mongoDBContainer.getMappedPort(27017);
        System.setProperty("mongodb.container.port", String.valueOf(mappedPort));
    }
}