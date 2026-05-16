package br.com.giovanni.projetotg.model;

public class Produto {
    private String nome;
    private double valor;
    private Mercado mercado;

    public Produto(String nome, double valor, Mercado mercado) {
        this.nome = nome;
        this.valor = valor;
        this.mercado = mercado;
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

    @Override
    public String toString() {
        return getNome() + " (R$ " + getValor() + "): " + getMercado().getNome();
    }
}
