package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GerenciaUsuarios {
    private final UsuarioRepository usuarioRepository;

    public GerenciaUsuarios(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario novoUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario editarUsuario(long id, String nome, String email, String password) {
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
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarUsuario(long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
    }
}
