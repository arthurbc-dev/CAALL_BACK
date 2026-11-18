package caal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(length = 8)
    private String cep;

    @Column(length = 100)
    private String bairro;

    @Column(length = 100)
    private String cidade;

    @Column(length = 100)
    private String estado;

}
