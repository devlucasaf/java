package org.example;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    public static void main(String[] args) {

        // Array com as opções possíveis do jogo
        String[] options = {"Pedra", "Papel", "Tesoura"};

        // Objeto para gerar uma escolha aleatória para o computador
        Random choice = new Random();

        // Gera um índice aleatório de 0 a 2
        int indexNumber = choice.nextInt(options.length);

        // Escolha do computador com base no índice gerado
        String choicePC = options[indexNumber];

        // Exibe o menu de opções para o usuário
        System.out.print("Escolha uma opção:\n");
        System.out.print("Digite [0] para sair\n");
        System.out.print("Pedra [1]\n");
        System.out.print("Papel [2]\n");
        System.out.print("Tesoura [3]\n");

        // Início do laço principal do jogo
        while (true){
            Scanner input = new Scanner(System.in); // Cria um scanner para ler a entrada do usuário
            System.out.print("Digite a sua escolha: \n");
            int num = input.nextInt(); // Lê a opção escolhida pelo usuário

            // Se o usuário digitar 0, o jogo termina
            if (num == 0){
                break;
            }

            // Se o número for inválido (fora do intervalo 1 a 3)
            if (num < 1 || num > 3){
                System.out.print("Número inválido!\n");
            }

            // Converte o número do usuário para a opção correspondente (Pedra, Papel ou Tesoura)
            String userChoice = options[num-1];

            // Verifica se houve empate
            // Verifica se a escolha do usuário é igual à escolha do computador (empate)
            if (userChoice.equals(choicePC)) {
                // Se for empate, exibe a mensagem com a jogada do PC
                System.out.printf("O pc escolheu %s! Vocês empataram\n", choicePC);
                break; // Encerra o loop do jogo (termina a partida)

            } else {
                // Caso não seja empate, verifica as condições de vitória do jogador

                // 1° Condição de vitória: Papel (usuário) embrulha Pedra (PC)
                if (userChoice.equals("Papel") && choicePC.equals("Pedra")) {
                    System.out.printf("Você ganhou! O PC escolheu: %s\n", choicePC);
                    break;

                    // 2° Condição de vitória: Tesoura (usuário) corta Papel (PC)
                } else if (userChoice.equals("Tesoura") && choicePC.equals("Papel")) {
                    System.out.printf("Você ganhou! O PC escolheu: %s\n", choicePC);
                    break;

                    // 3° Condição de vitória: Pedra (usuário) quebra Tesoura (PC)
                } else if (userChoice.equals("Pedra") && choicePC.equals("Tesoura")) {
                    System.out.printf("Você ganhou! O PC escolheu: %s\n", choicePC);
                    break;

                } else {
                    // Se nenhuma das condições acima for atendida, o jogador perde
                    System.out.printf("Você perdeu! O PC escolheu %s\n", choicePC);
                    break;
                }
            }
        }
    }
}

