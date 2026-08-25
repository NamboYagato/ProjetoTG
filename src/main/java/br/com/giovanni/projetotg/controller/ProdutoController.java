package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.dto.ProdutoDtoRequest;
import br.com.giovanni.projetotg.dto.ProdutoDtoResponse;
import br.com.giovanni.projetotg.dto.VotoDtoRequest;
import br.com.giovanni.projetotg.dto.VotoDtoResponse;
import br.com.giovanni.projetotg.model.Produto;
import br.com.giovanni.projetotg.service.GerenciaProdutos;
import br.com.giovanni.projetotg.service.VotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final GerenciaProdutos gerenciaProdutos;
    private final VotoService votoService;

    public ProdutoController(GerenciaProdutos gerenciaProdutos, VotoService votoService) {
        this.gerenciaProdutos = gerenciaProdutos;
        this.votoService = votoService;
    }

    @GetMapping
    public List<ProdutoDtoResponse> getProdutos(@RequestParam(required = false, name = "name") String nome) {
        return gerenciaProdutos.getProdutos(nome);
    }

    @PostMapping
    public ResponseEntity<ProdutoDtoResponse> novoProduto(@RequestBody ProdutoDtoRequest dtoRequest) {
        ProdutoDtoResponse novoProduto = gerenciaProdutos.novoProduto(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @PatchMapping("/{id}")
    public ProdutoDtoResponse editarProduto(@PathVariable long id, @RequestBody Produto produto) {
        ProdutoDtoResponse produtoEditado = gerenciaProdutos.editarProduto(produto.getNome(), produto.getValor(), id);
        return produtoEditado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoDtoResponse> deleteProduto(@PathVariable long id) {
        gerenciaProdutos.deletarProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ProdutoDtoResponse buscaProdutoPorId(@PathVariable long id) {
        ProdutoDtoResponse produtoBuscado = gerenciaProdutos.buscarProduto(id);
        return produtoBuscado;
    }

    @GetMapping("/mercados/{id}")
    public List<ProdutoDtoResponse> produtosDoMercado(@PathVariable long id) {
        List<ProdutoDtoResponse> produtos = gerenciaProdutos.buscarProdutosPorMercado(id);
        return produtos;
    }

    @GetMapping("/usuarios/{id}")
    public List<ProdutoDtoResponse> produtosDoUsuario(@PathVariable long id) {
        List<ProdutoDtoResponse> produtos = gerenciaProdutos.buscarProdutosPorUsuario(id);
        return produtos;
    }

    @PostMapping("/{id}/votos")
    public ResponseEntity<VotoDtoResponse> votarNoProduto(@PathVariable long id, @RequestBody VotoDtoRequest votoDtoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(votoService.votar(id, votoDtoRequest));
    }
}
