package br.com.resturante.reservas.entities;

import lombok.*;
import org.springframework.data.annotation.Id;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente {
    @Id
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
}
