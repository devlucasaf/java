package org.example;

// Classe principal que identifica números pares e ímpares
public class NumerosParImpar {

    // Método principal de execução do programa
    public static void main(String[] args) {

        // 1. Exibe números de 1 a 10
        System.out.println("Números de 1 a 10:");
        // Loop for para imprimir números de 1 a 10
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " "); // Imprime o número atual + espaço
        }
        System.out.println(); // Pula linha após o loop

        // 2. Exibe números pares de 1 a 50
        System.out.println("Números pares de 1 a 50:\n");
        int number = 1; // Inicializa contador
        // Loop while para percorrer até 50
        while (number <= 50) {
            // Verifica se o número é par (resto da divisão por 2 = 0)
            if (number % 2 == 0) {
                System.out.print(number + " "); // Imprime o par
            }
            number++; // Incrementa o contador
        }

        // 3. Exibe números ímpares de 1 a 50 (OBS: título está errado, diz "pares" novamente)
        System.out.println("Números pares de 1 a 50:\n"); // TÍTULO INCORRETO (deveria ser "ímpares")
        int impar = 1; // Inicializa outro contador
        // Loop while para percorrer até 50
        while (impar <= 50) {
            // Verifica se o número é ímpar (resto da divisão por 2 ≠ 0)
            if (impar % 2 != 0) {
                System.out.print(impar + " "); // Imprime o ímpar
            }
            impar++; // Incrementa o contador
        }
    }
}