package br.com.giovanni.projetotg.controller;

import br.com.giovanni.projetotg.dto.MercadoDtoRequest;
import br.com.giovanni.projetotg.dto.MercadoDtoResponse;
import br.com.giovanni.projetotg.dto.MercadoDtoSearchFilter;
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
    public List<MercadoDtoResponse> getMercados(MercadoDtoSearchFilter mercadoDtoSearchFilter) {
        return gerenciaMercados.getMercados(mercadoDtoSearchFilter.nome(), mercadoDtoSearchFilter.cidade(), mercadoDtoSearchFilter.estado());
    }

    @PostMapping
    public ResponseEntity<MercadoDtoResponse> novoMercado(@RequestBody MercadoDtoRequest mercadoDtoRequest) {
        MercadoDtoResponse mercadoCadastrado = gerenciaMercados.novoMercado(mercadoDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(mercadoCadastrado);
    }

    @PatchMapping("/{id}")
    public MercadoDtoResponse editarMercado(@PathVariable long id, @RequestBody MercadoDtoRequest mercadoDtoRequest) {
        return gerenciaMercados.editarMercado(id, mercadoDtoRequest);
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
