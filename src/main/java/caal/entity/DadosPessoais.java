package caal.entity;
import caal.enums.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_dados_pessoais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DadosPessoais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;


    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;


    @NotNull(message = "O gênero é obrigatório")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(nullable = false, length = 11)
    private String telefone;

    @Column(unique = true, length = 250)
    private String email;

    @Column(name = "ditionary", nullable = false, length = 1500)
    private String ditionary;

    @Column(name = "timestamp_dictionary", nullable = false, insertable = false, updatable = false)
    private LocalDateTime timestampDictionary;


}
