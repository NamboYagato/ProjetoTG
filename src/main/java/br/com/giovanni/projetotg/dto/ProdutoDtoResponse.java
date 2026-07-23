package br.com.giovanni.projetotg.dto;

public record ProdutoDtoResponse(
        String nome,
        double valor,
        long id,
        MercadoDtoSummary mercado,
        UsuarioDtoSummary usuario
) {
}
