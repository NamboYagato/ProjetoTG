package br.com.giovanni.projetotg.dto;

import br.com.giovanni.projetotg.enums.Cidades;
import br.com.giovanni.projetotg.enums.Estados;

public record MercadoDtoSearchFilter(
        String nome,
        Cidades cidade,
        Estados estado
) {
}
