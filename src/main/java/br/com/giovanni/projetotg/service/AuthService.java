package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String authUser(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new EntityNotFoundException("email ou senha inválidos"));
        if (passwordEncoder.matches(password, usuario.getPassword())) {
            return jwtService.tokenGenerator(usuario);
        } else {
            throw new BadCredentialsException("email ou senha inválidos");
        }
    }
}
