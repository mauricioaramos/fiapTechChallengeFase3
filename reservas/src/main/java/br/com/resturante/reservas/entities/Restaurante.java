package br.com.resturante.reservas.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class Restaurante {
    @Id
    private String id;
    private String nome;
    private String localizacao;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Integer capacidade;
    private Integer qtdMesas;
    private List<Mesa> mesas = new ArrayList<>();
    private List<Reserva> reservas = new ArrayList<>();
}
