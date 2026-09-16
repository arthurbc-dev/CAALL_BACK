package caal.service;

import caal.dto.ResponsavelCadastroDTO;
import caal.dto.UsuarioCadastroDTO;
import caal.dto.UsuarioRespostaDTO;
import caal.entity.Responsavel;
import caal.entity.Usuario;
import caal.exception.UsuarioExisteException;
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
    public UsuarioRespostaDTO cadastrarUsuario(UsuarioCadastroDTO dto){

        String usuarioNormalizado = dto.getUsuario()
                .trim()
                .toLowerCase(Locale.ROOT);


        if (usuarioRepository.existsByUsuario(usuarioNormalizado)){
            throw new UsuarioExisteException("O usuario informado já está cadastrado");
        }


        Usuario usuario = Usuario.builder()
                .usuario(usuarioNormalizado)
                .senha(passwordEncoder.encode(dto.getSenha()))
                .fcmToken(dto.getFcmToken())
                .nome(dto.getNome())
                .dataNascimento(dto.getDataNascimento())
                .genero(dto.getGenero())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .cep(dto.getCep())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .tipoSanguineo(dto.getTipoSanguineo())
                .condicaoSaude(dto.getCondicaoSaude())
                .alergias(dto.getAlergias())
                .medicamentos(dto.getMedicamentos())
                .build();



        if (possuiDadosResponsavel(dto.getResponsavel())) {
            ResponsavelCadastroDTO responsavelDto = dto.getResponsavel();

            Responsavel responsavel = Responsavel.builder()
                    .nome(responsavelDto.getNome())
                    .dataNascimento(responsavelDto.getDataNascimento())
                    .genero(responsavelDto.getGenero())
                    .telefone(responsavelDto.getTelefone())
                    .cep(responsavelDto.getCep())
                    .bairro(responsavelDto.getBairro())
                    .cidade(responsavelDto.getCidade())
                    .estado(responsavelDto.getEstado())
                    .build();

            usuario.setResponsavel(responsavel);
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);



        return UsuarioRespostaDTO.builder()
                .id(usuarioSalvo.getId())
                .usuario(usuarioSalvo.getUsuario())

                .build();
    }

    private boolean possuiDadosResponsavel(ResponsavelCadastroDTO responsavel) {
        if (responsavel == null) {
            return false;
        }

        return possuiTexto(responsavel.getNome())
                || responsavel.getDataNascimento() != null
                || possuiTexto(responsavel.getGenero())
                || possuiTexto(responsavel.getTelefone())
                || possuiTexto(responsavel.getCep())
                || possuiTexto(responsavel.getBairro())
                || possuiTexto(responsavel.getCidade())
                || possuiTexto(responsavel.getEstado());
    }

    private boolean possuiTexto(String valor) {
        return valor != null && !valor.isBlank();
    }
}


