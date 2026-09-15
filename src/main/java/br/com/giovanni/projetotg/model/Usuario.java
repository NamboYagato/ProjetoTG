package br.com.giovanni.projetotg.model;

import br.com.giovanni.projetotg.enums.Papeis;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "usuario")
    private List<Produto> produtos;
    @Enumerated(EnumType.STRING)
    private Papeis papel;

    public Usuario() {
        this.produtos = new ArrayList<>();
    }

    public Usuario(String nome, String email, String password) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.produtos = new ArrayList<>();
        this.papel = Papeis.USER;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public Papeis getPapel() {
        return papel;
    }

    @PreRemove
    public void setUsuarioIdNullOnDelete() {
        produtos.forEach(p -> p.setUsuario(null));
    }

}
