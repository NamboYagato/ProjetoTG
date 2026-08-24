package br.com.giovanni.projetotg.repository;

import br.com.giovanni.projetotg.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Optional<Voto> findByUsuarioIdAndProdutoId(long usuarioId, long produtoId);
}
