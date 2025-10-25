package untitled.src;

import java.util.Comparator;

public class ComparadorAlunoPorMatricula implements Comparator<Aluno> {

    @Override
    public int compare(Aluno a1, Aluno a2){
        return a1.getMatricula().compareTo(a2.getMatricula());
    }
}