package br.com.giovanni.projetotg.service;

import br.com.giovanni.projetotg.dto.VotoDtoRequest;
import br.com.giovanni.projetotg.dto.VotoDtoResponse;
import br.com.giovanni.projetotg.enums.Votos;
import br.com.giovanni.projetotg.model.Produto;
import br.com.giovanni.projetotg.model.Usuario;
import br.com.giovanni.projetotg.model.Voto;
import br.com.giovanni.projetotg.repository.ProdutoRepository;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import br.com.giovanni.projetotg.repository.VotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class VotoService {
    private final VotoRepository votoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public VotoService(VotoRepository votoRepository, ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.votoRepository = votoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public VotoDtoResponse votar(long produtoId, VotoDtoRequest votoDtoRequest) {
        String contextHolderEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(contextHolderEmail).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        long usuarioId = usuario.getId();
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (produto.getUsuario().getId() == usuarioId) {
            throw new AccessDeniedException("Dono do produto não pode votar");
        }

        if (votoDtoRequest.votos() == null) {
            throw new IllegalArgumentException("Voto não pode ser nulo!");
        }
        Optional<Voto> jaVotou = votoRepository.findByUsuarioIdAndProdutoId(usuarioId, produtoId);

        if (jaVotou.isEmpty()) {
            Voto novoVoto;
            if (votoDtoRequest.votos() == Votos.CERTO) {
                novoVoto = new Voto(usuario, produto, votoDtoRequest.votos());
                produto.setTotalCorreto(produto.getTotalCorreto() + 1);
            } else {
                novoVoto = new Voto(usuario, produto, votoDtoRequest.votos());
                produto.setTotalIncorreto(produto.getTotalIncorreto() + 1);
            }
            votoRepository.save(novoVoto);
            produtoRepository.save(produto);
            return new VotoDtoResponse(novoVoto.getVotos(), produto.getTotalCorreto(), produto.getTotalIncorreto());
        }

        // Se os votos forem iguais, remover o voto e atualizar valores
        if (votoDtoRequest.votos() == jaVotou.get().getVotos()) {
            if (votoDtoRequest.votos() == Votos.CERTO) {
                produto.setTotalCorreto(produto.getTotalCorreto() - 1);

            } else {
                produto.setTotalIncorreto(produto.getTotalIncorreto() - 1);
            }
            votoRepository.delete(jaVotou.get());
            produtoRepository.save(produto);
            return new VotoDtoResponse(null, produto.getTotalCorreto(), produto.getTotalIncorreto());
        }
        // Ou se os votos forem diferentes, mudar voto e atualizar valores
        else {
            if (votoDtoRequest.votos() == Votos.CERTO) {
                jaVotou.get().setVotos(votoDtoRequest.votos());
                produto.setTotalIncorreto(produto.getTotalIncorreto() - 1);
                produto.setTotalCorreto(produto.getTotalCorreto() + 1);
            }
            if (votoDtoRequest.votos() == Votos.ERRADO) {
                jaVotou.get().setVotos(votoDtoRequest.votos());
                produto.setTotalCorreto(produto.getTotalCorreto() - 1);
                produto.setTotalIncorreto(produto.getTotalIncorreto() + 1);
            }

            votoRepository.save(jaVotou.get());
            produtoRepository.save(produto);
        }
        return new VotoDtoResponse(jaVotou.get().getVotos(), produto.getTotalCorreto(), produto.getTotalIncorreto());
    }
}
