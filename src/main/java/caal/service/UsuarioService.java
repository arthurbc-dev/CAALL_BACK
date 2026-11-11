package caal.service;

import caal.dto.ResponsavelCadastroDTO;
import caal.dto.UsuarioCadastroDTO;
import caal.dto.UsuarioRespostaDTO;
import caal.entity.DadosUsuario;
import caal.entity.Responsavel;
import caal.entity.Usuario;
import caal.exception.UsuarioExisteException;
import caal.repository.DadosUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import caal.repository.UsuarioRepository;
import java.util.Locale;



@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final DadosUsuarioRepository dadosUsuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, DadosUsuarioRepository dadosUsuarioRepository){
        this.usuarioRepository = usuarioRepository;
        this.dadosUsuarioRepository = dadosUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public UsuarioRespostaDTO cadastrarUsuario(UsuarioCadastroDTO dto){

        String usuarioNormalizado = dto.getUsuario()
                .trim()
                .toLowerCase(Locale.ROOT);

        String cpfNormalizado = dto.getCpf()
                .trim()
                .toLowerCase(Locale.ROOT);



        if (usuarioRepository.existsByUsuario(usuarioNormalizado)){
            throw new UsuarioExisteException("O usuario informado já está cadastrado");
        }

        if(usuarioRepository.existsByCpf(cpfNormalizado)){
            throw new UsuarioExisteException("O CPF informado já está cadastrado");

        }


        Usuario usuario = Usuario.builder()
                .usuario(usuarioNormalizado)
                .cpf(cpfNormalizado)
                .senha(passwordEncoder.encode(dto.getSenha()))
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);


        DadosUsuario dados = DadosUsuario.builder()
                .fcmToken(dto.getFcmToken())
                .cpf(usuarioSalvo.getCpf())
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

        if (dados.getEmail() != null && !dados.getEmail().isBlank() && dadosUsuarioRepository.existsByEmail(dados.getEmail())) {
            throw new UsuarioExisteException("Email informado já está cadastrado");
        }

        if (possuiDadosResponsavel(dto.getResponsavel())) {
            ResponsavelCadastroDTO responsavelDto = dto.getResponsavel();
    //ainda pendente
            Responsavel responsavel = Responsavel.builder()
                    .nome(responsavelDto.getNome())
                    .dataNascimento(responsavelDto.getDataNascimento())
                    .telefone(responsavelDto.getTelefone())
                    .cep(responsavelDto.getCep())
                    .bairro(responsavelDto.getBairro())
                    .cidade(responsavelDto.getCidade())
                    .estado(responsavelDto.getEstado())
                    .build();

        }

        dadosUsuarioRepository.save(dados);


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


