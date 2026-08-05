package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.dto.*;
import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.model.Produto;
import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.repository.MercadoRepository;
import br.com.giovanni.projetotg.repository.ProdutoRepository;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ProdutoDtoResponse> getProdutos(String nome) {
        List<ProdutoDtoResponse> response;
        List<Produto> produtos;
        if (nome != null) {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        } else {
            produtos = produtoRepository.findAll();
        }
        response = produtos.stream()
                .map(p -> new ProdutoDtoResponse(p.getNome(), p.getValor(), p.getId(),
                        new MercadoDtoSummary(p.getMercado().getNome(), p.getMercado().getId()),
                        p.getUsuario() == null ? new UsuarioDtoSummary("Usuário deletado", null) : new UsuarioDtoSummary(p.getUsuario().getNome(), p.getUsuario().getId())
                ))
                .collect(Collectors.toList());
        return response;
    }

    public ProdutoDtoResponse novoProduto(ProdutoDtoRequest dtoRequest) {
        Produto produto = new Produto(dtoRequest.nome(), dtoRequest.valor(), mercadoRepository.findById(dtoRequest.idMercado()).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!")), usuarioRepository.findById(dtoRequest.idUsuario()).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!")));
        produtoRepository.save(produto);
        return new ProdutoDtoResponse(produto.getNome(), produto.getValor(), produto.getId(),
                new MercadoDtoSummary(produto.getMercado().getNome(), produto.getMercado().getId()),
                new UsuarioDtoSummary(produto.getUsuario().getNome(), produto.getUsuario().getId())
                );
    }

    public ProdutoDtoResponse editarProduto(String nome, double valor, long id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado!"));
        if (nome != null && !nome.isBlank()) {
            produto.setNome(nome);
        }
        if (valor > 0) {
            produto.setValor(valor);
        }
        produtoRepository.save(produto);
        return new ProdutoDtoResponse(produto.getNome(), produto.getValor(), produto.getId(),
                new MercadoDtoSummary(produto.getMercado().getNome(), produto.getMercado().getId()),
                new UsuarioDtoSummary(produto.getUsuario().getNome(), produto.getUsuario().getId()));
    }

    public void deletarProduto(long id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoDtoResponse buscarProduto(long id) {
        Produto p = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado!"));
        return new ProdutoDtoResponse(p.getNome(), p.getValor(), p.getId(), new MercadoDtoSummary(p.getMercado().getNome(), p.getMercado().getId()), new UsuarioDtoSummary(p.getUsuario().getNome(), p.getUsuario().getId()));
    }

    public List<ProdutoDtoResponse> buscarProdutosPorMercado(long id) {
        Mercado mercado = mercadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!"));
        List<Produto> produtos = produtoRepository.findByMercado(mercado);
        return produtos.stream()
                .map(p -> new ProdutoDtoResponse(p.getNome(), p.getValor(), p.getId(),
                        new MercadoDtoSummary(p.getMercado().getNome(), p.getMercado().getId()),
                        p.getUsuario() == null ? new UsuarioDtoSummary("Usuário deletado", null) : new UsuarioDtoSummary(p.getUsuario().getNome(), p.getUsuario().getId())
                ))
                .collect(Collectors.toList());
    }

    public List<ProdutoDtoResponse> buscarProdutosPorUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        List<Produto> produtos = produtoRepository.findByUsuario(usuario);
        return produtos.stream()
                .map(p -> new ProdutoDtoResponse(p.getNome(), p.getValor(), p.getId(),
                        new MercadoDtoSummary(p.getMercado().getNome(), p.getMercado().getId()),
                        new UsuarioDtoSummary(p.getUsuario().getNome(), p.getUsuario().getId())
                ))
                .collect(Collectors.toList());
    }
}
