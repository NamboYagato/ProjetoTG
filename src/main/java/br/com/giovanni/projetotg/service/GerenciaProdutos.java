package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.model.Produto;
import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.repository.MercadoRepository;
import br.com.giovanni.projetotg.repository.ProdutoRepository;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GerenciaProdutos {
    private final ProdutoRepository produtoRepository;
    private final MercadoRepository mercadoRepository;
    private final UsuarioRepository usuarioRepository;

    public GerenciaProdutos(ProdutoRepository produtoRepository, MercadoRepository mercadoRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.mercadoRepository = mercadoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Produto> getProdutos() {
        return produtoRepository.findAll();
    }

    public Produto novoProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto editarProduto(long id, String nome, double valor) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado!"));
        if (!nome.isBlank()) {
            produto.setNome(nome);
        }
        if (valor > 0) {
            produto.setValor(valor);
        }
        return produtoRepository.save(produto);
    }

    public void deletarProduto(long id) {
        produtoRepository.deleteById(id);
    }

    public Produto buscarProduto(long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado!"));
    }

    public List<Produto> buscarProdutosPorMercado(long id) {
        Mercado mercado = mercadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!"));
        return produtoRepository.findByMercado(mercado);
    }

    public List<Produto> buscarProdutosPorUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        return produtoRepository.findByUsuario(usuario);
    }
}
