package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.model.Produto;
import br.com.giovanni.projetotg.service.GerenciaProdutos;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final GerenciaProdutos gerenciaProdutos;

    public ProdutoController(GerenciaProdutos gerenciaProdutos) {
        this.gerenciaProdutos = gerenciaProdutos;
    }

    @GetMapping
    public List<Produto> getProdutos() {
        return gerenciaProdutos.getProdutos();
    }

    @PostMapping
    public ResponseEntity<Produto> novoProduto(@RequestBody Produto produto) {
        Produto novoProduto = gerenciaProdutos.novoProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @PatchMapping("/{id}")
    public Produto editarProduto(@PathVariable long id, @RequestBody Produto produto) {
        Produto produtoEditado = gerenciaProdutos.editarProduto(id, produto.getNome(), produto.getValor());
        return produtoEditado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Produto> deleteProduto(@PathVariable long id) {
        gerenciaProdutos.deletarProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public Produto buscaProdutoPorId(@PathVariable long id) {
        Produto produtoBuscado = gerenciaProdutos.buscarProduto(id);
        return produtoBuscado;
    }

    @GetMapping("/mercados/{id}")
    public List<Produto> produtosDoMercado(@PathVariable long id) {
        List<Produto> produtos = gerenciaProdutos.buscarProdutosPorMercado(id);
        return produtos;
    }

    @GetMapping("/usuarios/{id}")
    public List<Produto> produtosDoUsuario(@PathVariable long id) {
        List<Produto> produtos = gerenciaProdutos.buscarProdutosPorUsuario(id);
        return produtos;
    }
}
