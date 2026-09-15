package caal.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsuarioRespostaDTO {
    private Long id;
    private String usuario;
}
