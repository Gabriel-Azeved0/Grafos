package lib;

import java.util.ArrayList;

public class Vertice<T> {

    private T valor;
    private ArrayList<Aresta<T>> arestas;

    public Vertice(T valor) {
        this.valor = valor;
        this.arestas = new ArrayList<>();
    }

    public T getValor() {
        return valor;
    }

    public void adicionarAresta(Aresta<T> aresta) {
        this.arestas.add(aresta);
    }

    public ArrayList<Aresta<T>> getArestas() {
        return arestas;
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
