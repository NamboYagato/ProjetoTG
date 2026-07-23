package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.dto.UsuarioDto;
import br.com.giovanni.projetotg.dto.UsuarioDtoSummary;
import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GerenciaUsuarios {
    private final UsuarioRepository usuarioRepository;

    public GerenciaUsuarios(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDtoSummary> getUsuarios(String nome) {
        List<Usuario> usuarios;
        if (nome != null) {
            usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        } else {
            usuarios = usuarioRepository.findAll();
        }
        List<UsuarioDtoSummary> response = usuarios.stream()
                .map(u -> new UsuarioDtoSummary(u.getNome(), u.getId()))
                .collect(Collectors.toList());
        return response;
    }

    public UsuarioDto novoUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return new UsuarioDto(usuario.getNome(), usuario.getEmail(), usuario.getId());
    }

    public UsuarioDto editarUsuario(long id, String nome, String email, String password) {
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
        return new UsuarioDto(usuario.getNome(), usuario.getEmail(), usuario.getId());
    }

    public void deletarUsuario(long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDto buscarUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        return new UsuarioDto(usuario.getNome(), usuario.getEmail(), usuario.getId());
    }
}
