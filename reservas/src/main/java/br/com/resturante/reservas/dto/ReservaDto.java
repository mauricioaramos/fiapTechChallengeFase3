package br.com.resturante.reservas.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ReservaDto {

    private String nome;
    private String email;
    private String telefone;
    private LocalDate data;
    private LocalTime hora;
    private String idRestaurante;
    private String clienteId;

    public ReservaDto(String idRestaurante, LocalDate data, LocalTime hora) {
        this.idRestaurante = idRestaurante;
        this.data = data;
        this.hora = hora;

    }
}
