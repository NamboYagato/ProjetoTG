package br.com.giovanni.projetotg.repository;

import br.com.giovanni.projetotg.model.Mercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;

public interface MercadoRepository extends JpaRepository<Mercado, Long> {
    List<Mercado> findByNomeContainingIgnoreCase(String nome);

//    @NativeQuery(value = "SELECT * FROM mercado WHERE to_tsvector('portuguese', mercado.nome) @@ plainto_tsquery('portuguese', ?1)")
//    List<Mercado> findByNome(String nome);
}
