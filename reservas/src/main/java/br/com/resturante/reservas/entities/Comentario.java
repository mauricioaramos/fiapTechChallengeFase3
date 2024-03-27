package br.com.resturante.reservas.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Comentario {
    private Long id;
    private String texto;
    private Integer nota;
    private Cliente cliente;
    private Restaurante restaurante;
}
