package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.dto.MercadoDtoResponse;
import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.service.GerenciaMercados;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mercados")
public class MercadoController {
    private final GerenciaMercados gerenciaMercados;

    public MercadoController(GerenciaMercados gerenciaMercados) {
        this.gerenciaMercados = gerenciaMercados;
    }

    @GetMapping
    public List<MercadoDtoResponse> getMercados(@RequestParam(required = false, name = "name") String nome) {
        return gerenciaMercados.getMercados(nome);
    }

    @PostMapping
    public ResponseEntity<MercadoDtoResponse> novoMercado(@RequestBody Mercado mercado) {
        MercadoDtoResponse mercadoCadastrado = gerenciaMercados.novoMercado(mercado);
        return ResponseEntity.status(HttpStatus.CREATED).body(mercadoCadastrado); // Se for dar deploy no projeto precisa arrumar a segurança das rotas e provavelmente de mais coisas do projeto.
    }

    @PatchMapping("/{id}")
    public MercadoDtoResponse editarMercado(@PathVariable long id, @RequestBody Mercado mercado) {
        MercadoDtoResponse mercadoEditado = gerenciaMercados.editarMercado(id, mercado.getNome(), mercado.getEndereco());
        return mercadoEditado;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MercadoDtoResponse> deleteMercado(@PathVariable long id) {
        gerenciaMercados.removerMercado(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public MercadoDtoResponse buscarMercadoPorId(@PathVariable long id) {
        MercadoDtoResponse mercadoBuscado = gerenciaMercados.buscaMercadoPorId(id);
        return mercadoBuscado;
    }
}
