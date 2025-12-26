import java.util.Arrays;
import java.util.Scanner;

public class CaixaEletronico {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int[] notas = {200, 100, 50, 20, 10, 5, 2};
        int[] qntdNotaEstoque = {50, 50, 50, 50, 50, 50, 50};
        double valorTotal = 0;

        for (int i = 0; i < notas.length; i++) {
            valorTotal += notas[i] * qntdNotaEstoque[i];
        }

        System.out.printf("Nesse caixa, tem disponível: R$ %.2f%n", valorTotal);

        while (true) {
            System.out.print("Digite quanto você quer sacar: ");
            double saque = Double.parseDouble(scanner.nextLine());

            if (saque > valorTotal) {
                System.out.println("Saque indisponível! Valor maior do que disponível!");
                System.out.printf("O valor máximo permitido é: R$ %.2f%n", valorTotal);
                continue;
            }

            double resto = saque;

            for (int nota : notas) {
                if (resto / nota >= 1) {
                    int qntdNecessaria = (int) (resto / nota);
                    resto -= (qntdNecessaria * nota);
                    System.out.println("A quantidade de notas de R$ " + nota + " é de: " + qntdNecessaria + " notas");
                }
            }

            valorTotal -= saque;
            System.out.printf("Neste caixa tem R$ %.2f em notas de %s%n", valorTotal, Arrays.toString(notas));

            if (valorTotal <= 0) {
                System.out.println("Você conseguiu estourar o caixa");
                break;
            }

            System.out.print("Deseja realizar um novo saque? Sim/Não: ");
            String newSaque = scanner.nextLine().strip().toUpperCase();

            if (newSaque.equals("NAO")) {
                break;
            }
        }
        scanner.close();
    }
}
