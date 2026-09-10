package math.algebra.equacao.cubica;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class EquacaoCubica {
    private static final double EPSILON = 1e-10;

    // --- LE OS COEFICIENTES E RESOLVE A EQUAÇÃO CÚBICA ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Resolução de equação cúbica: a*x³ + b*x² + c*x + d = 0");
        System.out.print("Digite o coeficiente a (diferente de zero): ");
        double a = scanner.nextDouble();

        if (Math.abs(a) < EPSILON) {
            System.out.println("O coeficiente 'a' não pode ser zero (não é uma equação cúbica).");
            System.out.println("Tratando como equação de grau inferior...");
            resolverGrauInferior(a, scanner);
            scanner.close();
            return;
        }

        System.out.print("Digite o coeficiente b: ");
        double b = scanner.nextDouble();
        System.out.print("Digite o coeficiente c: ");
        double c = scanner.nextDouble();
        System.out.print("Digite o coeficiente d: ");
        double d = scanner.nextDouble();

        scanner.close();

        double[] raizesReais = calcularRaizesReais(a, b, c, d);
        exibirRaizes(raizesReais);
    }

    // --- RESOLVE EQUAÇÃO LINEAR OU QUADRÁTICA QUANDO A É ZERO ---
    private static void resolverGrauInferior(double a, Scanner scanner) {
        System.out.print("Digite o coeficiente b: ");
        double b = scanner.nextDouble();
        System.out.print("Digite o coeficiente c: ");
        double c = scanner.nextDouble();
        System.out.print("Digite o coeficiente d: ");
        double d = scanner.nextDouble();

        if (Math.abs(b) < EPSILON && Math.abs(c) < EPSILON) {
            if (Math.abs(d) < EPSILON) {
                System.out.println("A equação é identicamente nula (infinitas soluções).");
            } else {
                System.out.println("Equação impossível (sem solução real).");
            }
        } else if (Math.abs(b) < EPSILON) {
            double raiz = -d / c;
            System.out.printf("Raiz real única: %.6f\n", raiz);
        } else {
            double[] raizes = resolverQuadratica(b, c, d);
            exibirRaizes(raizes);
        }
    }

    // --- RESOLVE B*X^2 + C*X + D = 0 PELAS FÓRMULAS DE BHASKARA ---
    private static double[] resolverQuadratica(double b, double c, double d) {
        double delta = c * c - 4 * b * d;
        if (delta < -EPSILON) {
            return new double[0]; // --- SEM RAIZES REAIS ---
        }

        if (Math.abs(delta) < EPSILON) {
            double raiz = -c / (2 * b);
            return new double[]{raiz};
        }

        double sqrtDelta = Math.sqrt(delta);
        double raiz1 = (-c + sqrtDelta) / (2 * b);
        double raiz2 = (-c - sqrtDelta) / (2 * b);
        return new double[]{raiz1, raiz2};
    }

    // --- CALCULA AS RAÍZES REAIS PELO MÉTODO DE CARDANO ---
    public static double[] calcularRaizesReais(double a, double b, double c, double d) {
        double a2 = b / a;
        double a1 = c / a;
        double a0 = d / a;

        double p = a1 - (a2 * a2) / 3.0;
        double q = (2.0 * a2 * a2 * a2) / 27.0 - (a2 * a1) / 3.0 + a0;

        double discriminante = (q / 2.0) * (q / 2.0) + (p / 3.0) * (p / 3.0) * (p / 3.0);

        List<Double> raizes = new ArrayList<>();
        double deslocamento = -a2 / 3.0;

        if (discriminante > EPSILON) {
            double sqrtDelta = Math.sqrt(discriminante);
            double u = Math.cbrt(-q / 2.0 + sqrtDelta);
            double v = Math.cbrt(-q / 2.0 - sqrtDelta);
            double raizReal = u + v + deslocamento;
            raizes.add(raizReal);
        } else if (Math.abs(discriminante) < EPSILON) {
            double u = Math.cbrt(-q / 2.0);
            if (Math.abs(p) < EPSILON && Math.abs(q) < EPSILON) {
                double raizTripla = deslocamento;
                raizes.add(raizTripla);
            } else {
                double raiz1 = 2.0 * u + deslocamento;
                double raiz2 = -u + deslocamento;
                raizes.add(raiz1);
                if (Math.abs(raiz1 - raiz2) > EPSILON) {
                    raizes.add(raiz2);
                }
            }
        } else {
            double r = 2.0 * Math.sqrt(-p / 3.0);
            double theta = Math.acos((3.0 * q) / (2.0 * p) * Math.sqrt(-3.0 / p));
            double raiz1 = r * Math.cos(theta / 3.0) + deslocamento;
            double raiz2 = r * Math.cos((theta + 2.0 * Math.PI) / 3.0) + deslocamento;
            double raiz3 = r * Math.cos((theta + 4.0 * Math.PI) / 3.0) + deslocamento;
            raizes.add(raiz1);
            raizes.add(raiz2);
            raizes.add(raiz3);
        }

        double[] resultado = new double[raizes.size()];
        for (int i = 0; i < raizes.size(); i++) {
            resultado[i] = raizes.get(i);
        }
        return resultado;
    }

    // --- MOSTRA AS RAÍZES REAIS ENCONTRADAS ---
    private static void exibirRaizes(double[] raizes) {
        if (raizes.length == 0) {
            System.out.println("A equação não possui raízes reais.");
        } else {
            System.out.println("\nRaízes reais encontradas:");
            for (int i = 0; i < raizes.length; i++) {
                System.out.printf("x%d = %.6f\n", i + 1, raizes[i]);
            }
        }
    }
}
