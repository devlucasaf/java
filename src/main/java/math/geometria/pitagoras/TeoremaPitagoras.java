package math.geometria.pitagoras;

import java.util.Scanner;

public class TeoremaPitagoras {
    // --- CALCULA A HIPOTENUSA A PARTIR DOS DOIS CATETOS ---
    public static double calcularHipotenusa(double catetoA, double catetoB) {
        return Math.sqrt(Math.pow(catetoA, 2) + Math.pow(catetoB, 2));
    }

    // --- CALCULA O CATETO DESCONHECIDO A PARTIR DA HIPOTENUSA ---
    public static double calcularCateto(double hipotenusa, double catetoConhecido) {
        if (hipotenusa <= catetoConhecido) {
            throw new IllegalArgumentException("A hipotenusa deve ser maior que o cateto conhecido.");
        }
        return Math.sqrt(Math.pow(hipotenusa, 2) - Math.pow(catetoConhecido, 2));
    }

    // --- MENU INTERATIVO PARA CÁLCULOS DO TEOREMA DE PITÁGORAS ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        System.out.println("=== TEOREMA DE PITÁGORAS ===");

        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Calcular a hipotenusa");
            System.out.println("2 - Calcular um cateto (dado o outro cateto e a hipotenusa)");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor do primeiro cateto: ");
                    double catetoOposto = scanner.nextDouble();
                    System.out.print("Digite o valor do segundo cateto: ");
                    double catetoAdjacente = scanner.nextDouble();

                    if (catetoOposto <= 0 || catetoAdjacente <= 0) {
                        System.out.println("Os catetos devem ser valores positivos.");
                    } else {
                        double hipotenusa = calcularHipotenusa(catetoOposto, catetoAdjacente);
                        System.out.printf("A hipotenusa é: %.2f%n", hipotenusa);
                    }
                    break;
                case 2:
                    System.out.print("Digite o valor da hipotenusa: ");
                    double hipotenusa = scanner.nextDouble();
                    System.out.print("Digite o valor do cateto conhecido: ");
                    double catetoConhecido = scanner.nextDouble();

                    if (hipotenusa <= 0 || catetoConhecido <= 0) {
                        System.out.println("Os valores devem ser positivos.");
                    } else {
                        try {
                            double catetoDesconhecido = calcularCateto(hipotenusa, catetoConhecido);
                            System.out.printf("O outro cateto mede: %.2f%n", catetoDesconhecido);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida! Digite 1, 2 ou 3.");
            }

        } while (opcao != 3);

        scanner.close();
    }
}

