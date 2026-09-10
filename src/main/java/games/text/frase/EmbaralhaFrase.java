package games.text.frase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class EmbaralhaFrase {

    private final Scanner entrada = new Scanner(System.in);

    private final List<String> frases = Arrays.asList(
            "Programar em Java pode ser muito divertido",
            "O conhecimento e a chave para o sucesso",
            "Nunca desista dos seus sonhos por mais dificil que pareca",
            "A pratica diaria leva a mestria em qualquer area",
            "Algoritmos sao a base de toda a computacao moderna",
            "A criatividade nasce da curiosidade e da experimentacao",
            "Pequenas atitudes geram grandes transformacoes na vida",
            "Um bom desenvolvedor sempre busca aprender coisas novas",
            "O sol nascera novamente amanha trazendo nova esperanca",
            "A leitura abre as portas para mundos inimaginaveis"
    );

    public static void main(String[] args) {
        new EmbaralhaFrase().iniciar();
    }

    public void iniciar() {
        System.out.println("=== EMBARALHA-FRASE ===");
        System.out.println("As palavras de uma frase serao mostradas fora de ordem.");
        System.out.println("Sua missao e reorganiza-las digitando a frase correta.");
        System.out.print("\nQuantas rodadas? ");
        int qtd;

        try {
            qtd = Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            qtd = 3;
        }

        if (qtd > frases.size()) {
            qtd = frases.size();
        }

        List<String> rodada = new ArrayList<>(frases);
        Collections.shuffle(rodada);

        int acertos = 0;
        for (int i = 0; i < qtd; i++) {
            String original = rodada.get(i);
            List<String> palavras = new ArrayList<>(Arrays.asList(original.split("\\s+")));
            List<String> embaralhadas = new ArrayList<>(palavras);
            do {
                Collections.shuffle(embaralhadas);
            } while (embaralhadas.equals(palavras) && palavras.size() > 1);

            System.out.printf("%n[%d/%d] Palavras embaralhadas:%n  ", i + 1, qtd);
            for (String p : embaralhadas) {
                System.out.print(p + " | ");
            }
            System.out.print("\nReorganize: ");
            String resp = entrada.nextLine().trim();

            if (normalizar(resp).equals(normalizar(original))) {
                System.out.println("Correto!");
                acertos++;
            } else {
                System.out.println("Errado. Frase correta: " + original);
            }
        }

        System.out.printf("%n===== FIM =====%nAcertos: %d/%d (%.1f%%)%n",
                acertos, qtd, (acertos * 100.0) / qtd);
    }

    private String normalizar(String s) {
        return s.toLowerCase().replaceAll("\\s+", " ").trim();
    }
}

