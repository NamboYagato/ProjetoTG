package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.dto.UsuarioDtoRequest;
import br.com.giovanni.projetotg.dto.UsuarioDtoResponse;
import br.com.giovanni.projetotg.dto.UsuarioDtoSummary;
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
    public ResponseEntity<UsuarioDtoResponse> cadastraUsuario(@RequestBody UsuarioDtoRequest usuarioDtoRequest) {
        UsuarioDtoResponse usuarioCadastrado = gerenciaUsuarios.novoUsuario(usuarioDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }

    @PatchMapping
    public UsuarioDtoResponse editarUsuario(@RequestBody UsuarioDtoRequest usuarioDtoRequest) {
        return gerenciaUsuarios.editarUsuario(usuarioDtoRequest.nome(), usuarioDtoRequest.email(), usuarioDtoRequest.senha());
    }

    @DeleteMapping
    public ResponseEntity deleteUsuario() {
        gerenciaUsuarios.deletarUsuario();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public UsuarioDtoSummary buscaUsuarioPorId(@PathVariable long id) {
        return gerenciaUsuarios.buscarUsuario(id);
    }
}
