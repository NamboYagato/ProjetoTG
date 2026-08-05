package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.dto.MercadoDtoResponse;
import br.com.giovanni.projetotg.dto.ProdutoDtoSummary;
import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.repository.MercadoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GerenciaMercados {
    private final MercadoRepository mercadoRepository;

    public GerenciaMercados(MercadoRepository mercadoRepository) {
        this.mercadoRepository = mercadoRepository;
    }

    public List<MercadoDtoResponse> getMercados(String nome) {
        List<MercadoDtoResponse> response;
        List<Mercado> mercados;
        if (nome != null) {
            mercados = mercadoRepository.findByNomeContainingIgnoreCase(nome);
        } else {
            mercados = mercadoRepository.findAll();
        }
        response = mercados.stream()
                .map(mercado -> new MercadoDtoResponse(mercado.getNome(), mercado.getEndereco(), mercado.getId(),
                        mercado.getProdutos().stream()
                                .map(p -> new ProdutoDtoSummary(p.getNome(), p.getValor(), p.getId()))
                                .collect(Collectors.toList())
                        ))
                .collect(Collectors.toList());
        return response;
    }

    public MercadoDtoResponse novoMercado(Mercado mercado) {
        mercadoRepository.save(mercado);
        return new MercadoDtoResponse(mercado.getNome(), mercado.getEndereco(), mercado.getId(), mercado.getProdutos().stream()
                .map(p -> new ProdutoDtoSummary(p.getNome(), p.getValor(), p.getId()))
                .collect(Collectors.toList())
        );
    }

    public MercadoDtoResponse editarMercado(long id, String nome, String endereco) {
        Mercado mercado = mercadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!"));
            if (nome != null && !nome.isBlank()) {
                mercado.setNome(nome);
            }
            if (endereco != null && !endereco.isBlank()) {
                mercado.setEndereco(endereco);
            }
            mercadoRepository.save(mercado);
            return new MercadoDtoResponse(mercado.getNome(), mercado.getEndereco(), mercado.getId(), mercado.getProdutos().stream()
                    .map(p -> new ProdutoDtoSummary(p.getNome(), p.getValor(), p.getId()))
                    .collect(Collectors.toList())
            );
    }

    public void removerMercado(long id) {
        mercadoRepository.deleteById(id);
    }

    public MercadoDtoResponse buscaMercadoPorId(long id) {
        Mercado mercado = mercadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!"));
        return new MercadoDtoResponse(mercado.getNome(), mercado.getEndereco(), mercado.getId(), mercado.getProdutos().stream()
                .map(p -> new ProdutoDtoSummary(p.getNome(), p.getValor(), p.getId()))
                .collect(Collectors.toList())
        );
    }
}
