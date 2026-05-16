package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.model.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciaMercados {
    private List<Mercado> mercados;

    public GerenciaMercados() {
        this.mercados = new ArrayList<>(List.of(
                new Mercado("Shibata Supermercados - Caçapava", "Rod. João Amaral Gurgel, 980 - Res. Terras do Vale, Caçapava - SP, 12285-020"),
                new Mercado("Extra Mercado", "R. Reg. Feijó, 148 - Vila Santos, Caçapava - SP, 12280-034")
        ));
    }

    public void getMercados() {
        for (int i = 0; i < mercados.size(); i++) {
            System.out.println(i+1 + " - " + mercados.get(i));
        }
    }

    public void novoMercado(Mercado mercado) {
        this.mercados.add(mercado);
    }

    public List<Mercado> buscaMercado(String nomeMercado) {
        List<Mercado> busca = this.mercados.stream()
                .filter(m -> m.getNome().toLowerCase().contains(nomeMercado.toLowerCase()))
                .collect(Collectors.toList());
        return busca;
    }

    public List<Produto> buscaProdutosPorMercado(String nomeProduto) {
        List<Produto> produtoBuscado = mercados.stream()
                .map(m -> m.getProdutos())
                .flatMap(p -> p.stream()
                        .filter(produto -> produto.getNome().toLowerCase().contains(nomeProduto.toLowerCase())))
                .collect(Collectors.toList());
        return produtoBuscado;
    }

    public void novoProduto(int indexMercado, String nomeProduto, double valorProduto) {
        var mercadoSelecionado = mercados.get(indexMercado - 1);
        mercadoSelecionado.postProduto(new Produto(nomeProduto, valorProduto, mercadoSelecionado));
        System.out.println("Produto adicionado com sucesso!");
    }
}
