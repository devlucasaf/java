package math.algebra.matriz;

import java.util.Scanner;

public class MatrizCalculadora {

    // --- SOMA ELEMENTO A ELEMENTO DAS MATRIZES A E B ---
    public static double[][] soma(double[][] a, double[][] b) {
        int linhas = a.length;
        int colunas = a[0].length;
        double[][] resultado = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[i][j] = a[i][j] + b[i][j];
            }
        }
        return resultado;
    }

    // --- SUBTRAI ELEMENTO A ELEMENTO A MATRIZ B DA MATRIZ A ---
    public static double[][] subtracao(double[][] a, double[][] b) {
        int linhas = a.length;
        int colunas = a[0].length;
        double[][] resultado = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[i][j] = a[i][j] - b[i][j];
            }
        }
        return resultado;
    }

    // --- MULTIPLICA AS MATRIZES A E B ---
    public static double[][] multiplicacao(double[][] a, double[][] b) {
        int linhasA = a.length;
        int colunasA = a[0].length;
        int colunasB = b[0].length;
        double[][] resultado = new double[linhasA][colunasB];
        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                for (int k = 0; k < colunasA; k++) {
                    resultado[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return resultado;
    }

    // --- TROCA LINHAS POR COLUNAS DA MATRIZ ---
    public static double[][] transposta(double[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        double[][] resultado = new double[colunas][linhas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[j][i] = matriz[i][j];
            }
        }
        return resultado;
    }

    // --- CALCULA O DETERMINANTE DE MATRIZES ATE 3X3 ---
    public static double determinante(double[][] matriz) {
        int n = matriz.length;
        if (n == 1) {
            return matriz[0][0];
        }

        if (n == 2) {
            return matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0];
        }

        if (n == 3) {
            return matriz[0][0] * (matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1])
                 - matriz[0][1] * (matriz[1][0] * matriz[2][2] - matriz[1][2] * matriz[2][0])
                 + matriz[0][2] * (matriz[1][0] * matriz[2][1] - matriz[1][1] * matriz[2][0]);
        }
        throw new IllegalArgumentException("Determinante suportado apenas para matrizes até 3x3");
    }

    // --- MULTIPLICA TODOS OS ELEMENTOS DA MATRIZ POR UM ESCALAR ---
    public static double[][] multiplicacaoEscalar(double[][] matriz, double escalar) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        double[][] resultado = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado[i][j] = matriz[i][j] * escalar;
            }
        }
        return resultado;
    }

    // --- IMPRIME A MATRIZ FORMATADA NO CONSOLE ---
    public static void imprimir(double[][] matriz) {
        for (double[] linha : matriz) {
            System.out.print("| ");
            for (double val : linha) {
                System.out.printf("%8.2f ", val);
            }
            System.out.println("|");
        }
        System.out.println();
    }

    // --- LE OS ELEMENTOS DE UMA MATRIZ A PARTIR DO TECLADO ---
    public static double[][] lerMatriz(Scanner scanner, int linhas, int colunas) {
        double[][] matriz = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.printf("  [%d][%d]: ", i, j);
                matriz[i][j] = scanner.nextDouble();
            }
        }
        return matriz;
    }

    // --- MENU INTERATIVO DAS OPERACOES COM MATRIZES ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("      CALCULADORA DE MATRIZES         ");
            System.out.println(" 1. Soma                              ");
            System.out.println(" 2. Subtração                         ");
            System.out.println(" 3. Multiplicação                     ");
            System.out.println(" 4. Transposta                        ");
            System.out.println(" 5. Determinante (2x2 ou 3x3)         ");
            System.out.println(" 6. Multiplicação por escalar         ");
            System.out.println(" 0. Sair                              ");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1, 2, 3 -> {
                    System.out.print("Linhas da matriz A: ");
                    int linhasMatrizA = scanner.nextInt();
                    System.out.print("Colunas da matriz A: ");
                    int colunasMatrizA = scanner.nextInt();

                    System.out.println("Matriz A:");
                    double[][] matrizA = lerMatriz(scanner, linhasMatrizA, colunasMatrizA);

                    int linhasMatrizB;
                    int colunasMatrizB;
                    if (opcao == 3) {
                        linhasMatrizB = colunasMatrizA;
                        System.out.print("Colunas da matriz B: ");
                        colunasMatrizB = scanner.nextInt();
                    } else {
                        linhasMatrizB = linhasMatrizA;
                        colunasMatrizB = colunasMatrizA;
                    }

                    System.out.println("Matriz B:");
                    double[][] matrizB = lerMatriz(scanner, linhasMatrizB, colunasMatrizB);

                    double[][] resultado = switch (opcao) {
                        case 1 -> soma(matrizA, matrizB);
                        case 2 -> subtracao(matrizA, matrizB);
                        case 3 -> multiplicacao(matrizA, matrizB);
                        default -> null;
                    };

                    System.out.println("Resultado:");
                    imprimir(resultado);
                }
                case 4 -> {
                    System.out.print("Linhas: ");
                    int linha = scanner.nextInt();
                    System.out.print("Colunas: ");
                    int coluna = scanner.nextInt();
                    System.out.println("Matriz:");
                    double[][] matriz = lerMatriz(scanner, linha, coluna);
                    System.out.println("Transposta:");
                    imprimir(transposta(matriz));
                }
                case 5 -> {
                    System.out.print("Tamanho (2 ou 3): ");
                    int n = scanner.nextInt();
                    System.out.println("Matriz:");
                    double[][] m = lerMatriz(scanner, n, n);
                    System.out.printf("Determinante: %.2f%n%n", determinante(m));
                }
                case 6 -> {
                    System.out.print("Linhas: ");
                    int linha = scanner.nextInt();
                    System.out.print("Colunas: ");
                    int coluna = scanner.nextInt();
                    System.out.println("Matriz:");
                    double[][] matriz = lerMatriz(scanner, linha, coluna);
                    System.out.print("Escalar: ");
                    double escalar = scanner.nextDouble();
                    System.out.println("Resultado:");
                    imprimir(multiplicacaoEscalar(matriz, escalar));
                }
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }
}

