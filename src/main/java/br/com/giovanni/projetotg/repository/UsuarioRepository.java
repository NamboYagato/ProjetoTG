package br.com.giovanni.projetotg.repository;

import br.com.giovanni.projetotg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    Optional<Usuario> findByEmailIgnoreCase(String email);
}
