package src.lib;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvBin<T> {

    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    public void adicionar(T novoValor) {
        NoExemplo<T> novo = new NoExemplo<>(novoValor);
        this.raiz = inserirAVL(this.raiz, novo);
    }

    private NoExemplo<T> inserirAVL(NoExemplo<T> atual, NoExemplo<T> novo) {
        if (atual == null) {
            return novo;
        }

        int cmp = super.comparator.compare(novo.getValor(), atual.getValor());

        if (cmp < 0) {
            atual.setFilhoEsquerda(inserirAVL(atual.getFilhoEsquerda(), novo));
        }
        else if (cmp > 0) {
            atual.setFilhoDireita(inserirAVL(atual.getFilhoDireita(), novo));
        }
        else {
            return atual;
        }

        return balancear(atual);
    }

    private NoExemplo<T> balancear(NoExemplo<T> no) {
        int fb = fatorBalanceamento(no);

        if (fb > 1 && fatorBalanceamento(no.getFilhoEsquerda()) >= 0) {
            return rotacaoDireita(no);
        }

        if (fb > 1 && fatorBalanceamento(no.getFilhoEsquerda()) < 0) {
            no.setFilhoEsquerda(rotacaoEsquerda(no.getFilhoEsquerda()));
            return rotacaoDireita(no);
        }

        if (fb < -1 && fatorBalanceamento(no.getFilhoDireita()) <= 0) {
            return rotacaoEsquerda(no);
        }

        if (fb < -1 && fatorBalanceamento(no.getFilhoDireita()) > 0) {
            no.setFilhoDireita(rotacaoDireita(no.getFilhoDireita()));
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private int fatorBalanceamento(NoExemplo<T> no) {
        return altura(no.getFilhoEsquerda()) - altura(no.getFilhoDireita());
    }

    private int altura(NoExemplo<T> no) {
        if (no == null) return 0;
        return 1 + Math.max(
                altura(no.getFilhoEsquerda()),
                altura(no.getFilhoDireita())
        );
    }

    private NoExemplo<T> rotacaoDireita(NoExemplo<T> y) {
        NoExemplo<T> x = y.getFilhoEsquerda();
        NoExemplo<T> T2 = x.getFilhoDireita();

        x.setFilhoDireita(y);
        y.setFilhoEsquerda(T2);

        return x;
    }

    private NoExemplo<T> rotacaoEsquerda(NoExemplo<T> y) {
        NoExemplo<T> x = y.getFilhoDireita();
        NoExemplo<T> T2 = x.getFilhoEsquerda();

        x.setFilhoEsquerda(y);
        y.setFilhoDireita(T2);

        return x;
    }
}
