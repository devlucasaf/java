package games.narrativo.rpg;

import java.util.Random;

public class Inimigo {
    private String  nome;
    private int     vida;
    private int     vidaMaxima;
    private int     ataque;
    private int     defesa;
    private int     xpRecompensa;
    private int     ouroRecompensa;

    private final Random random = new Random();

    public Inimigo(String nome, int vida, int ataque, int defesa, int xp, int ouro) {
        this.nome = nome;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.xpRecompensa = xp;
        this.ouroRecompensa = ouro;
    }

    public int atacar() {
        return ataque + random.nextInt(8) - 2;
    }

    public int receberDano(int dano) {
        int danoReal = Math.max(dano - defesa - random.nextInt(3), 1);
        this.vida -= danoReal;
        if (this.vida < 0) {
            this.vida = 0;
        }
        return danoReal;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public String barraVida() {
        int barras = (int) ((double) vida / vidaMaxima * 20);
        return "".repeat(Math.max(barras, 0)) + "".repeat(20 - Math.max(barras, 0));
    }

    // Fábrica de inimigos por nível
    public static Inimigo gerarInimigo(int nivelJogador) {
        Random random = new Random();
        int tipo = random.nextInt(5);
        int mult = nivelJogador;

        return switch (tipo) {
            case 0 -> new Inimigo("Goblin", 30 + 10 * mult, 8 + 2 * mult, 2 + mult, 25 * mult, 10 + 5 * mult);
            case 1 -> new Inimigo("Esqueleto", 40 + 12 * mult, 10 + 3 * mult, 3 + mult, 35 * mult, 15 + 5 * mult);
            case 2 -> new Inimigo("Lobo Selvagem", 35 + 8 * mult, 12 + 3 * mult, 1 + mult, 30 * mult, 8 + 5 * mult);
            case 3 -> new Inimigo("Orc", 55 + 15 * mult, 12 + 4 * mult, 5 + mult, 50 * mult, 25 + 10 * mult);
            case 4 -> new Inimigo("Dragão Menor", 80 + 20 * mult, 15 + 5 * mult, 7 + 2 * mult, 80 * mult, 50 + 15 * mult);
            default -> new Inimigo("Slime", 20 + 5 * mult, 5 + mult, 1, 15 * mult, 5 + 3 * mult);
        };
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getXpRecompensa() {
        return xpRecompensa;
    }

    public int getOuroRecompensa() {
        return ouroRecompensa;
    }

}

