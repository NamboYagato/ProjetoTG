package br.com.giovanni.projetotg.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Mercado {
    private String nome;
    private String endereco;
    private List<Produto> produtos;

    public Mercado(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.produtos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    @Override
    public String toString() {
        return getNome() + ", " + getEndereco() + ", " + getProdutos();
    }

    public void postProduto(Produto produto) {
        produtos.add(produto);
    }

    public void buscaProduto(String nome) {
        if (!produtos.isEmpty()) {
            List<Produto> buscaProduto = produtos.stream()
                    .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                    .collect(Collectors.toList());
            System.out.println(buscaProduto);
        } else {
            System.out.println("Ainda não temos");
        }
    }

    public boolean verificaIndex(int index) {
        if (index > 0 && produtos.size() - index > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void removerProduto(int index) {
        produtos.remove(index-1);
    }
}
