package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.dto.MercadoDtoRequest;
import br.com.giovanni.projetotg.dto.MercadoDtoResponse;
import br.com.giovanni.projetotg.dto.ProdutoDtoSummary;
import br.com.giovanni.projetotg.enums.Cidades;
import br.com.giovanni.projetotg.enums.Estados;
import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.repository.MercadoRepository;
import br.com.giovanni.projetotg.specification.MercadoSpecification;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GerenciaMercados {
    private final MercadoRepository mercadoRepository;

    public GerenciaMercados(MercadoRepository mercadoRepository) {
        this.mercadoRepository = mercadoRepository;
    }

    public List<MercadoDtoResponse> getMercados(String nome, Cidades cidade, Estados estado) {
        List<MercadoDtoResponse> response;
        PredicateSpecification<Mercado> predicateSpecification = MercadoSpecification.onlyNome(nome).and(MercadoSpecification.onlyCidade(cidade)).and(MercadoSpecification.onlyEstado(estado));
        List<Mercado> mercados = mercadoRepository.findAll(predicateSpecification);
        response = mercados.stream().map(mercado -> new MercadoDtoResponse(mercado.getNome(), mercado.getRua(), mercado.getNumero(), mercado.getBairro(), mercado.getCidade(), mercado.getEstado(), mercado.getCep(), mercado.getId(), mercado.getProdutos().stream().map(p -> new ProdutoDtoSummary(p.getNome(), p.getValor(), p.getId())).collect(Collectors.toList()))).collect(Collectors.toList());
        return response;
    }

    public MercadoDtoResponse novoMercado(MercadoDtoRequest mercadoDtoRequest) {
        Optional<Mercado> mercadoOptional = mercadoRepository.findByCepAndNumero(mercadoDtoRequest.cep(), mercadoDtoRequest.numero());
        if (mercadoOptional.isPresent()) {
            throw new EntityExistsException("Um mercado com esse número e cep já existe!");
        } else {
            Mercado mercado = new Mercado(mercadoDtoRequest.nome(), mercadoDtoRequest.rua(), mercadoDtoRequest.numero(), mercadoDtoRequest.bairro(), mercadoDtoRequest.cidade(), mercadoDtoRequest.estado(), mercadoDtoRequest.cep());
            mercadoRepository.save(mercado);
            return new MercadoDtoResponse(mercado.getNome(), mercado.getRua(), mercado.getNumero(), mercado.getBairro(), mercado.getCidade(), mercado.getEstado(), mercado.getCep(), mercado.getId(), mercado.getProdutos().stream().map(p -> new ProdutoDtoSummary(p.getNome(), p.getValor(), p.getId())).collect(Collectors.toList()));
        }
    }

    public MercadoDtoResponse editarMercado(long id, MercadoDtoRequest mercadoDtoRequest) {
        Mercado mercado = mercadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!"));
        Optional<Mercado> mercadoOptional = mercadoRepository.findByCepAndNumero(mercadoDtoRequest.cep(), mercadoDtoRequest.numero());
        if (mercadoOptional.isPresent()) {
            if (mercadoOptional.get().getId() != mercado.getId()) {
                throw new EntityExistsException("Um mercado com esse número e cep já existe!");
            }
        }
        if (mercadoOptional.isEmpty() || mercadoOptional.get().getId() == mercado.getId()) {
            if (mercadoDtoRequest.nome() != null && !mercadoDtoRequest.nome().isBlank()) {
                mercado.setNome(mercadoDtoRequest.nome());
            }
            if (mercadoDtoRequest.rua() != null && !mercadoDtoRequest.rua().isBlank()) {
                mercado.setRua(mercadoDtoRequest.rua());
            }
            if (mercadoDtoRequest.numero() != null && mercadoDtoRequest.numero() != 0) {
                mercado.setNumero(mercadoDtoRequest.numero());
            }
            if (mercadoDtoRequest.bairro() != null && !mercadoDtoRequest.bairro().isBlank()) {
                mercado.setBairro(mercadoDtoRequest.bairro());
            }
            if (mercadoDtoRequest.cidade() != null) {
                mercado.setCidade(mercadoDtoRequest.cidade());
            }
            if (mercadoDtoRequest.estado() != null) {
                mercado.setEstado(mercadoDtoRequest.estado());
            }
            if (mercadoDtoRequest.cep() != null && !mercadoDtoRequest.cep().isBlank()) {
                mercado.setCep(mercadoDtoRequest.cep());
            }
            mercadoRepository.save(mercado);
        }
        return new MercadoDtoResponse(mercado.getNome(), mercado.getRua(), mercado.getNumero(), mercado.getBairro(), mercado.getCidade(), mercado.getEstado(), mercado.getCep(), mercado.getId(), mercado.getProdutos().stream().map(p -> new ProdutoDtoSummary(p.getNome(), p.getValor(), p.getId())).collect(Collectors.toList()));
    }

    public void removerMercado(long id) {
        mercadoRepository.deleteById(id);
    }

    public MercadoDtoResponse buscaMercadoPorId(long id) {
        Mercado mercado = mercadoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mercado não encontrado!"));
        return new MercadoDtoResponse(mercado.getNome(), mercado.getRua(), mercado.getNumero(), mercado.getBairro(), mercado.getCidade(), mercado.getEstado(), mercado.getCep(), mercado.getId(), mercado.getProdutos().stream().map(p -> new ProdutoDtoSummary(p.getNome(), p.getValor(), p.getId())).collect(Collectors.toList()));
    }
}
