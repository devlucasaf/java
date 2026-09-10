package games.plataforma.donkeykong;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DonkeyKong {

    private static final int    LARGURA = 24;
    private static final int    ALTURA = 14;
    private static final char   ANDAR = '=';
    private static final char   ESCADA = 'H';
    private static final char   JOGADOR = 'M';
    private static final char   BARRIL = 'O';
    private static final char   PRINCESA = 'P';
    private static final char   MACACO = '&';

    private final Scanner       entrada = new Scanner(System.in);
    private final Random        sorteador = new Random();
    private final char[][]      mapa = new char[ALTURA][LARGURA];
    private final List<Barril>  barris = new ArrayList<>();

    private int     jogadorX;
    private int     jogadorY;
    private int     vidas;
    private int     pontuacao;
    private int     turnos;
    private boolean vitoria;

    public static void main(String[] args) {
        new DonkeyKong().iniciar();
    }

    public void iniciar() {
        System.out.println("=== DONKEY KONG ===");
        System.out.println("Suba ate a princesa evitando os barris.");
        System.out.println("Comandos: a (esquerda) | d (direita) | w (subir escada) | s (descer escada) | enter (parado)");
        System.out.print("Pressione ENTER para comecar...");
        entrada.nextLine();

        construirMapa();
        jogadorX = 1;
        jogadorY = ALTURA - 2;
        vidas = 3;
        pontuacao = 0;
        turnos = 0;
        vitoria = false;

        while (vidas > 0 && !vitoria) {
            desenhar();
            System.out.print("Acao: ");
            String comando = entrada.nextLine().trim().toLowerCase();
            mover(comando);
            atualizarBarris();
            verificarColisao();
            turnos++;
            if (turnos % 3 == 0) {
                lancarBarril();
            }
            verificarVitoria();
        }

        desenhar();
        if (vitoria) {
            System.out.println("VITORIA! Voce salvou a princesa!");
        } else {
            System.out.println("GAME OVER!");
        }
        System.out.println("Pontuacao final: " + pontuacao);
    }

    private void construirMapa() {
        for (int y = 0; y < ALTURA; y++) {
            for (int x = 0; x < LARGURA; x++) {
                mapa[y][x] = ' ';
            }
        }

        int[] andaresY = {
                ALTURA - 1,
                ALTURA - 5,
                ALTURA - 9,
                1
        };

        for (int y : andaresY) {
            for (int x = 0; x < LARGURA; x++) {
                mapa[y][x] = ANDAR;
            }
        }
        criarEscada(LARGURA - 3, ALTURA - 5, ALTURA - 1);
        criarEscada(3, ALTURA - 9, ALTURA - 5);
        criarEscada(LARGURA - 4, 1, ALTURA - 9);
        mapa[0][2] = PRINCESA;
        mapa[0][3] = MACACO;
    }

    private void criarEscada(int x, int yTopo, int yBase) {
        for (int y = yTopo; y < yBase; y++) {
            mapa[y][x] = ESCADA;
        }
    }

    private void mover(String comando) {
        int novoX = jogadorX;
        int novoY = jogadorY;
        switch (comando) {
            case "a" -> novoX--;
            case "d" -> novoX++;
            case "w" -> {
                if (mapa[jogadorY][jogadorX] == ESCADA || mapa[jogadorY - 1][jogadorX] == ESCADA) {
                    novoY--;
                }
            }
            case "s" -> {
                if (jogadorY + 1 < ALTURA && (mapa[jogadorY + 1][jogadorX] == ESCADA || mapa[jogadorY][jogadorX] == ESCADA)) {
                    novoY++;
                }
            }
        }

        if (novoX >= 0 && novoX < LARGURA && novoY >= 0 && novoY < ALTURA) {
            if (mapa[novoY][novoX] != ANDAR) {
                jogadorX = novoX;
                jogadorY = novoY;
            }
        }

        if (jogadorY + 1 < ALTURA && mapa[jogadorY + 1][jogadorX] != ANDAR && mapa[jogadorY][jogadorX] != ESCADA) {
            jogadorY++;
        }
    }

    private void lancarBarril() {
        barris.add(new Barril(LARGURA - 5, 1, 1));
    }

    private void atualizarBarris() {
        Iterator<Barril> it = barris.iterator();
        while (it.hasNext()) {
            Barril b = it.next();
            b.mover();
            if (b.getX() < 0 || b.getX() >= LARGURA) {
                it.remove();
                continue;
            }

            if (b.getY() + 1 < ALTURA && mapa[b.getY() + 1][b.getX()] != ANDAR) {
                b.cair();
            } else if (sorteador.nextInt(10) == 0) {
                b.inverter();
            }

            if (b.getY() >= ALTURA - 1) {
                pontuacao += 5;
                it.remove();
            }
        }
    }

    private void verificarColisao() {
        Iterator<Barril> it = barris.iterator();
        while (it.hasNext()) {
            Barril barril = it.next();
            if (barril.getX() == jogadorX && barril.getY() == jogadorY) {
                vidas--;
                jogadorX = 1;
                jogadorY = ALTURA - 2;
                barris.clear();
                System.out.println("Voce foi atingido! Vidas restantes: " + vidas);
                return;
            }
        }
    }

    private void verificarVitoria() {
        if (jogadorY == 1 && Math.abs(jogadorX - 2) <= 1) {
            vitoria = true;
            pontuacao += 100;
        }
    }

    private void desenhar() {
        char[][] tela = new char[ALTURA][LARGURA];
        for (int y = 0; y < ALTURA; y++) {
            System.arraycopy(mapa[y], 0, tela[y], 0, LARGURA);
        }

        for (Barril b : barris) {
            if (b.getY() >= 0 && b.getY() < ALTURA && b.getX() >= 0 && b.getX() < LARGURA) {
                tela[b.getY()][b.getX()] = BARRIL;
            }
        }
        tela[jogadorY][jogadorX] = JOGADOR;

        System.out.println();
        System.out.println("Vidas: " + vidas + "   Pontuacao: " + pontuacao);
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

