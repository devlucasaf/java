package math.aritmetica.bases;

import java.util.Scanner;

public class ConversorBases {
    public static String decimalParaBinario(int decimal) {
        if (decimal == 0) {
            return "0";
        }

        StringBuilder resultado = new StringBuilder();
        int numero = Math.abs(decimal);
        while (numero > 0) {
            resultado.insert(0, numero % 2);
            numero /= 2;
        }
        return (decimal < 0 ? "-" : "") + resultado;
    }

    // --- CONVERTE DECIMAL PARA OCTAL POR DIVISÕES SUCESSIVAS POR 8 ---
    public static String decimalParaOctal(int decimal) {
        if (decimal == 0) {
            return "0";
        }
        StringBuilder resultado = new StringBuilder();
        int numero = Math.abs(decimal);
        while (numero > 0) {
            resultado.insert(0, numero % 8);
            numero /= 8;
        }
        return (decimal < 0 ? "-" : "") + resultado;
    }

    // --- CONVERTE DECIMAL PARA HEXADECIMAL POR DIVISÕES SUCESSIVAS POR 16 ---
    public static String decimalParaHexadecimal(int decimal) {
        if (decimal == 0) {
            return "0";
        }

        char[] hexDigitos = "0123456789ABCDEF".toCharArray();
        StringBuilder resultado = new StringBuilder();
        int num = Math.abs(decimal);
        while (num > 0) {
            resultado.insert(0, hexDigitos[num % 16]);
            num /= 16;
        }
        return (decimal < 0 ? "-" : "") + resultado;
    }

    // --- CONVERTE BINÁRIO PARA DECIMAL ---
    public static int binarioParaDecimal(String binario) {
        return Integer.parseInt(binario, 2);
    }

    // --- CONVERTE OCTAL PARA DECIMAL ---
    public static int octalParaDecimal(String octal) {
        return Integer.parseInt(octal, 8);
    }

    // --- CONVERTE HEXADECIMAL PARA DECIMAL ---
    public static int hexadecimalParaDecimal(String hexadecimal) {
        return Integer.parseInt(hexadecimal, 16);
    }

    // --- CONVERTE DE QUALQUER BASE PARA OUTRA PASSANDO PELO DECIMAL ---
    public static String converter(String numero, int baseOrigem, int baseDestino) {
        int decimal = Integer.parseInt(numero, baseOrigem);
        return Integer.toString(decimal, baseDestino).toUpperCase();
    }

    // --- MENU INTERATIVO DAS CONVERSÕES DE BASE ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("      CONVERSOR DE BASES NUMÉRICAS        ");
            System.out.println("  1. Decimal  → Binário                   ");
            System.out.println("  2. Decimal  → Octal                     ");
            System.out.println("  3. Decimal  → Hexadecimal               ");
            System.out.println("  4. Binário  → Decimal                   ");
            System.out.println("  5. Octal    → Decimal                   ");
            System.out.println("  6. Hexadecimal → Decimal                ");
            System.out.println("  7. Conversão livre (base X → base Y)    ");
            System.out.println("  0. Sair                                 ");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o número decimal: ");
                    int num = sc.nextInt();
                    System.out.printf("Binário: %s%n%n", decimalParaBinario(num));
                }
                case 2 -> {
                    System.out.print("Digite o número decimal: ");
                    int num = sc.nextInt();
                    System.out.printf("Octal: %s%n%n", decimalParaOctal(num));
                }
                case 3 -> {
                    System.out.print("Digite o número decimal: ");
                    int num = sc.nextInt();
                    System.out.printf("Hexadecimal: %s%n%n", decimalParaHexadecimal(num));
                }
                case 4 -> {
                    System.out.print("Digite o número binário: ");
                    String num = sc.next();
                    System.out.printf("Decimal: %d%n%n", binarioParaDecimal(num));
                }
                case 5 -> {
                    System.out.print("Digite o número octal: ");
                    String num = sc.next();
                    System.out.printf("Decimal: %d%n%n", octalParaDecimal(num));
                }
                case 6 -> {
                    System.out.print("Digite o número hexadecimal: ");
                    String num = sc.next();
                    System.out.printf("Decimal: %d%n%n", hexadecimalParaDecimal(num));
                }
                case 7 -> {
                    System.out.print("Digite o número: ");
                    String num = sc.next();
                    System.out.print("Base de origem (2-16): ");
                    int baseOrigem = sc.nextInt();
                    System.out.print("Base de destino (2-16): ");
                    int baseDestino = sc.nextInt();
                    System.out.printf("Resultado: %s%n%n", converter(num, baseOrigem, baseDestino));
                }
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }
}

