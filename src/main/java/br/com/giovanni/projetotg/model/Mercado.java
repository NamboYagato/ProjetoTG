package br.com.giovanni.projetotg.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Mercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String endereco;
    @OneToMany(mappedBy = "mercado", fetch = FetchType.EAGER)
    private List<Produto> produtos;

    public Mercado() {
    }

    public Mercado(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.produtos = new ArrayList<>();
    }

    public long getId() {
        return id;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "id: " + getId() + " " + getNome() + ", " + getEndereco() + ", " + getProdutos();
    }

    public void postProduto(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> buscaProduto(String nome) {
        List<Produto> buscaProduto = produtos.stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
        return buscaProduto;
    }

    public Produto getProduto(int index) {
        Produto produto = produtos.get(index - 1);
        return produto;
    }

    public boolean verificaIndex(int index) {
        return index > 0 && produtos.size() - index > 0 && produtos.size() > index;
    }

    public void removerProduto(int index) {
        produtos.remove(index-1);
    }
}
