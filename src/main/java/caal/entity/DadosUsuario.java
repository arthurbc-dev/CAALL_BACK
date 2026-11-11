package caal.entity;
import caal.enums.Genero;
import caal.enums.TipoSanguineo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_dados_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DadosUsuario {

    @Id
    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "cpf",
            referencedColumnName = "cpf",
            insertable = false,
            updatable = false
    )
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;


    @NotNull(message = "O gênero é obrigatório")
    @Enumerated(EnumType.STRING)
    private Genero genero;


    private String telefone;
    private String cep;
    private String bairro;
    private String cidade;
    private String estado;

    @Column(name = "condicao_saude", length = 1000)
    private String condicaoSaude;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sanguineo")
    private TipoSanguineo tipoSanguineo;

    @Column(length = 600)
    private String alergias;

    @Column(length = 600)
    private String medicamentos;

    @Column(unique = true, length = 250)
    private String email;

    @Column(name = "fcm_token")
    private String fcmToken;
}
