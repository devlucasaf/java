package games.text.capitais;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CapitaisDoMundo {

    private final Scanner entrada = new Scanner(System.in);

    private final List<Pais> banco = Arrays.asList(
            new Pais("Brasil", "Brasilia", "America do Sul"),
            new Pais("Argentina", "Buenos Aires", "America do Sul"),
            new Pais("Chile", "Santiago", "America do Sul"),
            new Pais("Peru", "Lima", "America do Sul"),
            new Pais("Colombia", "Bogota", "America do Sul"),
            new Pais("Uruguai", "Montevideu", "America do Sul"),
            new Pais("Venezuela", "Caracas", "America do Sul"),
            new Pais("Estados Unidos", "Washington", "America do Norte"),
            new Pais("Canada", "Ottawa", "America do Norte"),
            new Pais("Mexico", "Cidade do Mexico", "America do Norte"),
            new Pais("Portugal", "Lisboa", "Europa"),
            new Pais("Espanha", "Madri", "Europa"),
            new Pais("Franca", "Paris", "Europa"),
            new Pais("Italia", "Roma", "Europa"),
            new Pais("Alemanha", "Berlim", "Europa"),
            new Pais("Reino Unido", "Londres", "Europa"),
            new Pais("Russia", "Moscou", "Europa"),
            new Pais("Holanda", "Amsterda", "Europa"),
            new Pais("Grecia", "Atenas", "Europa"),
            new Pais("Suecia", "Estocolmo", "Europa"),
            new Pais("Japao", "Toquio", "Asia"),
            new Pais("China", "Pequim", "Asia"),
            new Pais("Coreia do Sul", "Seul", "Asia"),
            new Pais("India", "Nova Deli", "Asia"),
            new Pais("Tailandia", "Bangcoc", "Asia"),
            new Pais("Vietna", "Hanoi", "Asia"),
            new Pais("Egito", "Cairo", "Africa"),
            new Pais("Africa do Sul", "Pretoria", "Africa"),
            new Pais("Nigeria", "Abuja", "Africa"),
            new Pais("Marrocos", "Rabat", "Africa"),
            new Pais("Australia", "Canberra", "Oceania"),
            new Pais("Nova Zelandia", "Wellington", "Oceania")
    );

    public static void main(String[] args) {
        new CapitaisDoMundo().iniciar();
    }

    public void iniciar() {
        System.out.println("=== CAPITAIS DO MUNDO ===");
        System.out.println("1 - Pais -> Capital");
        System.out.println("2 - Capital -> Pais");
        System.out.println("3 - Filtrar por continente");
        System.out.print("Modo: ");
        String modo = entrada.nextLine().trim();

        List<Pais> rodada;
        if ("3".equals(modo)) {
            System.out.println("Continentes: America do Sul | America do Norte | Europa | Asia | Africa | Oceania");
            System.out.print("Continente: ");
            String cont = entrada.nextLine().trim();
            rodada = banco.stream()
                    .filter(p -> normalizar(p.getContinente()).equals(normalizar(cont)))
                    .collect(Collectors.toList());
            if (rodada.isEmpty()) {
                System.out.println("Nenhum pais encontrado.");
                return;
            }
            System.out.print("1 - Pais->Capital ou 2 - Capital->Pais? ");
            modo = entrada.nextLine().trim();
        } else {
            rodada = new ArrayList<>(banco);
        }

        boolean paisParaCapital = !"2".equals(modo);

        System.out.print("Quantas perguntas? ");
        int qtd;

        try {
            qtd = Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            qtd = 10;
        }

        if (qtd > rodada.size()) {
            qtd = rodada.size();
        }

        Collections.shuffle(rodada);
        int acertos = 0;

        for (int i = 0; i < qtd; i++) {
            Pais pais = rodada.get(i);
            String enunciado = paisParaCapital ? pais.getNome() : pais.getCapital();
            String esperado = paisParaCapital ? pais.getCapital() : pais.getNome();
            String rotulo = paisParaCapital ? "Capital de" : "Pais cuja capital e";

            System.out.printf("%n[%d/%d] %s %s?%n", i + 1, qtd, rotulo, enunciado);
            System.out.print("Resposta: ");
            String resp = entrada.nextLine().trim();

            if (normalizar(resp).equals(normalizar(esperado))) {
                System.out.println("Correto!");
                acertos++;
            } else {
                System.out.println("Errado. Resposta correta: " + esperado);
            }
        }

        System.out.printf("%n===== FIM =====%nAcertos: %d/%d (%.1f%%)%n",
                acertos, qtd, (acertos * 100.0) / qtd);
    }

    private String normalizar(String s) {
        String n = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return n.toLowerCase().trim().replaceAll("\\s+", " ");
    }
}

