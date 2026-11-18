package caal.dto;


import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResponsavelCadastroDTO {
    private String nome;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 números")
    private String cpf;

    @Pattern(regexp = "\\d{11}", message = "Telefone deve conter 11 números")
    private String telefone;

}
