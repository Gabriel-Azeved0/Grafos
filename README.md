🌳 ArvoresBinarias2025_2

Este projeto foi desenvolvido como parte da disciplina de Técnicas de Programação Avançada (TPA) e tem como objetivo implementar, testar e analisar estruturas de árvores binárias e AVL em Java, com foco em topologia, complexidade de busca e geração controlada de árvores.

📂 Estrutura do Projeto
📁 src/lib

Nesta pasta estão localizadas as classes base e interfaces responsáveis pelas implementações das árvores:

ArvBin.java — Implementação base da árvore binária de busca.
ArvoreAVLExemplo.java — Exemplo de implementação de árvore AVL, usada como referência.
IArvoreBinaria.java — Interface que define as operações principais da árvore binária (adicionar, pesquisar, remover, altura, etc).
Main.java — Classe principal de execução auxiliar.
NoExemplo.java — Estrutura de nó utilizada na árvore binária.

📌 Essas classes servem de base para implementação e testes. A interface deve ser renovada e as classes ArvoreBinaria e AVL devem ser adaptadas conforme a especificação do trabalho.

📁 src/app

Nesta pasta estão os aplicativos e classes auxiliares usadas para gerar dados, comparar chaves e executar os relatórios:
Aluno.java — Classe de entidade usada como elemento nas árvores.
GeradorDeArvores.java — Responsável por gerar árvores degeneradas e perfeitamente balanceadas para análise de performance.
ComparadorAlunoPorMatricula.java — Comparador padrão para ordenação e busca nas árvores.
ComparadorAlunoPorNome.java — Comparador alternativo para busca por nome (gera buscas O(n)).
AppRelatorioArvoreBinaria.java — Aplicativo de teste e geração de relatórios da primeira etapa do trabalho.
AppRelatorioAVL.java — Aplicativo de teste e geração de relatórios da terceira etapa, envolvendo árvores AVL.

🧪 Funcionalidades Principais

Geração de árvores degeneradas e perfeitamente balanceadas para análise de desempenho.
Implementação de busca binária (pesquisar) e comparação de custo computacional entre topologias.
Análise de complexidade e comportamento em cenários de grande volume de dados.
Geração automática de relatórios de execução.

📊 Relatórios

📎 O relatório da Etapa 1 contém os resultados de topologia, custo de busca e análise de complexidade com base no AppRelatorioArvoreBinaria. https://docs.google.com/document/d/1y5R0vCrUQoWrjzfQFiG1SWnpyuHVvf7v/edit?usp=sharing&ouid=105652783404333681485&rtpof=true&sd=true
