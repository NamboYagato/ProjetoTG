package br.com.giovanni.projetotg.repository;

import br.com.giovanni.projetotg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    Optional<Usuario> findByEmailIgnoreCase(String email);
}
