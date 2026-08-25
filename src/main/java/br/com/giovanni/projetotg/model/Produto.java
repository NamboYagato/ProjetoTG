package br.com.giovanni.projetotg.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private double valor;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Mercado mercado;
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "produto")
    private List<Voto> votos;
    private long totalCorreto;
    private long totalIncorreto;
    @Version
    private long version;

    public Produto() {
        this.votos = new ArrayList<>();
    }

    public Produto(String nome, double valor, Mercado mercado, Usuario usuario) {
        this.nome = nome;
        this.valor = valor;
        this.mercado = mercado;
        this.usuario = usuario;
        this.votos = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public Mercado getMercado() {
        return mercado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public long getTotalCorreto() {
        return totalCorreto;
    }

    public long getTotalIncorreto() {
        return totalIncorreto;
    }

    public long getVersion() {
        return version;
    }

    public void setTotalCorreto(long totalCorreto) {
        this.totalCorreto = totalCorreto;
    }

    public void setTotalIncorreto(long totalIncorreto) {
        this.totalIncorreto = totalIncorreto;
    }
}
