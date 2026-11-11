package caal.repository;

import caal.entity.DadosUsuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DadosUsuarioRepository
        extends JpaRepository<DadosUsuario, String> {
    boolean existsByEmail(String email);

}
