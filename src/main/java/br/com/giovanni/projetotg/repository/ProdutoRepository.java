package br.com.giovanni.projetotg.repository;

import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.model.Produto;
import br.com.giovanni.projetotg.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByMercado(Mercado mercado);

    List<Produto> findByUsuario(Usuario usuario);
}
