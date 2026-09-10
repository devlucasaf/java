package games.sorteio.cacaniquel;

import java.util.Random;
import java.util.Scanner;

public class CacaNiquel {

    private final Random sorteador = new Random();
    private final Scanner scanner = new Scanner(System.in);

    private double saldo;
    private double jackpot = 1000.0;

    private int     rodadas;
    private int     vitorias;
    private double  maiorPremio;

    public static void main(String[] args) {
        new CacaNiquel().iniciar();
    }

    public void iniciar() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("              CAÇA-NÍQUEL TURBO               ");
        System.out.println("==============================================");
        System.out.println("  3 rolos | Pague para girar | Jackpot      ");
        System.out.println("==============================================");

        System.out.print("\nDigite seu saldo inicial: R$ ");
        saldo = lerDouble();

        while (saldo > 0) {
            System.out.printf("%nSaldo: R$ %.2f   |   Jackpot: R$ %.2f%n", saldo, jackpot);
            System.out.print("Valor da aposta (0 para sair): R$ ");
            double aposta = lerDouble();

            if (aposta <= 0) {
                break;
            }

            if (aposta > saldo) {
                System.out.println("⚠ Saldo insuficiente!");
                continue;
            }

            saldo -= aposta;
            jackpot += aposta * 0.05;
            girar(aposta);
            rodadas++;
        }

        exibirEstatisticas();
    }

    private void girar(double aposta) {
        Simbolo[] resultado = new Simbolo[3];
        for (int i = 0; i < 3; i++) {
            resultado[i] = sortearSimbolo();
        }

        animarRolos(resultado);
        double premio = calcularPremio(resultado, aposta);

        if (premio > 0) {
            saldo += premio;
            vitorias++;
            if (premio > maiorPremio) {
                maiorPremio = premio;
            }
            System.out.printf("✔ Você ganhou R$ %.2f!%n", premio);
        } else {
            System.out.println("✘ Não foi dessa vez...");
        }
    }

    private Simbolo sortearSimbolo() {
        int total = Simbolo.somaPesos();
        int sorteado = sorteador.nextInt(total);
        int acumulado = 0;
        for (Simbolo s : Simbolo.values()) {
            acumulado += s.getPeso();
            if (sorteado < acumulado) {
                return s;
            }
        }
        return Simbolo.CEREJA;
    }

    private void animarRolos(Simbolo[] resultado) {
        try {
            System.out.println();
            for (int passos = 0; passos < 8; passos++) {
                Simbolo simbolo1 = sortearSimbolo();
                Simbolo simbolo2 = sortearSimbolo();
                Simbolo simbolo3 = sortearSimbolo();
                System.out.print("\r  |  " + simbolo1.getEmoji() + "  |  " + simbolo2.getEmoji()
                        + "  |  " + simbolo3.getEmoji() + "  |  ");
                Thread.sleep(120);
            }
            System.out.print("\r  |  " + resultado[0].getEmoji() + "  |  " + resultado[1].getEmoji()
                    + "  |  " + resultado[2].getEmoji() + "  |  \n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private double calcularPremio(Simbolo[] resultado, double aposta) {
        if (resultado[0] == resultado[1] && resultado[1] == resultado[2]) {
            if (resultado[0] == Simbolo.SETE) {
                double premio = jackpot;
                System.out.println("\nJACKPOT! TRÊS SETES!");
                jackpot = 1000.0;
                return premio;
            }
            return aposta * resultado[0].getMultiplicador();
        }

        if (resultado[0] == resultado[1] || resultado[1] == resultado[2] || resultado[0] == resultado[2]) {
            Simbolo repetido = (resultado[0] == resultado[1] || resultado[0] == resultado[2])
                    ? resultado[0] : resultado[1];
            return aposta * (repetido.getMultiplicador() / 4.0);
        }
        return 0;
    }

    private void exibirEstatisticas() {
        System.out.println();
        System.out.println("============== ESTATÍSTICAS ==============");
        System.out.printf ("  Rodadas jogadas: %d%n", rodadas);
        System.out.printf ("  Vitórias: %d  (%.1f%%)%n",
                vitorias, rodadas == 0 ? 0 : 100.0 * vitorias / rodadas);
        System.out.printf ("  Maior prêmio: R$ %.2f%n", maiorPremio);
        System.out.printf ("  Saldo final: R$ %.2f%n", saldo);
        System.out.println("==========================================");
        System.out.println("Volte sempre!");
    }

    private double lerDouble() {
        while (true) {
            String linha = scanner.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido, digite novamente: R$ ");
            }
        }
    }
}

