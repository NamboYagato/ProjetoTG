package br.com.giovanni.projetotg.model;

import br.com.giovanni.projetotg.enums.Cidades;
import br.com.giovanni.projetotg.enums.Estados;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Mercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String rua;
    @Column(nullable = false)
    private Integer numero;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Cidades cidade;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estados estado;
    @Column(nullable = false)
    private String cep;
    @OneToMany(mappedBy = "mercado", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Produto> produtos;

    public Mercado() {
        this.produtos = new ArrayList<>();
    }

    public Mercado(String nome, String rua, Integer numero, String bairro, Cidades cidade, Estados estado, String cep) {
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.produtos = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRua() {
        return rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public Cidades getCidade() {
        return cidade;
    }

    public Estados getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCidade(Cidades cidade) {
        this.cidade = cidade;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
