package br.com.giovanni.projetotg.repository;

import br.com.giovanni.projetotg.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByUsuarioIdAndProdutoId(UUID usuarioId, long produtoId);
}
