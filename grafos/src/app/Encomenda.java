package app;

public class Encomenda {

    private int id;
    private String produto;
    private String origem;
    private String destino;

    public Encomenda(int id, String produto, String origem, String destino) {
        this.id = id;
        this.produto = produto;
        this.origem = origem;
        this.destino = destino;
    }

    public int getId() {
        return id;
    }

    public String getProduto() {
        return produto;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    @Override
    public String toString() {
        return id + ") " + produto + " | " + origem + " -> " + destino;
    }
}
