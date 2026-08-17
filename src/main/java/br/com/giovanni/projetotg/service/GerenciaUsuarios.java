package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.dto.UsuarioDto;
import br.com.giovanni.projetotg.dto.UsuarioDtoSummary;
import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GerenciaUsuarios {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public GerenciaUsuarios(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioDtoSummary> getUsuarios(String nome) {
        List<Usuario> usuarios;
        if (nome != null && !nome.isBlank()) {
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
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return new UsuarioDto(usuario.getNome(), usuario.getEmail(), usuario.getId());
    }

    public UsuarioDto editarUsuario(long id, String nome, String email, String password) {
        String contextHolderEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        if (!usuario.getEmail().equalsIgnoreCase(contextHolderEmail)) {
            throw new AccessDeniedException("Você não tem permissão para editar este usuário");
        }
        if (nome != null && !nome.isBlank()) {
            usuario.setNome(nome);
        }
        if (email != null && !email.isBlank()) {
            usuario.setEmail(email);
        }
        if (password != null && !password.isBlank()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }
        usuarioRepository.save(usuario);
        return new UsuarioDto(usuario.getNome(), usuario.getEmail(), usuario.getId());
    }

    public void deletarUsuario(long id) {
        String contextHolderEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        if (!usuario.getEmail().equalsIgnoreCase(contextHolderEmail)) {
            throw new AccessDeniedException("Você não tem permissão para deletar este usuário");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioDtoSummary buscarUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        return new UsuarioDtoSummary(usuario.getNome(), usuario.getId());
    }
}
