package math.algebra.equacao.quadratica;

import java.util.Scanner;

public class EquacaoQuadratica {

    // --- LE OS COEFICIENTES E RESOLVE A EQUAÇÃO QUADRÁTICA ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o valor de a: ");
        double a = scanner.nextDouble();
        while (a == 0) {
            System.out.print("Coeficiente 'a' inválido para equação quadrática. Digite novamente: ");
            a = scanner.nextDouble();
        }

        System.out.print("Digite o valor de b: ");
        double b = scanner.nextDouble();

        System.out.print("Digite o valor de c: ");
        double c = scanner.nextDouble();

        double delta = calcularDelta(a, b, c);
        System.out.println("Discriminante (Δ) = " + delta);

        double[] raizes = calcularRaizes(a, b, c, delta);

        exibirResultado(delta, raizes);

        scanner.close();
    }

    // --- CALCULA O DISCRIMINANTE B^2 - 4AC ---
    public static double calcularDelta(double a, double b, double c) {
        return b * b - 4 * a * c;
    }

    // --- CALCULA AS RAÍZES REAIS OU COMPLEXAS PELA FÓRMULA DE BHASKARA ---
    public static double[] calcularRaizes(double a, double b, double c, double delta) {
        double[] raizes = new double[4];

        if (delta >= 0) {
            double raiz1 = (-b + Math.sqrt(delta)) / (2 * a);
            double raiz2 = (-b - Math.sqrt(delta)) / (2 * a);
            raizes[0] = raiz1;
            raizes[1] = 0.0;
            raizes[2] = raiz2;
            raizes[3] = 0.0;
        } else {
            double parteReal = -b / (2 * a);
            double parteImaginaria = Math.sqrt(-delta) / (2 * a);
            raizes[0] = parteReal;
            raizes[1] = parteImaginaria;
            raizes[2] = parteReal;
            raizes[3] = -parteImaginaria;
        }

        return raizes;
    }

    // --- MOSTRA AS RAÍZES DE ACORDO COM O SINAL DO DELTA ---
    public static void exibirResultado(double delta, double[] raizes) {
        if (delta > 0) {
            System.out.println("A equação possui duas raízes reais e distintas:");
            System.out.println("x₁ = " + raizes[0]);
            System.out.println("x₂ = " + raizes[2]);
        } else if (delta == 0) {
            System.out.println("A equação possui uma raiz real (dupla):");
            System.out.println("x = " + raizes[0]);
        } else {
            System.out.println("A equação possui duas raízes complexas conjugadas:");
            System.out.printf("x₁ = %.2f + %.2fi%n", raizes[0], raizes[1]);
            System.out.printf("x₂ = %.2f - %.2fi%n", raizes[2], -raizes[3]);
        }
    }
}