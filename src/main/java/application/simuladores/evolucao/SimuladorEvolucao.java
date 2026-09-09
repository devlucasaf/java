package application.simuladores.evolucao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimuladorEvolucao {

    public static void main(String[] args) {
        String alvo = "A evolução emerge de pressão seletiva!";
        int populacao = 200;
        double elite = 0.1;
        double mutacao = 0.02;

        Random r = new Random();
        List<Individuo> pop = new ArrayList<>();
        for (int i = 0; i < populacao; i++) {
            pop.add(Individuo.aleatorio(alvo, r));
        }

        System.out.println("=== SIMULADOR DE EVOLUÇÃO GENÉTICA ===");
        System.out.println("Alvo: \"" + alvo + "\"");
        System.out.println("População: " + populacao + " | Elite: " + (int)(populacao * elite) + " | Mutação: " + mutacao);
        System.out.println();

        int geracao = 0;
        while (true) {
            Collections.sort(pop);
            Individuo melhor = pop.get(0);
            if (geracao % 20 == 0 || melhor.fitness == alvo.length()) {
                System.out.printf("Ger %4d | fitness %d/%d | \"%s\"%n",
                        geracao, melhor.fitness, alvo.length(), melhor.gene);
            }

            if (melhor.fitness == alvo.length()) {
                System.out.println("\nAlvo atingido em " + geracao + " gerações.");
                break;
            }

            int nElite = (int)(populacao * elite);
            List<Individuo> nova = new ArrayList<>(pop.subList(0, nElite));
            while (nova.size() < populacao) {
                Individuo a = pop.get(r.nextInt(nElite * 3));
                Individuo b = pop.get(r.nextInt(nElite * 3));
                nova.add(a.cruzar(b, alvo, r, mutacao));
            }
            pop = nova;
            geracao++;
        }
    }
}

