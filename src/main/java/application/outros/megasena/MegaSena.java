package application.outros.megasena;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class MegaSena {
    private List<Integer> numerosJogosAJogar;

    // --- GERA UM JOGO COM DEZENAS ALEATÓRIAS ENTRE 1 E 60 ---
    public List<Integer> gerarJogo (int quantidadeNumeros) {
        if (quantidadeNumeros < 6 || quantidadeNumeros > 20) {
            throw new IllegalArgumentException("Para jogar, escolha entre 6 e 20 dezenas.");
        }

        Set<Integer> selecao = new HashSet<>();
        Random random = new Random();

        // --- GERA AS DEZENAS ATÉ ATINGIR A QUANTIDADE SOLICITADA ---
        while (selecao.size() < quantidadeNumeros) {
            selecao.add(random.nextInt(60) + 1);
        }

        this.numerosJogosAJogar = new ArrayList<>(selecao);
        Collections.sort(this.numerosJogosAJogar);

        return this.numerosJogosAJogar;
    }

    // --- CALCULA O CUSTO DO JOGO CONFORME A QUANTIDADE DE DEZENAS ---
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

    // --- GRAVA O JOGO E O VALOR TOTAL EM UM ARQUIVO DE TEXTO ---
    public void gravarJogos() {
        try (FileWriter fileWriter = new FileWriter("mega-teste.txt", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("     Jogo para a Mega da Virada     ");
            printWriter.println("                |                   ");
            printWriter.println("------------------------------------");
            printWriter.println("    " + this.numerosJogosAJogar);
            printWriter.println("------------------------------------");
            printWriter.printf("- Valor total: R$%.2f ------\n", custoJogoMegaSena(this.numerosJogosAJogar.size()));
            printWriter.println("\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }
}


