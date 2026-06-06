package br.com.giovanni.projetotg;

import br.com.giovanni.projetotg.repository.MercadoRepository;
import br.com.giovanni.projetotg.repository.ProdutoRepository;
import br.com.giovanni.projetotg.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoTgApplication implements CommandLineRunner {
    private MercadoRepository mercadoRepository;
    private ProdutoRepository produtoRepository;
    private UsuarioRepository usuarioRepository;
    @Autowired
    public ProjetoTgApplication(MercadoRepository mercadoRepository, ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.mercadoRepository = mercadoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjetoTgApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
