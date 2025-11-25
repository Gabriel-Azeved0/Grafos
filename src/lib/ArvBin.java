package src.lib;

import java.util.Comparator;

public class ArvBin<T> implements IArvoreBinaria<T> {
    protected NoExemplo<T> raiz;
    protected final Comparator<T> comparator;

    public ArvBin(Comparator<T> comparator) {
        this.comparator = java.util.Objects.requireNonNull(comparator);
        this.raiz = null;
    }

    @Override
    public void adicionar(T novoValor) {
        NoExemplo<T> novoNo = new NoExemplo<>(novoValor);
        if (this.raiz == null) {
            this.raiz = novoNo;
        } else {
            adicionarInterno(novoNo, this.raiz);
        }
    }

    @Override
    public T pesquisar(T valor) {
        if (this.raiz == null) return null;
        NoExemplo<T> achado = pesquisarInterno(new NoExemplo<>(valor), this.raiz);
        return (achado == null) ? null : achado.getValor();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T pesquisar(T valor, Comparator comparador) {
        if (this.raiz == null) return null;
        Comparator<T> compExtra = (Comparator<T>) comparador;
        NoExemplo<T> achado = pesquisarInterno(new NoExemplo<>(valor), this.raiz, compExtra);
        return (achado == null) ? null : achado.getValor();
    }

    @Override
    public T remover(T valor) {
        if (this.raiz == null) return null;
        NoRemovido<T> res = removerInterno(new NoExemplo<>(valor), this.raiz);
        this.raiz = res.noAtualizado;
        return res.valorRemovido;
    }

    @Override
    public int altura() {
        if (this.raiz == null) return -1;
        return alturaInterno(this.raiz, this.raiz);
    }

    @Override
    public int quantidadeNos() {
        if (this.raiz == null) return 0;
        return quantidadeNosInterno(this.raiz, this.raiz);
    }

    @Override
    public String caminharEmNivel() {
        return "[]";
    }

    @Override
    public String caminharEmOrdem() {
        if (this.raiz == null) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        caminharEmOrdemInterno(this.raiz, this.raiz, sb);
        if (sb.length() >= 3 && sb.substring(sb.length() - 3).equals(" \n ")) {
            sb.setLength(sb.length() - 3);
        }
        sb.append(']');
        return sb.toString();
    }

    protected NoExemplo<T> adicionarInterno(NoExemplo<T> novoNo, NoExemplo<T> noAtual) {
        int cmp = comparator.compare(novoNo.getValor(), noAtual.getValor());
        if (cmp == 0) {
            System.out.println("Matricula ja existe, novo aluno nao foi adicionado!!");
            return null;
        }
        if (cmp < 0) {
            if (noAtual.getFilhoEsquerda() == null) {
                noAtual.setFilhoEsquerda(novoNo);
            } else {
                adicionarInterno(novoNo, noAtual.getFilhoEsquerda());
            }
        } else { // cmp > 0
            if (noAtual.getFilhoDireita() == null) {
                noAtual.setFilhoDireita(novoNo);
            } else {
                adicionarInterno(novoNo, noAtual.getFilhoDireita());
            }
        }
        return null;
    }

    protected NoExemplo<T> pesquisarInterno(NoExemplo<T> noBusca, NoExemplo<T> noAtual) {
        if (noAtual == null) return null;

        int cmp = comparator.compare(noBusca.getValor(), noAtual.getValor());
        if (cmp == 0) return noAtual;
        if (cmp < 0)  return pesquisarInterno(noBusca, noAtual.getFilhoEsquerda());
        else          return pesquisarInterno(noBusca, noAtual.getFilhoDireita());
    }

    protected NoExemplo<T> pesquisarInterno(NoExemplo<T> noBusca, NoExemplo<T> noAtual, Comparator<T> compExtra) {
        if (noAtual == null) return null;
        if (compExtra.compare(noBusca.getValor(), noAtual.getValor()) == 0) return noAtual;

        NoExemplo<T> achadoEsq = pesquisarInterno(noBusca, noAtual.getFilhoEsquerda(), compExtra);
        if (achadoEsq != null) return achadoEsq;
        else return pesquisarInterno(noBusca, noAtual.getFilhoDireita(), compExtra);
    }

    protected NoRemovido<T> removerInterno(NoExemplo<T> noBusca, NoExemplo<T> noAtual) {
        if (noAtual == null) return new NoRemovido<>(null, null);

        int cmp = comparator.compare(noBusca.getValor(), noAtual.getValor());

        if (cmp < 0) {
            NoRemovido<T> res = removerInterno(noBusca, noAtual.getFilhoEsquerda());
            noAtual.setFilhoEsquerda(res.noAtualizado);
            return new NoRemovido<>(noAtual, res.valorRemovido);
        }

        if (cmp > 0) {
            NoRemovido<T> res = removerInterno(noBusca, noAtual.getFilhoDireita());
            noAtual.setFilhoDireita(res.noAtualizado);
            return new NoRemovido<>(noAtual, res.valorRemovido);
        }

        // achou o nó a remover
        T valorRemovido = noAtual.getValor();

        // caso 1: folha
        if (noAtual.getFilhoEsquerda() == null && noAtual.getFilhoDireita() == null) {
            return new NoRemovido<>(null, valorRemovido);
        }

        // caso 2: um filho
        if (noAtual.getFilhoEsquerda() == null) {
            return new NoRemovido<>(noAtual.getFilhoDireita(), valorRemovido);
        }
        if (noAtual.getFilhoDireita() == null) {
            return new NoRemovido<>(noAtual.getFilhoEsquerda(), valorRemovido);
        }

        // caso 3: dois filhos
        NoExemplo<T> menorDaDireita = encontroMenor(noAtual.getFilhoDireita());
        noAtual.setValor(menorDaDireita.getValor());
        NoRemovido<T> res = removerInterno(new NoExemplo<>(menorDaDireita.getValor()), noAtual.getFilhoDireita());
        noAtual.setFilhoDireita(res.noAtualizado);
        return new NoRemovido<>(noAtual, valorRemovido);
    }

    protected NoExemplo<T> encontroMenor(NoExemplo<T> noAtual) {
        while (noAtual.getFilhoEsquerda() != null) {
            noAtual = noAtual.getFilhoEsquerda();
        }
        return noAtual;
    }

    protected void caminharEmOrdemInterno(NoExemplo<T> novoNo, NoExemplo<T> noAtual, StringBuilder sb) {
        if (noAtual == null) return;
        caminharEmOrdemInterno(novoNo, noAtual.getFilhoEsquerda(), sb);
        sb.append(noAtual.getValor()).append(" \n ");
        caminharEmOrdemInterno(novoNo, noAtual.getFilhoDireita(), sb);
    }

    protected int alturaInterno(NoExemplo<T> novoNo, NoExemplo<T> noAtual) {
        if (noAtual == null) return -1;
        int hEsq = alturaInterno(novoNo, noAtual.getFilhoEsquerda());
        int hDir = alturaInterno(novoNo, noAtual.getFilhoDireita());
        return 1 + Math.max(hEsq, hDir);
    }

    protected int quantidadeNosInterno(NoExemplo<T> novoNo, NoExemplo<T> noAtual) {
        if (noAtual == null) return 0;
        int qEsq = quantidadeNosInterno(novoNo, noAtual.getFilhoEsquerda());
        int qDir = quantidadeNosInterno(novoNo, noAtual.getFilhoDireita());
        return 1 + qEsq + qDir;
    }

    protected static class NoRemovido<U> {
        NoExemplo<U> noAtualizado;
        U valorRemovido;
        NoRemovido(NoExemplo<U> noAtualizado, U valorRemovido) {
            this.noAtualizado = noAtualizado;
            this.valorRemovido = valorRemovido;
        }
    }
}
