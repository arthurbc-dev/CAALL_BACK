package caal.controller;

import caal.dto.UsuarioCadastroDTO;
import caal.dto.UsuarioRespostaDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import caal.service.UsuarioService;
import caal.dto.AtualizarDitionaryDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioRespostaDTO> cadastrar(@Valid @RequestBody UsuarioCadastroDTO dto){
        UsuarioRespostaDTO usuarioCriado = usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PutMapping("/{usuarioId}/ditionary")
    public ResponseEntity<Void> atualizarDitionary(
            Authentication autenticacao,
            @PathVariable Long usuarioId,
            @Valid @RequestBody AtualizarDitionaryDTO dto
    ) {
        usuarioService.atualizarDitionary(autenticacao.getName(), usuarioId, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
