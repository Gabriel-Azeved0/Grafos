package untitled.src;

public class Aluno implements Comparable<Aluno> {

    private String matricula;
    private String nome;

    public Aluno(String m, String n) {
        this.matricula = m;
        this.nome = n;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int compareTo(Aluno outro) {
        // 1º critério: matrícula
        int compMatricula = this.matricula.compareTo(outro.matricula);
        if (compMatricula != 0) {
            return compMatricula;
        }

        // 2º critério (desempate): nome
        return this.nome.compareToIgnoreCase(outro.nome);
    }

    @Override
    public String toString() {
        return matricula + " - " + nome;
    }
}