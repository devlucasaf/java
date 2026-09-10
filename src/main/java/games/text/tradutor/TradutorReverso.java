package games.text.tradutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TradutorReverso {

    private final Scanner entrada = new Scanner(System.in);

    private final List<Palavra> banco = new ArrayList<>(Arrays.asList(
            new Palavra("house", "casa", "ingles"),
            new Palavra("dog", "cachorro", "ingles"),
            new Palavra("cat", "gato", "ingles"),
            new Palavra("book", "livro", "ingles"),
            new Palavra("water", "agua", "ingles"),
            new Palavra("car", "carro", "ingles"),
            new Palavra("friend", "amigo", "ingles"),
            new Palavra("school", "escola", "ingles"),
            new Palavra("apple", "maca", "ingles"),
            new Palavra("sun", "sol", "ingles"),
            new Palavra("casa", "casa", "espanhol"),
            new Palavra("perro", "cachorro", "espanhol"),
            new Palavra("gato", "gato", "espanhol"),
            new Palavra("libro", "livro", "espanhol"),
            new Palavra("agua", "agua", "espanhol"),
            new Palavra("coche", "carro", "espanhol"),
            new Palavra("amigo", "amigo", "espanhol"),
            new Palavra("escuela", "escola", "espanhol"),
            new Palavra("manzana", "maca", "espanhol"),
            new Palavra("sol", "sol", "espanhol")
    ));

    public static void main(String[] args) {
        new TradutorReverso().iniciar();
    }

    public void iniciar() {
        System.out.println("=== TRADUTOR REVERSO ===");
        System.out.println("1 - Ingles -> Portugues");
        System.out.println("2 - Espanhol -> Portugues");
        System.out.println("3 - Misto");
        System.out.print("Escolha: ");
        String opcao = entrada.nextLine().trim();

        List<Palavra> rodada;
        switch (opcao) {
            case "1":
                rodada = filtrar("ingles");
                break;
            case "2":
                rodada = filtrar("espanhol");
                break;
            default:
                rodada = new ArrayList<>(banco);
        }

        System.out.print("Quantas palavras? ");
        int qtd;
        try {
            qtd = Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            qtd = 5;
        }
        if (qtd > rodada.size()) {
            qtd = rodada.size();
        }

        Collections.shuffle(rodada);
        int acertos = 0;

        for (int i = 0; i < qtd; i++) {
            Palavra p = rodada.get(i);
            System.out.printf("%n[%d/%d] (%s) Traduza: %s%n", i + 1, qtd, p.getIdioma(), p.getOriginal());
            System.out.print("Resposta: ");
            String resp = entrada.nextLine().trim().toLowerCase();
            if (resp.equals(p.getTraducao().toLowerCase())) {
                System.out.println("Correto!");
                acertos++;
            } else {
                System.out.printf("Errado. Resposta certa: %s%n", p.getTraducao());
            }
        }

        System.out.printf("%n===== FIM =====%nAcertos: %d/%d (%.1f%%)%n",
                acertos, qtd, (acertos * 100.0) / qtd);
    }

    private List<Palavra> filtrar(String idioma) {
        List<Palavra> lista = new ArrayList<>();
        for (Palavra p : banco) {
            if (p.getIdioma().equals(idioma)) {
                lista.add(p);
            }
        }
        return lista;
    }
}

