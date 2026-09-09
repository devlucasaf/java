package application.exercicios.basico.pipeline;

import application.exercicios.basico.pipeline.entity.Funcionario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o caminho completo do arquivo: ");

        String path = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            List<Funcionario> lista = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                String[] campos = line.split(",");

                String nome = campos[0];
                String email = campos[1];
                Double salario = Double.parseDouble(campos[2]);

                lista.add(new Funcionario(nome, email, salario));

                line = br.readLine();
            }

            System.out.print("Digite o salário: ");
            double salario = scanner.nextDouble();

            List<String> emails = lista.stream()
                    .filter(e -> e.getSalario() > salario)
                    .map(e -> e.getEmail())
                    .collect(Collectors.toList());

            System.out.println("E-mails das pessoas cujo salário é maior que "
                            + String.format("%.2f", salario)
                            + ":"
            );

            emails.forEach(System.out::println);

            double sum = lista.stream()
                    .filter(e -> e.getNome().startsWith("M"))
                    .map(e -> e.getSalario())
                    .reduce(0.0, (x, y) -> x + y);

            System.out.println("Soma dos salários das pessoas cujo nome começa com 'M': " + String.format("%.2f", sum));
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        scanner.close();
    }
}
