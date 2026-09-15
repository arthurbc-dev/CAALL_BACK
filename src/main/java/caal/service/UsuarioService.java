package caal.service;

import caal.dto.CadastroAcessoDTO;
import caal.dto.UsuarioRespostaDTO;
import caal.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import caal.repository.UsuarioRepository;
import java.util.Locale;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public UsuarioRespostaDTO cadastrarUsuario(CadastroAcessoDTO dto){

        String usuarioNormalizado = dto.getUsuario()
                .trim()
                .toLowerCase(Locale.ROOT);


        if (usuarioRepository.existsByUsuario(usuarioNormalizado)){
            throw new IllegalArgumentException("O usuario informado já está cadastrado");
        }


        Usuario usuario = Usuario.builder()
                .usuario(usuarioNormalizado)
                .senha(passwordEncoder.encode(dto.getSenha()))
                .fcmToken(dto.getFcmToken())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioRespostaDTO.builder()
                .id(usuarioSalvo.getId())
                .usuario(usuarioSalvo.getUsuario())
                .build();
    }
}


