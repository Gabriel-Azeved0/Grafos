package lib;

public class Aresta<T> {

    private Vertice<T> destino;
    private int peso;
    private boolean direcional;

    public Aresta(Vertice<T> destino, int peso, boolean direcional) {
        this.destino = destino;
        this.peso = peso;
        this.direcional = direcional;
    }

    public Vertice<T> getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    public boolean isDirecional() {
        return direcional;
    }

    @Override
    public String toString() {
        return "-> " + destino.getValor()
                + " (peso: " + peso
                + ", dir: " + direcional + ")";
    }


}
