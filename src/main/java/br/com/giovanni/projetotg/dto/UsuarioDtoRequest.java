package br.com.giovanni.projetotg.dto;

public record UsuarioDtoRequest(
        String nome,
        String email,
        String senha
) {
}
