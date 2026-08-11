package br.com.giovanni.projetotg.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginDtoResponse(
        String token,
        String message
) {
}
