package caal.service;

import caal.dto.AtualizarDitionaryDTO;
import caal.dto.ResponsavelCadastroDTO;
import caal.dto.UsuarioCadastroDTO;
import caal.dto.UsuarioRespostaDTO;
import caal.entity.*;
import caal.exception.UsuarioExisteException;
import caal.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
public class UsuarioService {

    private static final String DITIONARY_PADRAO =
            "Eu,Você,Nós,Eles,Ela,Ele,Meu,Seu,Sua@Quero,Gosto,Sinto,Estou,Vou,Falo,Penso,Acho,Preciso@Ir," +
                    "Ficar,Sair,Voltar,Correr,Andar,Parar,Pular,Sentar@Comer,Beber,Mastigar,Engolir,Provar," +
                    "Lanchar,Jantar,Almoçar,Ceia@Água,Suco,Leite,Café,Chá,Refrigerante,Sopa,Vitamina,Achocolatado@Brincar," +
                    "Jogar,Assistir,Ouvir,Ler,Desenhar,Pintar,Cantar,Dançar@Dormir,Acordar,Descansar,Deitar,Sonhar,Cochilar," +
                    "Relaxar,Espreguiçar,Bocejar@Ajuda,Por favor,Obrigado,Desculpa,Sim,Não,Talvez,Agora,Depois";

    private final UsuarioRepository usuarioRepository;
    private final DadosPessoaisRepository dadosPessoaisRepository;
    private final EnderecoRepository enderecoRepository;
    private final SaudeRepository saudeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ResponsavelRepository responsavelRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            DadosPessoaisRepository dadosPessoaisRepository,
            EnderecoRepository enderecoRepository,
            SaudeRepository saudeRepository,
            PasswordEncoder passwordEncoder,
            ResponsavelRepository responsavelRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.dadosPessoaisRepository = dadosPessoaisRepository;
        this.enderecoRepository = enderecoRepository;
        this.saudeRepository = saudeRepository;
        this.passwordEncoder = passwordEncoder;
        this.responsavelRepository = responsavelRepository;

    }

    @Transactional
    public UsuarioRespostaDTO cadastrarUsuario(UsuarioCadastroDTO dto) {
        String usuarioNormalizado = dto.getUsuario()
                .trim()
                .toLowerCase(Locale.ROOT);

        String cpfNormalizado = dto.getCpf().trim();

        if (usuarioRepository.existsByUsuario(usuarioNormalizado)) {
            throw new UsuarioExisteException(
                    "O usuário informado já está cadastrado"
            );
        }

        if (dadosPessoaisRepository.existsByCpf(cpfNormalizado)) {
            throw new UsuarioExisteException(
                    "O CPF informado já está cadastrado"
            );
        }

        if (dto.getEmail() != null
                && !dto.getEmail().isBlank()
                && dadosPessoaisRepository.existsByEmail(dto.getEmail())) {
            throw new UsuarioExisteException(
                    "O e-mail informado já está cadastrado"
            );
        }

        Usuario usuario = Usuario.builder()
                .usuario(usuarioNormalizado)
                .senha(passwordEncoder.encode(dto.getSenha()))
                .fcmToken(dto.getFcmToken())
                .build();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        DadosPessoais dadosPessoais = DadosPessoais.builder()
                .usuario(usuarioSalvo)
                .nome(dto.getNome())
                .cpf(cpfNormalizado)
                .dataNascimento(dto.getDataNascimento())
                .genero(dto.getGenero())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .ditionary(DITIONARY_PADRAO)
                .build();

        dadosPessoaisRepository.save(dadosPessoais);

        if (possuiDadosEndereco(dto)) {
            Endereco endereco = Endereco.builder()
                    .usuario(usuarioSalvo)
                    .cep(dto.getCep())
                    .bairro(dto.getBairro())
                    .cidade(dto.getCidade())
                    .estado(dto.getEstado())
                    .build();

            enderecoRepository.save(endereco);
        }

        if (possuiDadosSaude(dto)) {
            Saude saude = Saude.builder()
                    .usuario(usuarioSalvo)
                    .condicaoSaude(dto.getCondicaoSaude())
                    .tipoSanguineo(dto.getTipoSanguineo())
                    .alergias(dto.getAlergias())
                    .medicamentos(dto.getMedicamentos())
                    .build();

            saudeRepository.save(saude);
        }

        if (possuiDadosResponsavel(dto.getResponsavel())) {
            ResponsavelCadastroDTO responsavelDto = dto.getResponsavel();

            Responsavel responsavel = Responsavel.builder()
                    .usuario(usuarioSalvo)
                    .nome(responsavelDto.getNome())
                    .cpf(responsavelDto.getCpf())
                    .telefone(responsavelDto.getTelefone())
                    .build();

            responsavelRepository.save(responsavel);
        }

        return UsuarioRespostaDTO.builder()
                .id(usuarioSalvo.getId())
                .usuario(usuarioSalvo.getUsuario())
                .build();
    }



    @Transactional
    public void atualizarDitionary(
            String usuarioLogado,
            Long usuarioId,
            AtualizarDitionaryDTO dto
    ) {
        Usuario logado = usuarioRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        DadosPessoais dadosPessoais = dadosPessoaisRepository
                .findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException(
                        "Dados pessoais não encontrados"
                ));

        if (!logado.getId().equals(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        
        validarDitionary(dto.getDitionary());
        dadosPessoais.setDitionary(dto.getDitionary().trim());

        dadosPessoaisRepository.save(dadosPessoais);
    }

    private boolean possuiDadosEndereco(UsuarioCadastroDTO dto) {
        return possuiTexto(dto.getCep())
                || possuiTexto(dto.getBairro())
                || possuiTexto(dto.getCidade())
                || possuiTexto(dto.getEstado());
    }

    private boolean possuiDadosSaude(UsuarioCadastroDTO dto) {
        return possuiTexto(dto.getCondicaoSaude())
                || dto.getTipoSanguineo() != null
                || possuiTexto(dto.getAlergias())
                || possuiTexto(dto.getMedicamentos());
    }

    private boolean possuiTexto(String valor) {
        return valor != null && !valor.isBlank();
    }

    private boolean possuiDadosResponsavel(
            ResponsavelCadastroDTO responsavel
    ) {
        if (responsavel == null) {
            return false;
        }

        return possuiTexto(responsavel.getNome())
                || possuiTexto(responsavel.getCpf())
                || possuiTexto(responsavel.getTelefone());
    }
    private void validarDitionary(String ditionary) {
        String[] camadas = ditionary.split("@", -1);

        if (camadas.length > 8) {
            throw new IllegalArgumentException(
                    "O ditionary pode possuir no máximo 8 camadas"
            );
        }
    }
}