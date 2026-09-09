package repository;

import entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // indica que é um DAO
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email); //verifica se existe um usuario com o mesmo email


    Optional<Usuario> findByEmail(String email);
}
