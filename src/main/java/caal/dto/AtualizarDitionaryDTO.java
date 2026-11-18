package caal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarDitionaryDTO {

    @NotBlank
    @Size(max=999, message = "ditionary deve ter menos de 1000 caracteres")
    private String ditionary;
}
