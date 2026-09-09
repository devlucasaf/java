package application.outros.renda;

import java.util.Locale;
import java.util.Scanner;

public class CalculadoraRenda {

    // --- LE O SALÁRIO, CALCULA OS DESCONTOS E EXIBE O RESULTADO ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.println("=== CALCULADORA DE IMPOSTO DE RENDA E INSS ===");
        System.out.print("Informe o valor do salario bruto: R$ ");

        double salarioBruto = lerValor(scanner);
        if (salarioBruto < 0) {
            System.out.println("Salario invalido.");
            return;
        }

        double inss = calcularInss(salarioBruto);
        double impostoRenda = calcularImpostoRenda(salarioBruto);
        double salarioLiquido = salarioBruto - inss - impostoRenda;

        System.out.println();
        System.out.println("------------- RESULTADO -------------");
        System.out.printf(Locale.of("pt", "BR"), "Salario bruto:    R$ %,.2f%n", salarioBruto);
        System.out.printf(Locale.of("pt", "BR"), "INSS:             R$ %,.2f%n", inss);
        System.out.printf(Locale.of("pt", "BR"), "Imposto de Renda: R$ %,.2f%n", impostoRenda);
        System.out.println("-------------------------------------");
        System.out.printf(Locale.of("pt", "BR"), "Salario liquido:  R$ %,.2f%n", salarioLiquido);
    }

    // --- CALCULA O IMPOSTO DE RENDA CONFORME A FAIXA SALARIAL ---
    private static double calcularImpostoRenda(double salario) {
        if (salario <= 5000.00) {
            return 0.0;
        }

        if (salario <= 6500.00) {
            return (salario - 5000.00) * 0.075;
        }

        if (salario <= 8000.00) {
            return (salario - 6500.00) * 0.15;
        }

        if (salario <= 10000.00) {
            return (salario - 8000.00) * 0.225;
        }
        return (salario - 10000.00) * 0.275;
    }

    // --- CALCULA O DESCONTO DO INSS CONFORME A FAIXA SALARIAL ---
    private static double calcularInss(double salario) {
        if (salario <= 1412.00) {
            return salario * 0.075;
        }

        if (salario <= 2666.68) {
            return salario * 0.09;
        }

        if (salario <= 4000.03) {
            return salario * 0.12;
        }

        if (salario <= 8475.55) {
            return salario * 0.14;
        }
        return 8475.55 * 0.14;
    }

    // --- LE E CONVERTE O VALOR INFORMADO PELO USUARIO ---
    private static double lerValor(Scanner entrada) {
        String linha = entrada.nextLine().trim().replace(",", ".");
        try {
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

