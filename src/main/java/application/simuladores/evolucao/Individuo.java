package application.simuladores.evolucao;

import java.util.Random;

public class Individuo implements Comparable<Individuo> {

    public final String gene;
    public final int    fitness;

    // --- CRIA UM INDIVÍDUO E CALCULA SUA APTIDÃO EM RELAÇÃO AO ALVO ---
    public Individuo(String gene, String alvo) {
        this.gene = gene;
        int f = 0;
        int len = Math.min(gene.length(), alvo.length());

        for (int i = 0; i < len; i++) {
            if (gene.charAt(i) == alvo.charAt(i)) {
                f++;
            }
        }
        this.fitness = f;
    }

    // --- GERA UM INDIVÍDUO ALEATÓRIO COM O TAMANHO DO ALVO ---
    public static Individuo aleatorio(String alvo, Random r) {
        StringBuilder sb = new StringBuilder(alvo.length());
        for (int i = 0; i < alvo.length(); i++) {
            sb.append(alfabeto(r));
        }
        return new Individuo(sb.toString(), alvo);
    }

    // --- CRUZA DOIS INDIVÍDUOS E APLICA A POSSIBILIDADE DE MUTAÇÃO ---
    public Individuo cruzar(Individuo outro, String alvo, Random r, double taxaMutacao) {
        StringBuilder sb = new StringBuilder(gene.length());

        for (int i = 0; i < gene.length(); i++) {
            char c = r.nextBoolean() ? gene.charAt(i) : outro.gene.charAt(i);
            if (r.nextDouble() < taxaMutacao) {
                c = alfabeto(r);
            }
            sb.append(c);
        }
        return new Individuo(sb.toString(), alvo);
    }

    // --- RETORNA UM CARACTERE ALEATÓRIO DO ALFABETO PERMITIDO ---
    private static char alfabeto(Random r) {
        String chars = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ.!?";
        return chars.charAt(r.nextInt(chars.length()));
    }

    // --- ORDENA OS INDIVÍDUOS DO MAIOR PARA O MENOR FITNESS ---
    @Override
    public int compareTo(Individuo o) {
        return Integer.compare(o.fitness, this.fitness);
    }
}

