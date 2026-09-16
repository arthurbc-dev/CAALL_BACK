package caal.service;

import caal.dto.AuthRequestDTO;
import caal.dto.AuthResponseDTO;
import caal.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import caal.entity.Usuario;
import caal.exception.CredenciaisInvalidasException;


import java.util.Locale;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponseDTO login (AuthRequestDTO dto) {
        String usernameNormalizado = dto.getUsername()
                .trim()
                .toLowerCase(Locale.ROOT);

        Usuario usuario = usuarioRepository.findByUsuario(usernameNormalizado).orElseThrow(CredenciaisInvalidasException::new);

        boolean senhaCorreta = passwordEncoder.matches(dto.getPassword(), usuario.getSenha());

        if (!senhaCorreta) {
            throw new CredenciaisInvalidasException();
        }

        AuthResponseDTO resposta = new AuthResponseDTO();
        resposta.setToken(jwtService.gerarToken(usuario.getUsuario()));
        resposta.setType("Bearer");

        return resposta;
    }

}
