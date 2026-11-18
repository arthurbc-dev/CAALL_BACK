package caal.entity;

import caal.enums.TipoSanguineo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_saude")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Saude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(length = 1000)
    private String condicaoSaude;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoSanguineo tipoSanguineo;

    @Column( length = 1000)
    private String alergias;

    @Column(length = 1000)
    private String medicamentos;
}
