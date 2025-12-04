package lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Grafo<T> {

    private ArrayList<Vertice<T>> vertices = new ArrayList<>();
    private String caminhoArquivoRotas;

    // =====================================================
    //                      VÉRTICES
    // =====================================================

    public Vertice<T> adicionarVertice(T valor) {
        Vertice<T> existente = buscarVertice(valor);
        if (existente != null) return existente;

        Vertice<T> novo = new Vertice<>(valor);
        this.vertices.add(novo);
        return novo;
    }

    private Vertice<T> buscarVertice(T valor) {
        for (Vertice<T> v : vertices) {
            if (Objects.equals(v.getValor(), valor)) return v;
        }
        return null;
    }

    public boolean vazio() {
        return vertices.isEmpty();
    }

    // =====================================================
    //                       ARESTAS
    // =====================================================

    public void adicionarAresta(T origem, T destino, int peso, boolean direcional) {

        Vertice<T> vOrigem = adicionarVertice(origem);
        Vertice<T> vDestino = adicionarVertice(destino);

        // origem → destino
        Aresta<T> a = new Aresta<>(vDestino, peso, direcional);
        vOrigem.adicionarAresta(a);

        // se for bidirecional, cria a reversa
        if (!direcional) {
            Aresta<T> r = new Aresta<>(vOrigem, peso, false);
            vDestino.adicionarAresta(r);
        }
    }

    // =====================================================
    //                       BFS
    // =====================================================


    public void buscaEmLargura(T origem) {

        Vertice<T> inicial = buscarVertice(origem);
        if (inicial == null) {
            System.out.println("Vértice não encontrado: " + origem);
            return;
        }

        Set<Vertice<T>> visitados = new HashSet<>();
        Queue<Vertice<T>> fila = new LinkedList<>();

        visitados.add(inicial);
        fila.add(inicial);

        System.out.println("BFS a partir de: " + inicial.getValor());

        while (!fila.isEmpty()) {

            Vertice<T> atual = fila.poll();
            System.out.println(atual.getValor());

            for (Aresta<T> a : atual.getArestas()) {
                Vertice<T> viz = a.getDestino();
                if (!visitados.contains(viz)) {
                    visitados.add(viz);
                    fila.add(viz);
                }
            }
        }
    }


    // =====================================================
    //                        PRIM
    // =====================================================

    public ArrayList<Aresta<T>> prim() {

        ArrayList<Aresta<T>> mst = new ArrayList<>();
        HashSet<Vertice<T>> visitados = new HashSet<>();

        PriorityQueue<Aresta<T>> fila =
                new PriorityQueue<>(Comparator.comparingInt(Aresta::getPeso));

        for (Vertice<T> inicial : vertices) {

            if (!visitados.add(inicial)) continue;

            fila.addAll(inicial.getArestas());

            while (!fila.isEmpty()) {

                Aresta<T> menor = fila.poll();
                Vertice<T> destino = menor.getDestino();

                if (!visitados.add(destino)) continue;

                mst.add(menor);

                for (Aresta<T> a : destino.getArestas()) {
                    fila.add(a);
                }
            }
        }

        return mst;
    }

    // =====================================================
    //                    DIJKSTRA
    // =====================================================

    public void dijkstra(T origem) {

        Vertice<T> inicial = buscarVertice(origem);
        if (inicial == null) {
            System.out.println("Vértice não encontrado.");
            return;
        }

        Map<Vertice<T>, Integer> dist = new HashMap<>();
        Map<Vertice<T>, Vertice<T>> pai = new HashMap<>();

        for (Vertice<T> v : vertices) {
            dist.put(v, Integer.MAX_VALUE);
            pai.put(v, null);
        }

        dist.put(inicial, 0);

        PriorityQueue<Vertice<T>> fila =
                new PriorityQueue<>(Comparator.comparingInt(dist::get));

        fila.add(inicial);

        while (!fila.isEmpty()) {

            Vertice<T> atual = fila.poll();

            for (Aresta<T> a : atual.getArestas()) {

                Vertice<T> viz = a.getDestino();
                int peso = a.getPeso();

                int novaDist = dist.get(atual) + peso;

                if (novaDist < dist.get(viz)) {
                    dist.put(viz, novaDist);
                    pai.put(viz, atual);
                    fila.add(viz);
                }
            }
        }

        System.out.println("\nMenores distâncias a partir de " + origem + ":");

        for (Vertice<T> v : vertices) {

            int custo = dist.get(v);

            if (custo == Integer.MAX_VALUE) {
                System.out.println("Destino: " + v.getValor() + " | Inacessível");
                continue;
            }

            System.out.print("Destino: " + v.getValor() + " | Custo: " + custo + " | Caminho: ");

            Stack<T> caminho = new Stack<>();
            Vertice<T> atual = v;

            while (atual != null) {
                caminho.push(atual.getValor());
                atual = pai.get(atual);
            }

            while (!caminho.isEmpty()) {
                System.out.print(caminho.pop());
                if (!caminho.isEmpty()) System.out.print(" -> ");
            }

            System.out.println();
        }
    }

    // =====================================================
    //                    EXIBIR GRAFO
    // =====================================================

    public void exibirGrafo() {
        if (vertices.isEmpty()) {
            System.out.println("Grafo vazio.");
            return;
        }

        System.out.println("\n=== GRAFO ===");

        for (Vertice<T> v : vertices) {
            System.out.print(v.getValor() + " -> ");

            if (v.getArestas().isEmpty()) {
                System.out.println("(sem conexões)");
                continue;
            }

            for (Aresta<T> a : v.getArestas()) {
                System.out.print(a.getDestino().getValor() +
                        " (peso: " + a.getPeso() +
                        ", dir: " + a.isDirecional() + ") | ");
            }

            System.out.println();
        }
    }

    // =====================================================
    //                     ARQUIVOS
    // =====================================================

    public void cadastrarRota(T origem, T destino, int peso, boolean direcional) {

        adicionarAresta(origem, destino, peso, direcional);

        if (caminhoArquivoRotas == null)
            caminhoArquivoRotas = "rotas_logistica.txt";

        try (FileWriter fw = new FileWriter(caminhoArquivoRotas, true)) {
            fw.write(origem + " " + destino + " " + peso + " " + direcional + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo.");
        }
    }

    public void carregarRotasDeArquivo(String caminho) {

        vertices.clear();
        caminhoArquivoRotas = caminho;

        try (Scanner sc = new Scanner(new File(caminho))) {

            while (sc.hasNextLine()) {

                String linha = sc.nextLine().trim();
                if (linha.isEmpty() || linha.startsWith("#")) continue;

                String[] p = linha.split("\\s+");
                if (p.length < 4) {
                    System.out.println("Linha ignorada: " + linha);
                    continue;
                }

                T origem = (T) p[0];
                T destino = (T) p[1];
                int peso = Integer.parseInt(p[2]);
                boolean direcional = Boolean.parseBoolean(p[3]);

                this.adicionarAresta(origem, destino, peso, direcional);
            }

            System.out.println("✔ Rotas carregadas com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }

    public ArrayList<Vertice<T>> getVertices() {
        return vertices;
    }

    public void menorCaminhoEntre(T origem, T destino) {

        Vertice<T> inicial = buscarVertice(origem);
        Vertice<T> alvo = buscarVertice(destino);

        if (inicial == null || alvo == null) {
            System.out.println("Origem ou destino não existem no grafo.");
            return;
        }

        Map<Vertice<T>, Integer> dist = new HashMap<>();
        Map<Vertice<T>, Vertice<T>> pai = new HashMap<>();

        for (Vertice<T> v : vertices) {
            dist.put(v, Integer.MAX_VALUE);
            pai.put(v, null);
        }

        dist.put(inicial, 0);

        PriorityQueue<Vertice<T>> fila =
                new PriorityQueue<>(Comparator.comparingInt(dist::get));

        fila.add(inicial);

        while (!fila.isEmpty()) {

            Vertice<T> atual = fila.poll();
            if (atual == alvo) break;

            for (Aresta<T> a : atual.getArestas()) {

                Vertice<T> viz = a.getDestino();
                int peso = a.getPeso();

                int novaDist = dist.get(atual) + peso;

                if (novaDist < dist.get(viz)) {
                    dist.put(viz, novaDist);
                    pai.put(viz, atual);
                    fila.add(viz);
                }
            }
        }

        int custo = dist.get(alvo);

        if (custo == Integer.MAX_VALUE) {
            System.out.println("Não há caminho de " + origem + " até " + destino + ".");
            return;
        }

        // Reconstrói o caminho até o destino
        Stack<T> caminho = new Stack<>();
        Vertice<T> atual = alvo;

        while (atual != null) {
            caminho.push(atual.getValor());
            atual = pai.get(atual);
        }

        System.out.print("Melhor rota de " + origem + " até " + destino +
                " (custo " + custo + "): ");

        while (!caminho.isEmpty()) {
            System.out.print(caminho.pop());
            if (!caminho.isEmpty()) System.out.print(" -> ");
        }

        System.out.println();
    }



}
