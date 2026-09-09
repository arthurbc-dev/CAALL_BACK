package service;

import dto.UsuarioCadastroDTO;
import dto.UsuarioRespostaDTO;
import entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UsuarioRepository;


@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioRespostaDTO cadastrarUsuario(UsuarioCadastroDTO dto){
        if(usuarioRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("O email é informado já está cadastrado");
        }
            String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .senha(senhaCriptografada)
                .fcmToken(dto.getFcmToken())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioRespostaDTO.builder()
                .id(usuarioSalvo.getId())
                .nome(usuarioSalvo.getNome())
                .email(usuarioSalvo.getEmail())
                .build();
    }
}
