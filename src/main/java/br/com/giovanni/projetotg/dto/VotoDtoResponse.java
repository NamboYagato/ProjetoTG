package br.com.giovanni.projetotg.dto;

import br.com.giovanni.projetotg.enums.Votos;

public record VotoDtoResponse(
        Votos votos,
        Long totalCorreto,
        Long totalIncorreto
) {
}
