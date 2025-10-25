package untitled.src;

import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int i = 1;
        Scanner scanner = new Scanner(System.in);
        ComparadorAlunoPorMatricula comp = new ComparadorAlunoPorMatricula();
        ComparadorAlunoPorNome comp2 = new ComparadorAlunoPorNome();
        ArvBin<Aluno> arv = new ArvBin<Aluno>(comp, null);

        System.out.print("BEM VINDO AO SISTEMA DE CONTROLE DE ALUNOS \n");
        while (i != 6) {
            System.out.print("Escolha a ação que deseja executar: \n"
                    + "1 - Adicionar aluno\n"
                    + "2 - Pesquisar aluno por matrícula\n"
                    + "3 - Adicionar aluno por nome\n"
                    + "4 - Remover aluno\n"
                    + "5 - Imprimir lista de alunos\n"
                    + "6 - Sair\n"
                    + "Digite a opção desejada e pressione Enter: "
            );
            i = scanner.nextInt();
            scanner.nextLine();
            switch (i){
                case 1:
                    System.out.println("Insira o nome do aluno: " );
                    String nome = scanner.nextLine();
                    System.out.println("Insira a matricula do aluno: " );
                    String matricula = scanner.nextLine();
                    System.out.println(nome);
                    System.out.println(matricula);
                    Aluno a = new Aluno(matricula, nome);
                    arv.adicionar(a);
                    System.out.println("\n\n");
                    break;
                case 2:
                    System.out.print("Insira a matricula que deseja buscar: " );
                    matricula = scanner.nextLine();
                    a = new Aluno(matricula, "");
                    if (arv.pesquisar(a) == null){
                        System.out.println("Aluno não encontrado");
                    }else{
                        System.out.println(arv.pesquisar(a));
                    }
                    System.out.println("\n\n");
                    break;
                case 3:
                    System.out.print("Insira o nome que deseja buscar: " );
                    nome = scanner.nextLine();
                    a = new Aluno(null, nome);
                    if (arv.pesquisar(a, comp2) == null){
                        System.out.println("Aluno não encontrado");
                    }else{
                        System.out.println(arv.pesquisar(a, comp2));
                    }
                    System.out.println("\n\n");
                    break;
                case 4:
                    System.out.print("Insira a matricula que deseja remover: " );
                    matricula = scanner.nextLine();
                    a = new Aluno(matricula, null);
                    if (arv.pesquisar(a) == null){
                        System.out.println("Aluno não encontrado");
                    }else{
                        System.out.println("Aluno removido: " + arv.remover(arv.pesquisar(a)));
                    }
                    System.out.println("\n\n");
                    break;
                case 5:
                    System.out.println("Lista de Alunos: ");
                    System.out.println(arv.caminharEmOrdem());
                    System.out.println("\n\n");
                    break;
            }

        }
    }
}