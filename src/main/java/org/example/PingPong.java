package org.example;

import java.util.Objects;
import java.util.Scanner;

// Classe principal do programa
public class PingPong {

    public static void main(String[] args) {
        // Cria um objeto Scanner para entrada de dados
        Scanner input = new Scanner(System.in);

        // Pontuação de sets para cada jogador
        int userPointSetOne = 0;
        int userPointSettwo = 0;

        // Pontuação atual do set para cada jogador
        int userpointOne = 0;
        int userpointTwo = 0;

        // Solicita e lê os nomes dos dois participantes
        System.out.print("Digite o nome do participante: ");
        String userNameOne = input.nextLine();
        System.out.print("Digite o nome do participante: ");
        String userNameTwo = input.nextLine();

        // Solicita o número de pontos por set
        System.out.print("Digite o total de pontos da partida: ");
        String totalPoints = input.nextLine();

        // Solicita o número de sets necessários para vencer a partida
        System.out.print("Digite o total de Sets da partida: ");
        String totalSets = input.nextLine();

        // Loop principal do jogo (enquanto ninguém venceu a partida)
        while (true) {
            // Loop de um set (até que um jogador atinja os pontos definidos)
            while (true) {
                System.out.print("Digite o nome de quem fez o ponto: ");
                String user = input.nextLine();

                // Verifica se o ponto foi do jogador 1
                if (Objects.equals(user, userNameOne)) {
                    userpointOne++;
                    System.out.printf("Ponto do %s\n", userNameOne);
                    System.out.println(userNameOne + ":" + userpointOne);
                    System.out.println(userNameTwo + ":" + userpointTwo);

                } else { // Senão, o ponto foi do jogador 2
                    userpointTwo++;
                    System.out.printf("Ponto do %s\n", userNameTwo);
                    System.out.println(userNameOne + ":" + userpointOne);
                    System.out.println(userNameTwo + ":" + userpointTwo);
                }

                // Verifica se algum jogador alcançou os pontos necessários para vencer o set
                if (userpointOne == Integer.parseInt(totalPoints) || userpointTwo == Integer.parseInt(totalPoints)) {
                    break; // Sai do loop do set
                }
            }

            // Verifica quem venceu o set e atualiza a contagem de sets
            if (userpointOne == Integer.parseInt(totalPoints)) {
                userPointSetOne++;  // jogador 1 vence o set
                userpointOne = 0;
                userpointTwo = 0;
                System.out.printf("setpoint do %s \n", userNameOne);
            } else {
                userPointSettwo++;  // jogador 2 vence o set
                userpointOne = 0;
                userpointTwo = 0;
                System.out.printf("setpoint do %s \n", userNameTwo);
            }

            // Exibe o placar de sets atual
            System.out.println("sets: \n");
            System.out.println(userNameOne + " " + userPointSetOne + "\n");
            System.out.println(userNameTwo + " " + userPointSettwo + "\n");

            // Verifica se algum jogador venceu a partida
            if (userPointSetOne == Integer.parseInt(totalSets) || userPointSettwo == Integer.parseInt(totalSets)) {
                break; // Sai do loop principal do jogo
            }
        }

        // Exibe o placar final de sets
        System.out.println("sets: \n");
        System.out.println(userNameOne + " " + userPointSetOne + "\n");
        System.out.println(userNameTwo + " " + userPointSettwo + "\n");
    }
}
