package games.plataforma.pacman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PacMan {
    private static final int    LARGURA = 28;
    private static final int    ALTURA = 14;
    private static final char   PAREDE = '#';
    private static final char   ESCADA = 'H';
    private static final char   PASTILHA = '.';
    private static final char   PODER = '*';
    private static final char   PAC = 'C';
    private static final char   FANTASMA = 'G';

    private final Scanner       entrada = new Scanner(System.in);
    private final Random        sorteador = new Random();
    private final char[][]      mapa = new char[ALTURA][LARGURA];
    private final List<int[]>   fantasmas = new ArrayList<>();

    private int pacX;
    private int pacY;
    private int vidas;
    private int pontuacao;
    private int totalPastilhas;
    private int turnosPoder;

    public static void main(String[] args) {
        new PacMan().iniciar();
    }

    public void iniciar() {
        System.out.println("=== PAC-MAN ===");
        System.out.println("Coma todas as pastilhas. Pegue o poder para comer fantasmas.");
        System.out.println("Comandos: a/d (lateral) | w/s (escada) | enter (parado)");
        System.out.print("Pressione ENTER para começar...");
        entrada.nextLine();

        construirMapa();
        pacX = 1;
        pacY = ALTURA - 2;
        vidas = 3;
        pontuacao = 0;
        turnosPoder = 0;
        fantasmas.add(new int[]{LARGURA - 2, 2});
        fantasmas.add(new int[]{LARGURA / 2, 6});
        fantasmas.add(new int[]{4, 2});

        while (vidas > 0 && totalPastilhas > 0) {
            desenhar();
            System.out.print("Acao: ");
            String comando = entrada.nextLine().trim().toLowerCase();
            mover(comando);
            coletar();
            moverFantasmas();
            colidirFantasmas();
            if (turnosPoder > 0) {
                turnosPoder--;
            }
        }

        desenhar();
        if (totalPastilhas == 0) {
            System.out.println("VITÓRIA! Você comeu todas as pastilhas!");
        } else {
            System.out.println("GAME OVER!");
        }
        System.out.println("Pontuação final: " + pontuacao);
    }

    private void construirMapa() {
        totalPastilhas = 0;
        for (int y = 0; y < ALTURA; y++) {
            for (int x = 0; x < LARGURA; x++) {
                mapa[y][x] = ' ';
            }
        }

        int[] andares = {
                ALTURA - 1,
                ALTURA - 5,
                ALTURA - 9,
                1
        };

        for (int y : andares) {
            for (int x = 0; x < LARGURA; x++) {
                mapa[y][x] = PAREDE;
            }

            for (int x = 1; x < LARGURA - 1; x++) {
                if (y - 1 >= 0 && mapa[y - 1][x] == ' ') {
                    mapa[y - 1][x] = PASTILHA;
                    totalPastilhas++;
                }
            }
        }

        criarEscada(5, ALTURA - 5, ALTURA - 1);
        criarEscada(LARGURA - 6, ALTURA - 5, ALTURA - 1);
        criarEscada(LARGURA / 2, ALTURA - 9, ALTURA - 5);
        criarEscada(3, 1, ALTURA - 9);
        criarEscada(LARGURA - 4, 1, ALTURA - 9);
        mapa[ALTURA - 2][1] = ' ';
        mapa[0][2] = PODER;
        mapa[0][LARGURA - 3] = PODER;
        totalPastilhas += 2;
    }

    private void criarEscada(int x, int yTopo, int yBase) {
        for (int y = yTopo; y < yBase; y++) {
            mapa[y][x] = ESCADA;
        }
    }

    private void mover(String comando) {
        int novoX = pacX;
        int novoY = pacY;
        switch (comando) {
            case "a" -> novoX--;
            case "d" -> novoX++;
            case "w" -> {
                if (mapa[pacY][pacX] == ESCADA) {
                    novoY--;
                }
            }
            case "s" -> {
                if (pacY + 1 < ALTURA && mapa[pacY + 1][pacX] == ESCADA) {
                    novoY++;
                }
            }
        }

        if (novoX >= 0 && novoX < LARGURA && novoY >= 0 && novoY < ALTURA && mapa[novoY][novoX] != PAREDE) {
            pacX = novoX;
            pacY = novoY;
        }

        if (pacY + 1 < ALTURA && mapa[pacY + 1][pacX] != PAREDE && mapa[pacY][pacX] != ESCADA) {
            pacY++;
        }
    }

    private void coletar() {
        if (mapa[pacY][pacX] == PASTILHA) {
            pontuacao += 10;
            totalPastilhas--;
            mapa[pacY][pacX] = ' ';
        } else if (mapa[pacY][pacX] == PODER) {
            pontuacao += 50;
            totalPastilhas--;
            turnosPoder = 8;
            mapa[pacY][pacX] = ' ';
            System.out.println("Modo poder ativado!");
        }
    }

    private void moverFantasmas() {
        for (int[] f : fantasmas) {
            int direcao = sorteador.nextInt(4);
            int nx = f[0];
            int ny = f[1];
            switch (direcao) {
                case 0 -> nx--;
                case 1 -> nx++;
                case 2 -> {
                    if (mapa[f[1]][f[0]] == ESCADA) {
                        ny--;
                    }
                }
                case 3 -> {
                    if (f[1] + 1 < ALTURA && mapa[f[1] + 1][f[0]] == ESCADA) {
                        ny++;
                    }
                }
            }

            if (nx >= 0 && nx < LARGURA && ny >= 0 && ny < ALTURA && mapa[ny][nx] != PAREDE) {
                f[0] = nx;
                f[1] = ny;
            }

            if (f[1] + 1 < ALTURA && mapa[f[1] + 1][f[0]] != PAREDE && mapa[f[1]][f[0]] != ESCADA) {
                f[1]++;
            }
        }
    }

    private void colidirFantasmas() {
        for (int[] f : fantasmas) {
            if (f[0] == pacX && f[1] == pacY) {
                if (turnosPoder > 0) {
                    pontuacao += 200;
                    f[0] = LARGURA / 2;
                    f[1] = 6;
                } else {
                    vidas--;
                    System.out.println("Você foi pego! Vidas: " + vidas);
                    pacX = 1;
                    pacY = ALTURA - 2;
                    return;
                }
            }
        }
    }

    private void desenhar() {
        char[][] tela = new char[ALTURA][LARGURA];
        for (int y = 0; y < ALTURA; y++) {
            System.arraycopy(mapa[y], 0, tela[y], 0, LARGURA);
        }

        for (int[] f : fantasmas) {
            tela[f[1]][f[0]] = FANTASMA;
        }

        tela[pacY][pacX] = PAC;
        System.out.println();
        System.out.println("Vidas: " + vidas);
        System.out.println("Pontos: " + pontuacao);
        System.out.println("Pastilhas: " + totalPastilhas);

        if (turnosPoder > 0) {
            System.out.println("[PODER: " + turnosPoder + "]");
        }

        StringBuilder borda = new StringBuilder("+");
        for (int i = 0; i < LARGURA; i++) {
            borda.append('-');
        }

        borda.append('+');
        System.out.println(borda);
        for (int y = 0; y < ALTURA; y++) {
            System.out.print('|');
            for (int x = 0; x < LARGURA; x++) {
                System.out.print(tela[y][x]);
            }
            System.out.println('|');
        }
        System.out.println(borda);
    }
}

