package caal.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonFormat
public class UsuarioPerfilDTO {

    @NotBlank
    private  String nome;

    @NotNull
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Email
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

}
