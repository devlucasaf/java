package games.puzzle.quebracabeca;

import java.util.Random;
import java.util.Scanner;

public class QuebraCabecaDeslizante {

    private static final int TAMANHO = 4;

    private final int[][]   tabuleiro = new int[TAMANHO][TAMANHO];
    private final Random    sorteador = new Random();
    private final Scanner   entrada = new Scanner(System.in);

    private int vazioLinha;
    private int vazioColuna;
    private int movimentos;

    public static void main(String[] args) {
        new QuebraCabecaDeslizante().iniciar();
    }

    public void iniciar() {
        System.out.println("=== QUEBRA-CABECA DESLIZANTE (15-PUZZLE) ===");
        System.out.println("Ordene de 1 a 15. O espaco vazio e representado por '  '.");
        System.out.println("Comandos: w (sobe peca de baixo) | a (peca da direita) | s (peca de cima) | d (peca da esquerda) | q (sair)");
        boolean novaPartida = true;
        while (novaPartida) {
            inicializarOrdenado();
            embaralhar(200);
            movimentos = 0;
            executar();
            System.out.print("\nJogar novamente? (s/n): ");
            novaPartida = entrada.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    private void inicializarOrdenado() {
        int valor = 1;
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                tabuleiro[i][j] = valor++;
            }
        }
        tabuleiro[TAMANHO - 1][TAMANHO - 1] = 0;
        vazioLinha = TAMANHO - 1;
        vazioColuna = TAMANHO - 1;
    }

    private void embaralhar(int quantidade) {
        for (int n = 0; n < quantidade; n++) {
            int dir = sorteador.nextInt(4);
            char c = "wasd".charAt(dir);
            mover(c);
        }
    }

    private void executar() {
        while (true) {
            desenhar();
            if (estaOrdenado()) {
                System.out.println("PARABENS! Voce resolveu em " + movimentos + " movimentos!");
                return;
            }

            System.out.print("Acao: ");
            String comando = entrada.nextLine().trim().toLowerCase();
            if (comando.equals("q")) {
                return;
            }

            if (comando.length() == 1 && "wasd".indexOf(comando.charAt(0)) >= 0) {
                if (mover(comando.charAt(0))) {
                    movimentos++;
                }
            }
        }
    }

    private boolean mover(char direcao) {
        int origemL = vazioLinha;
        int origemC = vazioColuna;
        switch (direcao) {
            case 'w' -> origemL++;
            case 's' -> origemL--;
            case 'a' -> origemC++;
            case 'd' -> origemC--;
        }

        if (origemL < 0 || origemL >= TAMANHO || origemC < 0 || origemC >= TAMANHO) {
            return false;
        }

        tabuleiro[vazioLinha][vazioColuna] = tabuleiro[origemL][origemC];
        tabuleiro[origemL][origemC] = 0;
        vazioLinha = origemL;
        vazioColuna = origemC;
        return true;
    }

    private boolean estaOrdenado() {
        int esperado = 1;
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (i == TAMANHO - 1 && j == TAMANHO - 1) {
                    return tabuleiro[i][j] == 0;
                }

                if (tabuleiro[i][j] != esperado++) {
                    return false;
                }
            }
        }
        return true;
    }

    private void desenhar() {
        System.out.println();
        System.out.println("Movimentos: " + movimentos);
        String divisor = "+----+----+----+----+";
        System.out.println(divisor);
        for (int i = 0; i < TAMANHO; i++) {
            StringBuilder sb = new StringBuilder("|");
            for (int j = 0; j < TAMANHO; j++) {
                if (tabuleiro[i][j] == 0) {
                    sb.append("    |");
                } else {
                    sb.append(String.format(" %2d |", tabuleiro[i][j]));
                }
            }
            System.out.println(sb);
            System.out.println(divisor);
        }
    }
}

