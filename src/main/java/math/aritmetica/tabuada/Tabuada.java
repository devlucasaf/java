package math.aritmetica.tabuada;

import java.util.Scanner;

public class Tabuada {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um número: ");
        int numero = scanner.nextInt();

        for (int o = 1; o <= 10; o++) {
            int soma = numero + o;
            System.out.printf("%s + %s = %s \n", numero, o, soma);
        }

        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");

        for (int a=1; a<=10; a++) {
            int subtracao = numero + a;
            System.out.printf("\n%s - %s = %s\n", subtracao, numero, a);
        }

        System.out.print("+=+=" + 25);

        for (int i = 1; i <= 10; i++) {
            int multiplicacao = numero * i;
            System.out.printf("\n%s * %s = %s\n", numero, i, multiplicacao);
        }

        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");

        for (int u = 1; u <= 10; u++) {
            int divisao = numero * u;
            System.out.printf("\n%s / %s = %s\n", divisao, numero, u);
        }

        System.out.print("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
    }
}
