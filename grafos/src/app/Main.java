package app;

import lib.Aresta;
import lib.Grafo;
import lib.Vertice;
import app.Encomenda;
import java.util.ArrayList;
import java.util.List;


import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Grafo<String> grafo = new Grafo<>();
        List<Encomenda> encomendas = new ArrayList<>();
        int opcao;


        do {
            System.out.println("\n=== SISTEMA DE LOGÍSTICA DE ENTREGAS ===");
            System.out.println("1) Carregar rotas de um arquivo");
            System.out.println("2) Cadastrar rota manualmente");
            System.out.println("3) Exibir Busca em Largura (BFS)");
            System.out.println("4) Cadastrar encomendas a serem entregues");
            System.out.println("5) Calcular rota da encomenda");
            System.out.println("6) Calcular Árvore Geradora Mínima (Prim)");
            System.out.println("7) Calcular menor caminho (Dijkstra)");
            System.out.println("8) Exibir Grafo");
            System.out.println("0) Sair");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Digite um número válido.");
                scanner.nextLine();
            }
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1 -> {
                    System.out.print("Informe o caminho do arquivo de rotas: ");
                    String caminho = scanner.nextLine().trim();
                    grafo.carregarRotasDeArquivo(caminho);
                }
                case 2 -> {
                    System.out.print("Origem: ");
                    String origem = scanner.nextLine().trim();

                    System.out.print("Destino: ");
                    String destino = scanner.nextLine().trim();

                    System.out.print("Distância (km): ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Digite um número inteiro.");
                        scanner.nextLine();
                    }
                    int distancia = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("A aresta é direcional? (true/false): ");
                    boolean direcional = scanner.nextBoolean();
                    scanner.nextLine();

                    grafo.cadastrarRota(origem, destino, distancia, direcional);

                    System.out.println("✔ Rota registrada.");
                }
                case 3 -> {
                    if (grafo.vazio()) {
                        System.out.println("\n⚠ Nenhuma rota cadastrada.");
                    } else {
                        System.out.println("\n=== BUSCA EM LARGURA (BFS) ===");
                        System.out.print("Cidade de origem da busca: ");
                        String origem = scanner.nextLine().trim();
                        grafo.buscaEmLargura(origem);
                    }
                }
                case 4 -> {
                    if (grafo.vazio()) {
                        System.out.println("\n⚠ Cadastre ou carregue rotas antes de registrar encomendas.");
                    } else {
                        System.out.print("Descrição do produto: ");
                        String produto = scanner.nextLine().trim();

                        System.out.print("Cidade de origem: ");
                        String origem = scanner.nextLine().trim();

                        System.out.print("Cidade de destino: ");
                        String destino = scanner.nextLine().trim();

                        int id = encomendas.size() + 1;
                        encomendas.add(new Encomenda(id, produto, origem, destino));

                        System.out.println("✔ Encomenda cadastrada com ID " + id + ".");
                    }
                }
                case 5 -> {
                    if (encomendas.isEmpty()) {
                        System.out.println("\n⚠ Nenhuma encomenda cadastrada.");
                    } else if (grafo.vazio()) {
                        System.out.println("\n⚠ Nenhuma rota cadastrada no grafo.");
                    } else {
                        System.out.println("\n=== ENCOMENDAS CADASTRADAS ===");
                        for (Encomenda e : encomendas) {
                            System.out.println(e);
                        }

                        System.out.print("Informe o ID da encomenda para calcular a rota: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Digite um número inteiro.");
                            scanner.nextLine();
                        }

                        int idEscolhido = scanner.nextInt();
                        scanner.nextLine();

                        Encomenda selecionada = null;
                        for (Encomenda e : encomendas) {
                            if (e.getId() == idEscolhido) {
                                selecionada = e;
                                break;
                            }
                        }

                        if (selecionada == null) {
                            System.out.println("ID de encomenda inválido.");
                        } else {
                            grafo.menorCaminhoEntre(
                                    selecionada.getOrigem(),
                                    selecionada.getDestino()
                            );
                        }
                    }
                }
                case 6 -> {
                    if (grafo.vazio()) System.out.println("\n⚠ Nenhuma rota cadastrada.");
                    else {
                        System.out.println("\n=== Árvore Geradora Mínima (Prim) ===");
                        var mst = grafo.prim();

                        int total = 0;

                        for (Aresta<String> a : mst) {

                            String origem = "?";
                            for (Vertice<String> v : grafo.getVertices()) {
                                if (v.getArestas().contains(a)) {
                                    origem = v.getValor();
                                    break;
                                }
                            }

                            System.out.println(
                                    origem + " -- " + a.getPeso() + " --> " +
                                            a.getDestino().getValor()
                            );

                            total += a.getPeso();
                        }

                        System.out.println("Peso total da AGM: " + total);
                    }
                }
                case 7 -> {
                    if (grafo.vazio()) System.out.println("\n⚠ Nenhuma rota cadastrada.");
                    else {
                        System.out.print("Cidade de origem: ");
                        String origem = scanner.nextLine().trim();
                        grafo.dijkstra(origem);
                    }
                }
                case 8 -> {
                    if (grafo.vazio()) System.out.println("\n⚠ Nenhuma rota cadastrada.");
                    else grafo.exibirGrafo();
                }
                case 0 -> System.out.println("Encerrando o sistema...");

                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}
