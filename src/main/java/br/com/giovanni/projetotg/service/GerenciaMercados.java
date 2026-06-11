package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.repository.MercadoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GerenciaMercados {
    private final MercadoRepository mercadoRepository;

    public GerenciaMercados(MercadoRepository mercadoRepository) {
        this.mercadoRepository = mercadoRepository;
    }

    public List<Mercado> getMercados() {
        return mercadoRepository.findAll();
    }

    public Mercado novoMercado(Mercado mercado) {
        return mercadoRepository.save(mercado);
    }

    public Mercado editarMercado(long id, String nome, String endereco) {
        Mercado mercado;
        Optional<Mercado> optionalMercado = mercadoRepository.findById(id);
        if (optionalMercado.isPresent()) {
            mercado = optionalMercado.get();
            if (!nome.isBlank()) {
                mercado.setNome(nome);
            }
            if (!endereco.isBlank()) {
                mercado.setEndereco(endereco);
            }
            return mercadoRepository.save(mercado);
        } else {
            throw new EntityNotFoundException("Mercado não encontrado!");
        }
    }

    public void removerMercado(long id) {
        mercadoRepository.deleteById(id);
    }

    public Mercado buscaMercado(long id) {
        return mercadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!"));
    }
}
