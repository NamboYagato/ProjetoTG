package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class GerenciaUsuarios {
    private UsuarioRepository usuarioRepository;

    public GerenciaUsuarios(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public void novoUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void editarUsuario(long id, String nome, String email, String password) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        if (!nome.isBlank()) {
            usuario.setNome(nome);
        }
        if (!email.isBlank()) {
            usuario.setEmail(email);
        }
        if (!password.isBlank()) {
            usuario.setPassword(password);
        }
        usuarioRepository.save(usuario);
    }

    public void deletarUsuario(long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarUsuario(long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
    }
}
