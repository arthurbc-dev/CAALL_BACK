package caal.entity;
import caal.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="tb_responsavel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Responsavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String nome;

    @Column
    private LocalDate dataNascimento;

    @Column(length = 20)
    private String telefone;

    @NotNull(message = "O gênero é obrigatório")
    private Genero genero;


    @Column
    private String cep;

    @Column
    private String bairro;

    @Column
    private String cidade;

    @Column
    private String estado;
}
