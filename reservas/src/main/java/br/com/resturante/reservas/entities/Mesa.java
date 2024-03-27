package br.com.resturante.reservas.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Mesa {
    private Long id;
    private Integer numero;
    private Integer capacidade;
    private Boolean disponivel;

    public boolean isDisponivel() {
        return disponivel;
    }
}
