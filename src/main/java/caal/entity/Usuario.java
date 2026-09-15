package caal.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private  String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String usuario;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past
    private LocalDate dataNascimento;

    @Column(length = 20)
    private String genero;

    @Column(length = 20)
    private String telefone;

    @Column(length = 10)
    private String  cep;

    @Column(length = 100)
    private String bairro;

    @Column(length = 100)
    private String cidade;

    @Column(length = 100)
    private String estado;

    @Column(name = "tipo_sanguineo", length = 5)
    private String tipoSanguineo;

    @Column(columnDefinition = "TEXT")
    private String alergias;

    @Column(columnDefinition = "TEXT")
    private String medicamentos;

    @Column(unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;
}
