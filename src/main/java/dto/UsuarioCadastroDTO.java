package dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCadastroDTO {

    @NotBlank(message = "O nome é obrigatorio") // garante que não seja nulo
    @Size(min = 3, max = 100)
    private String nome;

    @NotBlank(message = "O email é obrigatorio")
    @Email(message = "Forneça um email valido") //valida se o texto tem a estrutura do email ( @ e "." )
    private String email;

    @NotBlank(message = "O email é obrigatorio")
    @Size(min = 6, message = "A senha deve ter no minimo 6 caracters")
    private String senha;

    private String fcmToken;


}
