package br.com.giovanni.projetotg.model;

import jakarta.persistence.*;

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

    public Produto() {
    }

    public Produto(String nome, double valor, Mercado mercado, Usuario usuario) {
        this.nome = nome;
        this.valor = valor;
        this.mercado = mercado;
        this.usuario = usuario;
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

    @Override
    public String toString() {
        return "id: " + getId() + " " + getNome() + " (R$ " + getValor() + ") - Mercado: " + getMercado().getNome() + " - Usuário: " + getUsuario();
    }
}
