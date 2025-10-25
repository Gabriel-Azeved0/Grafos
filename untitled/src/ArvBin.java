package untitled.src;

import java.util.Comparator;

public class ArvBin<T>  implements IArvoreBinaria<T> {
    private No <T> raiz;
    private final Comparator<T> comparator;

    public ArvBin(Comparator<T> comparator) {
        this.comparator = comparator;
        this.raiz = null;
    }

    @Override
    public void adicionar(T novoValor) {
        No<T> novoNo = new No<T>(novoValor);
        if (this.raiz == null){
            this.raiz = novoNo;
        }else{
            adicionarInterno(novoNo, this.raiz);
        }
    }

    @Override
    public T pesquisar(T valor) {
        if (this.raiz == null){
            return null;
        }else{
            No<T> noEncontrado = pesquisarInterno(new No<T>(valor), this.raiz);
            if (noEncontrado == null) {
                return null;
            }
            return noEncontrado.getValue();
        }
    }

    @Override
    public T pesquisar(T valor, Comparator comparador) {
        if (this.raiz == null){
            return null;
        }else{
            No<T> noEncontrado = pesquisarInterno(new No<T>(valor), this.raiz, comparador);
            if (noEncontrado == null) {
                return null;
            }
            return noEncontrado.getValue();
        }
    }

    @Override
    public T remover(T valor) {
        if (this.raiz == null) {
            return null;
        }
        No<T> novoNo = new No<>(valor);
        NoRemovido<T> resultado = removerInterno(novoNo, this.raiz);
        this.raiz = resultado.noAtualizado;
        return resultado.valorRemovido;
    }


    @Override
    public int altura() {
        if (this.raiz == null) {
            return -1; // conforme requisito da interface
        }
        return alturaInterno(this.raiz, this.raiz);
    }

    @Override
    public int quantidadeNos() {
        if (this.raiz == null) {
            return 0;
        }
        return quantidadeNosInterno(this.raiz, this.raiz);
    }

    @Override
    public String caminharEmNivel() {
        return "";
    }

    @Override
    public String caminharEmOrdem() {
        if (this.raiz == null) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // mantém o padrão (No<T> novoNo, No<T> noAtual)
        caminharEmOrdemInterno(this.raiz, this.raiz, sb);

        // remove o último separador " \n " se houver elementos
        int n = sb.length();
        if (n >= 3 && sb.charAt(n - 1) == ' ' && sb.charAt(n - 2) == 'n' && sb.charAt(n - 3) == '\\') {
            // caso algum editor escape, ignore. Abaixo, remoção direta do padrão " \n "
        }
        if (n >= 3 && sb.substring(n - 3).equals(" \n ")) {
            sb.setLength(n - 3);
        }

        sb.append(']');
        return sb.toString();
    }


    protected No<T> adicionarInterno(No<T> novoValor, No<T> noAtual){
        int cmp = this.comparator.compare(novoValor.getValue(), noAtual.getValue());
        if(cmp == 0){
            return null;
        }if(cmp > 0){
            if(noAtual.getRight() == null){
                noAtual.setRight(novoValor);
            }else{
                adicionarInterno(novoValor, noAtual.getRight());
            }
        }if(cmp < 0){
            if(noAtual.getLeft() == null){
                noAtual.setLeft(novoValor);
            }else{
                adicionarInterno(novoValor, noAtual.getLeft());
            }
        }
        return null;
    }

    protected No<T> pesquisarInterno(No<T>noBusca, No<T> noAtual){
        if (noAtual == null) {
            return null;
        }
        int cmp = this.comparator.compare(noBusca.getValue(), noAtual.getValue());
        if(cmp == 0){
            return noAtual;
        }if(cmp > 0){
            return pesquisarInterno(noBusca, noAtual.getRight());
        }else{
            return pesquisarInterno(noBusca, noAtual.getLeft());
        }
    }


    protected No<T> pesquisarInterno(No<T>noBusca, No<T> noAtual, Comparator<T> novoComparator){
        if (noAtual == null){
            return null;
        }
        if (novoComparator.compare(noBusca.getValue(), noAtual.getValue()) == 0){
            return noAtual;
        }

        //Buscando à esquerda da raiz primeiro
        No<T> resultado = pesquisarInterno(noBusca, noAtual.getLeft(), novoComparator);
        if (resultado != null){
            return resultado;
        }else{//Buscando à direita da raiz
            return pesquisarInterno(noBusca, noAtual.getRight(), novoComparator);
        }
    }

    protected NoRemovido<T> removerInterno(No<T> novoNo, No<T> noAtual) {
        if (noAtual == null) {
            return new NoRemovido<>(null, null);
        }

        int cmp = comparator.compare(novoNo.getValue(), noAtual.getValue());

        if (cmp < 0) {
            NoRemovido<T> res = removerInterno(novoNo, noAtual.getLeft());
            noAtual.setLeft(res.noAtualizado);
            return new NoRemovido<>(noAtual, res.valorRemovido);
        }

        if (cmp > 0) {
            NoRemovido<T> res = removerInterno(novoNo, noAtual.getRight());
            noAtual.setRight(res.noAtualizado);
            return new NoRemovido<>(noAtual, res.valorRemovido);
        }

        // achou o nó a remover
        T valorRemovido = noAtual.getValue();

        // caso 1: folha
        if (noAtual.getLeft() == null && noAtual.getRight() == null) {
            return new NoRemovido<>(null, valorRemovido);
        }

        // caso 2: um filho
        if (noAtual.getLeft() == null) {
            return new NoRemovido<>(noAtual.getRight(), valorRemovido);
        }
        if (noAtual.getRight() == null) {
            return new NoRemovido<>(noAtual.getLeft(), valorRemovido);
        }

        // caso 3: dois filhos
        No<T> menorDaDireita = encontroMenor(noAtual.getRight());
        noAtual.setValue(menorDaDireita.getValue());

        NoRemovido<T> res = removerInterno(new No<>(menorDaDireita.getValue()), noAtual.getRight());
        noAtual.setRight(res.noAtualizado);
        return new NoRemovido<>(noAtual, valorRemovido);
    }

    protected No<T> encontroMenor(No<T> no) {
        while (no.getLeft() != null) {
            no = no.getLeft();
        }
        return no;
    }

    // classe auxiliar interna
    protected static class NoRemovido<U> {
        No<U> noAtualizado;
        U valorRemovido;
        NoRemovido(No<U> noAtualizado, U valorRemovido) {
            this.noAtualizado = noAtualizado;
            this.valorRemovido = valorRemovido;
        }
    }

    protected void caminharEmOrdemInterno(No<T> novoNo, No<T> noAtual, StringBuilder sb) {
        if (noAtual == null) return;

        caminharEmOrdemInterno(novoNo, noAtual.getLeft(), sb);
        sb.append(noAtual.getValue()).append(" \n ");
        caminharEmOrdemInterno(novoNo, noAtual.getRight(), sb);
    }

    protected int alturaInterno(No<T> novoNo, No<T> noAtual) {
        if (noAtual == null) {
            return -1; // árvore vazia tem altura -1
        }
        int alturaEsquerda = alturaInterno(novoNo, noAtual.getLeft());
        int alturaDireita = alturaInterno(novoNo, noAtual.getRight());
        return 1 + Math.max(alturaEsquerda, alturaDireita);
    }

    protected int quantidadeNosInterno(No<T> novoNo, No<T> noAtual) {
        if (noAtual == null) {
            return 0;
        }
        int qtdEsquerda = quantidadeNosInterno(novoNo, noAtual.getLeft());
        int qtdDireita = quantidadeNosInterno(novoNo, noAtual.getRight());
        return 1 + qtdEsquerda + qtdDireita;
    }
}

