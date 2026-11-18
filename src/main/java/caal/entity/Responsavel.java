package caal.entity;
import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(length = 100)
    private String nome;

    @Column(length = 11)
    private String cpf;

    @Column(length = 11)
    private String telefone;


}
