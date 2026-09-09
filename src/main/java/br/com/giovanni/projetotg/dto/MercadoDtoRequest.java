package br.com.giovanni.projetotg.dto;

import br.com.giovanni.projetotg.enums.Cidades;
import br.com.giovanni.projetotg.enums.Estados;

public record MercadoDtoRequest(
        String nome,
        String rua,
        Integer numero,
        String bairro,
        Cidades cidade,
        Estados estado,
        String cep
) {
}
