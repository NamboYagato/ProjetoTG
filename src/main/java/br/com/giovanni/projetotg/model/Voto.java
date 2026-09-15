package br.com.giovanni.projetotg.model;

import br.com.giovanni.projetotg.enums.Votos;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "produto_id"}))
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public void setVotos(Votos votos) {
        this.votos = votos;
    }
}
