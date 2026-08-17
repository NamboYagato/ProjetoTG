package br.com.giovanni.projetotg.dto;

public record ProdutoDtoRequest(
        String nome,
        double valor,
        long idMercado
) {
}
