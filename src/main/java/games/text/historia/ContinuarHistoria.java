package games.text.historia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ContinuarHistoria {

    private final Scanner entrada = new Scanner(System.in);
    private final Map<String, No> historia = new HashMap<>();

    public static void main(String[] args) {
        new ContinuarHistoria().iniciar();
    }

    public ContinuarHistoria() {
        montarHistoria();
    }

    private void montarHistoria() {
        historia.put("inicio", new No(
                "Voce acorda em uma floresta densa sem lembrar como chegou ali. Ao seu lado ha uma mochila e a sua frente dois caminhos.",
                new String[]{"Seguir pelo caminho da esquerda", "Seguir pelo caminho da direita", "Abrir a mochila"},
                new String[]{"esquerda", "direita", "mochila"}, false));

        historia.put("esquerda", new No(
                "O caminho da esquerda te leva ate uma cabana abandonada. A porta esta entreaberta.",
                new String[]{"Entrar na cabana", "Contornar a cabana"},
                new String[]{"cabana", "atrasCabana"}, false));

        historia.put("direita", new No(
                "Voce caminha por horas ate encontrar um rio largo. Ha uma pequena ponte de madeira velha.",
                new String[]{"Atravessar a ponte", "Seguir o rio"},
                new String[]{"ponte", "seguirRio"}, false));

        historia.put("mochila", new No(
                "Dentro da mochila ha um mapa antigo, uma lanterna e um bilhete: 'Confie no caminho da agua'.",
                new String[]{"Seguir o conselho do bilhete (direita)", "Ignorar e ir pela esquerda"},
                new String[]{"direita", "esquerda"}, false));

        historia.put("cabana", new No(
                "Dentro da cabana ha um homem misterioso oferecendo um cha. Ele diz saber como te tirar dali.",
                new String[]{"Aceitar o cha", "Recusar e sair correndo"},
                new String[]{"finalRuim1", "finalBom1"}, false));

        historia.put("atrasCabana", new No(
                "Atras da cabana voce encontra um portal brilhante.",
                null, null, true));

        historia.put("ponte", new No(
                "A ponte balanca mas voce consegue atravessar. Do outro lado ha uma vila acolhedora.",
                null, null, true));

        historia.put("seguirRio", new No(
                "Seguindo o rio voce chega ate uma cachoeira majestosa. Atras dela ha uma caverna iluminada.",
                new String[]{"Entrar na caverna", "Voltar"},
                new String[]{"finalBom2", "inicio"}, false));

        historia.put("finalRuim1", new No(
                "O cha tinha um sonifero. Voce nunca mais acordou. FIM RUIM.",
                null, null, true));

        historia.put("finalBom1", new No(
                "Voce escapa e encontra um grupo de exploradores que te levam de volta. FIM BOM.",
                null, null, true));

        historia.put("finalBom2", new No(
                "Dentro da caverna ha um portal que te leva de volta para casa. FIM EPICO.",
                null, null, true));
    }

    public void iniciar() {
        System.out.println("=== CONTINUAR A HISTORIA ===");
        System.out.println("Escolha suas acoes e descubra para onde a historia leva.\n");
        String atual = "inicio";
        while (true) {
            No no = historia.get(atual);
            System.out.println("\n" + no.getTexto());

            if (no.isFinal()) {
                System.out.println("\n--- FIM DA HISTORIA ---");
                return;
            }

            List<String> opcoes = no.getOpcoes();
            for (int i = 0; i < opcoes.size(); i++) {
                System.out.printf("%d - %s%n", i + 1, opcoes.get(i));
            }
            System.out.print("Escolha: ");
            String linha = entrada.nextLine().trim();
            try {
                int escolha = Integer.parseInt(linha) - 1;
                if (escolha < 0 || escolha >= opcoes.size()) {
                    System.out.println("Opcao invalida.");
                    continue;
                }
                atual = no.getDestinos().get(escolha);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero.");
            }
        }
    }
}

