package br.com.giovanni.projetotg.dto;

public record NovoProdutoDtoRequest(
        String nome,
        Double valor,
        long idMercado
) {
}
