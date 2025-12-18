package org.example;

import java.util.Scanner;  // Importa a classe Scanner para ler entrada do usuário

public class MultiplicationTable {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);  // Cria um objeto Scanner para entrada
        System.out.print("Digite um número: ");  // Solicita um número ao usuário
        int number = input.nextInt();  // Armazena o número digitado na variável num

        // Primeiro bloco: TABELA DE ADIÇÃO
        for (int o=1; o<=10; o++) {  // Loop de 1 a 10
            int sum = number + o;  // Calcula a soma do número com o contador
            System.out.printf("%s + %s = %s \n", num, o, sum);  // Exibe a operação de soma
        }

        // Linha separadora
        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");

        // Segundo bloco: TABELA DE SUBTRAÇÃO (com erro lógico)
        for (int a=1; a<=10; a++) {
            int sub = number + a;  // ERRO: Aqui deveria ser subtração, mas está somando
            System.out.printf("\n%s - %s = %s\n", sub, number, a);  // Exibe como subtração (resultado incorreto)
        }

        // Linha separadora parcial com número 25 (não está claro o propósito)
        System.out.print("+=+=" + 25);

        // Terceiro bloco: TABELA DE MULTIPLICAÇÃO
        for (int i=1; i<=10; i++) {
            int mult = number * i;  // Calcula a multiplicação
            System.out.printf("\n%s * %s = %s\n", number, i, mult);  // Exibe a operação
        }

        // Linha separadora
        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");

        // Quarto bloco: TABELA DE DIVISÃO (com abordagem inversa)
        for (int u=1; u<=10; u++) {
            int div = number * u;  // Calcula o numerador (divisão inversa)
            System.out.printf("\n%s / %s = %s\n", div, number, u);  // Exibe a divisão que sempre resulta no contador
        }

        // Linha separadora final
        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
    }
}
