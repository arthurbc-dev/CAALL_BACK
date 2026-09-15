package caal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CadastroAcessoDTO {

    @NotBlank(message = "O usuario é obrigatorio") // garante que não seja nulo
    @Size(min = 3, max = 100)
    private String usuario;

    @NotBlank(message = "A senha é obrigatoria")
    @Size(min = 6, message = "A senha deve ter no minimo 6 caracters")
    private String senha;

    @NotBlank(message = "O token é obrigatorio")
    private String fcmToken;

}
