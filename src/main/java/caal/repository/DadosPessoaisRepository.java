package caal.repository;

import caal.entity.DadosPessoais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DadosPessoaisRepository
        extends JpaRepository<DadosPessoais, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<DadosPessoais> findByUsuarioId(Long usuarioId);

}
