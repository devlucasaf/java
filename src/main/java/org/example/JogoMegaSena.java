package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

class MegaSena {
    private List<Integer> NumerosJogosAJogar;

    public List<Integer> gerarJogo (int quantidadeNumeros) {
        if (quantidadeNumeros < 6 || quantidadeNumeros > 20) {
            throw new IllegalArgumentException("Para jogar, escolha entre 6 e 20 dezenas.");
        }

        Set<Integer> selecao = new HashSet<>();
        Random random = new Random();

        while (selecao.size() < quantidadeNumeros) {
            selecao.add(random.nextInt(60) + 1);
        }

        this.NumerosJogosAJogar = new ArrayList<>(selecao);
        Collections.sort(this.NumerosJogosAJogar);

        return this.NumerosJogosAJogar;
    }

    public double custoJogoMegaSena (int tamanho) {
        return switch (tamanho) {
            case 6 -> 5.00;
            case 7 -> 35.00;
            case 8 -> 140.00;
            case 9 -> 420.00;
            case 10 -> 1050.00;
            case 11 -> 2310.00;
            case 12 -> 4620.00;
            case 13 -> 8580.00;
            case 14 -> 15015.00;
            case 15 -> 25035.00;
            case 16 -> 40040.00;
            case 17 -> 61880.00;
            case 18 -> 92820.00;
            case 19 -> 135600.00;
            case 20 -> 193800.00;
            default -> 0.0;
        };
    }

    public void gravarJogos() {
        try (FileWriter fileWriter = new FileWriter("mega-teste.txt", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("     Jogo para a Mega da Virada     ");
            printWriter.println("                |                   ");
            printWriter.println("------------------------------------");
            printWriter.println("    " + this.NumerosJogosAJogar);
            printWriter.println("------------------------------------");
            printWriter.printf("- Valor total: R$%.2f ------\n", custoJogoMegaSena(this.NumerosJogosAJogar.size()));
            printWriter.println("\n");

        }
        catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }
}

public class JogoMegaSena {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MegaSena ms = new MegaSena();

        System.out.print("Digite quantos jogos de Mega Sena quer fazer: ");
        int numJogos = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numJogos; i++) {
            System.out.println("\nNovo jogo!");
            System.out.print("Digite a quantidade de dezenas (6-20): ");
            int qtd = Integer.parseInt(scanner.nextLine());

            try {
                List<Integer> jogo = ms.gerarJogo(qtd);
                System.out.printf("Seu jogo está pronto! Números: %s\n", jogo);
                System.out.printf("Custo da aposta: R$%.2f\n", ms.custoJogoMegaSena(qtd));
                ms.gravarJogos();
            }
            catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
