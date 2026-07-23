package br.com.giovanni.projetotg.dto;

import br.com.giovanni.projetotg.model.Produto;

import java.util.List;

public record MercadoDtoResponse(
        String nome,
        String endereco,
        long id,
        List<ProdutoDtoSummary> produtos
) {
}
