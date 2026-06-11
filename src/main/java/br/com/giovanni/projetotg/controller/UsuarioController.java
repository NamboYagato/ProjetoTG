package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.service.GerenciaUsuarios;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final GerenciaUsuarios gerenciaUsuarios;

    public UsuarioController(GerenciaUsuarios gerenciaUsuarios) {
        this.gerenciaUsuarios = gerenciaUsuarios;
    }

    @GetMapping
    public List<Usuario> getUsuarios() {
        return gerenciaUsuarios.getUsuarios();
    }

    @PostMapping
    public ResponseEntity<Usuario> cadastraUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCadastrado = gerenciaUsuarios.novoUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }

    @PatchMapping("/{id}")
    public Usuario editarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        Usuario usuarioEditado = gerenciaUsuarios.editarUsuario(id, usuario.getNome(), usuario.getEmail(), usuario.getPassword());
        return usuarioEditado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUsuario(@PathVariable long id) {
        gerenciaUsuarios.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public Usuario buscaUsuarioPorId(@PathVariable long id) {
        Usuario usuarioBuscado = gerenciaUsuarios.buscarUsuario(id);
        return usuarioBuscado;
    }
}
