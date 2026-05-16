package br.com.giovanni.projetotg;

import br.com.giovanni.projetotg.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoTgApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoTgApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main();
        main.exibeMenu();
    }
}
