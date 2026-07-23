package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.dto.UsuarioDto;
import br.com.giovanni.projetotg.dto.UsuarioDtoSummary;
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
    public List<UsuarioDtoSummary> getUsuarios(@RequestParam(required = false, name = "name") String nome) {
        return gerenciaUsuarios.getUsuarios(nome);
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> cadastraUsuario(@RequestBody Usuario usuario) {
        UsuarioDto usuarioCadastrado = gerenciaUsuarios.novoUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }

    @PatchMapping("/{id}")
    public UsuarioDto editarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        UsuarioDto usuarioEditado = gerenciaUsuarios.editarUsuario(id, usuario.getNome(), usuario.getEmail(), usuario.getPassword());
        return usuarioEditado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUsuario(@PathVariable long id) {
        gerenciaUsuarios.deletarUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public UsuarioDto buscaUsuarioPorId(@PathVariable long id) {
        UsuarioDto usuarioBuscado = gerenciaUsuarios.buscarUsuario(id);
        return usuarioBuscado;
    }
}
