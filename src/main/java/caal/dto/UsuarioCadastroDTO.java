package caal.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioCadastroDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100)
    private  String nome;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve estar no passado")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Email(message = "Informe um e-mail válido")
    private String email;
    
    private String genero;

    private String telefone;

    private String  cep;

    private String bairro;

    private String cidade;

    private String estado;

    private String tipoSanguineo;

    private String alergias;

    private String medicamentos;

    private String condicaoSaude;

    @NotBlank(message = "O usuario é obrigatorio") // garante que não seja nulo
    @Size(min = 3, max = 100)
    private String usuario;

    @NotBlank(message = "A senha é obrigatoria")
    @Size(min = 6, message = "A senha deve ter no minimo 6 caracters")
    private String senha;

    @NotBlank(message = "O token é obrigatorio")
    private String fcmToken;

    @Valid
    private ResponsavelCadastroDTO responsavel;

}
