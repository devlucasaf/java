package games.text.trivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Trivia {

    private final Scanner entrada = new Scanner(System.in);

    private final List<Pergunta> banco = Arrays.asList(
            new Pergunta("CINEMA", "Quem dirigiu o filme Pulp Fiction?",
                    new String[]{"Steven Spielberg", "Quentin Tarantino", "Christopher Nolan", "Martin Scorsese"}, 1),
            new Pergunta("CINEMA", "Em que ano foi lancado o primeiro filme Matrix?",
                    new String[]{"1997", "1998", "1999", "2000"}, 2),
            new Pergunta("CINEMA", "Qual filme ganhou o Oscar de Melhor Filme em 2020?",
                    new String[]{"1917", "Parasita", "Joker", "Coringa"}, 1),
            new Pergunta("CINEMA", "Quem interpreta Tony Stark no MCU?",
                    new String[]{"Chris Evans", "Mark Ruffalo", "Robert Downey Jr", "Chris Hemsworth"}, 2),
            new Pergunta("CINEMA", "Qual a franquia de filmes mais lucrativa de todos os tempos?",
                    new String[]{"Star Wars", "Harry Potter", "MCU", "James Bond"}, 2),

            new Pergunta("MUSICA", "Qual banda lancou o album The Dark Side of the Moon?",
                    new String[]{"Led Zeppelin", "Pink Floyd", "The Beatles", "Queen"}, 1),
            new Pergunta("MUSICA", "Quem e conhecido como o Rei do Pop?",
                    new String[]{"Elvis Presley", "Michael Jackson", "Prince", "Freddie Mercury"}, 1),
            new Pergunta("MUSICA", "Quantos integrantes tem o BTS?",
                    new String[]{"5", "6", "7", "8"}, 2),
            new Pergunta("MUSICA", "Qual desses NAO e instrumento de corda?",
                    new String[]{"Violino", "Harpa", "Flauta", "Cavaquinho"}, 2),
            new Pergunta("MUSICA", "Quem compos a Nona Sinfonia?",
                    new String[]{"Mozart", "Bach", "Beethoven", "Chopin"}, 2),

            new Pergunta("GAMES", "Quem e o personagem principal de The Legend of Zelda?",
                    new String[]{"Zelda", "Link", "Ganon", "Sheik"}, 1),
            new Pergunta("GAMES", "Qual empresa criou o console PlayStation?",
                    new String[]{"Microsoft", "Nintendo", "Sega", "Sony"}, 3),
            new Pergunta("GAMES", "Em que ano foi lancado o Minecraft?",
                    new String[]{"2009", "2011", "2013", "2015"}, 1),
            new Pergunta("GAMES", "Qual jogo popularizou o genero Battle Royale?",
                    new String[]{"Fortnite", "PUBG", "Apex Legends", "Call of Duty"}, 1),
            new Pergunta("GAMES", "Qual personagem da Nintendo aparece em mais jogos?",
                    new String[]{"Link", "Samus", "Mario", "Donkey Kong"}, 2)
    );

    public static void main(String[] args) {
        new Trivia().iniciar();
    }

    public void iniciar() {
        System.out.println("=== TRIVIA CINEMA / MUSICA / GAMES ===");
        System.out.println("1 - Apenas CINEMA");
        System.out.println("2 - Apenas MUSICA");
        System.out.println("3 - Apenas GAMES");
        System.out.println("4 - Misto");
        System.out.print("Categoria: ");
        String opcao = entrada.nextLine().trim();

        List<Pergunta> rodada;
        switch (opcao) {
            case "1":
                rodada = filtrar("CINEMA");
                break;
            case "2":
                rodada = filtrar("MUSICA");
                break;
            case "3":
                rodada = filtrar("GAMES");
                break;
            default:
                rodada = new ArrayList<>(banco);
        }

        System.out.print("Quantas perguntas? ");
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
            Pergunta pergunta = rodada.get(i);
            System.out.printf("%n[%d/%d] (%s) %s%n", i + 1, qtd, pergunta.getCategoria(), pergunta.getEnunciado());
            for (int j = 0; j < pergunta.getAlternativas().size(); j++) {
                System.out.printf("  %d - %s%n", j + 1, pergunta.getAlternativas().get(j));
            }
            System.out.print("Resposta: ");
            try {
                int resposta = Integer.parseInt(entrada.nextLine().trim()) - 1;
                if (resposta == pergunta.getIndiceCorreto()) {
                    System.out.println("Correto!");
                    acertos++;
                } else {
                    System.out.println("Errado. Resposta correta: " + pergunta.getRespostaCorreta());
                }
            } catch (NumberFormatException e) {
                System.out.println("Resposta invalida. Considerada errada.");
            }
        }

        System.out.printf("%n===== FIM =====%nAcertos: %d/%d (%.1f%%)%n",
                acertos, qtd, (acertos * 100.0) / qtd);
    }

    private List<Pergunta> filtrar(String categoria) {
        return banco.stream().filter(p -> p.getCategoria().equals(categoria)).collect(Collectors.toList());
    }
}

