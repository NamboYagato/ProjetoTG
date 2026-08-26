package br.com.giovanni.projetotg.dto;

public record ErroDtoResponse(
        String mensagem,
        int status
) {
}
