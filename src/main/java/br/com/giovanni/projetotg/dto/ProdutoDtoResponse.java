package br.com.giovanni.projetotg.dto;

public record ProdutoDtoResponse(
        String nome,
        Double valor,
        long id,
        MercadoDtoSummary mercado,
        UsuarioDtoSummary usuario,
        long totalCorreto,
        long totalIncorreto
) {
}
