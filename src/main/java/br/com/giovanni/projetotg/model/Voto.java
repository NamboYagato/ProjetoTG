package br.com.giovanni.projetotg.model;

import br.com.giovanni.projetotg.enums.Votos;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "produto_id"}))
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Produto produto;
    @Enumerated(EnumType.STRING)
    private Votos votos;

    public Voto() {
    }

    public Voto(Usuario usuario, Produto produto, Votos votos) {
        this.usuario = usuario;
        this.produto = produto;
        this.votos = votos;
    }

    public long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public Votos getVotos() {
        return votos;
    }
}
