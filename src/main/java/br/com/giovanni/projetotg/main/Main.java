package br.com.giovanni.projetotg.main;

import br.com.giovanni.projetotg.model.Mercado;
import br.com.giovanni.projetotg.model.Produto;
import br.com.giovanni.projetotg.service.GerenciaMercados;

import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private GerenciaMercados mercados = new GerenciaMercados();


    public void exibeMenu() {
        var menuInicial = """
                
                Digite o número para realizar a ação.
                
                1 - Listar mercados
                2 - Buscar mercado
                3 - Adicionar produto
                4 - Buscar por produtos
                

                0 - Sair
                """;
        System.out.println("Olá sejá bem vindo!" + menuInicial);
        int respostaMenuInicial = -1;
        while (respostaMenuInicial != 0) {
            respostaMenuInicial = scanner.nextInt();
            scanner.nextLine();
            switch (respostaMenuInicial) {
                case 1:
                    mercados.getMercados();
                    System.out.println(menuInicial);
                    break;
                case 2:
                    System.out.println("Digite o nome do mercado:");
                    var respostaMenuMercados = scanner.nextLine();
                    System.out.println(mercados.buscaMercado(respostaMenuMercados));
                    System.out.println(menuInicial);
                    break;
                case 3:
                    mercados.getMercados();
                    System.out.println("Digite o número do mercado para adicionar um produto:");
                    var indexMercado = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Digite o nome do produto:");
                    var nomeProduto = scanner.nextLine();
                    System.out.println("Digite o valor do produto:");
                    var valorProduto = scanner.nextDouble();
                    scanner.nextLine();
                    mercados.novoProduto(indexMercado, nomeProduto, valorProduto);
                    System.out.println(menuInicial);
                    break;
                case 4:
                    System.out.println("Digite o nome do produto:");
                    var respostaBuscaProduto = scanner.nextLine();
                    System.out.println(mercados.buscaProdutosPorMercado(respostaBuscaProduto));
                    System.out.println(menuInicial);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Essa ação não existe!");
            }
        }
    }
}
