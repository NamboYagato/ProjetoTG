package br.com.giovanni.projetotg.repository;

import br.com.giovanni.projetotg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
