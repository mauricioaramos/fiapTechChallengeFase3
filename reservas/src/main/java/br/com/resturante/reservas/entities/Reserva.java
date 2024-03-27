package br.com.resturante.reservas.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class Reserva {
    private Long id;
    private String restauranteId;
    private LocalDate data;
    private LocalTime hora;
    private Cliente cliente;
    private Mesa mesa;
    private Comentario comentario;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}