package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.dto.UsuarioDtoRequest;
import br.com.giovanni.projetotg.dto.UsuarioDtoResponse;
import br.com.giovanni.projetotg.dto.UsuarioDtoSummary;
import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        List<UsuarioDtoSummary> response = usuarios.stream().map(u -> new UsuarioDtoSummary(u.getNome())).collect(Collectors.toList());
        return response;
    }

    public UsuarioDtoResponse novoUsuario(UsuarioDtoRequest usuarioDtoRequest) {
        Optional<Usuario> usuarioJaExiste = usuarioRepository.findByEmailIgnoreCase(usuarioDtoRequest.email());
        if (usuarioJaExiste.isPresent()) {
            throw new EntityExistsException("Esse email já está em uso!");
        } else {
            Usuario usuario = new Usuario(usuarioDtoRequest.nome(), usuarioDtoRequest.email(), usuarioDtoRequest.senha());
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioRepository.save(usuario);
            return new UsuarioDtoResponse(usuario.getNome(), usuario.getEmail());
        }
    }

    public UsuarioDtoResponse editarUsuario(String nome, String email, String password) {
        String contextHolderEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(contextHolderEmail).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        Optional<Usuario> usuarioJaExiste = usuarioRepository.findByEmailIgnoreCase(email);
        if (usuarioJaExiste.isPresent()) {
            if (!usuarioJaExiste.get().getEmail().equals(contextHolderEmail)) {
                throw new EntityExistsException("Esse email já está em uso!");
            }
        }
        if (usuarioJaExiste.isEmpty() || usuarioJaExiste.get().getEmail().equalsIgnoreCase(contextHolderEmail)) {
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
        }
        return new UsuarioDtoResponse(usuario.getNome(), usuario.getEmail());
    }

    public void deletarUsuario() {
        String contextHolderEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(contextHolderEmail).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        usuarioRepository.deleteById(usuario.getId());
    }

    public UsuarioDtoSummary buscarUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
        return new UsuarioDtoSummary(usuario.getNome());
    }
}
