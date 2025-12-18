package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

class Senha {
    private String app;
    private String senhaFinal;
    private Random random = new Random();

    private static final String LETRAS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String PONTUACAO = "!#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    private static final String unirTodosAtributos = LETRAS + NUMEROS + PONTUACAO;

    public void setNomeAPP(String nome) {
        this.app = nome;
    }

    public String gerarSenha (int tamanho){
        if (tamanho <= 4) {
            System.out.print("A senha precisa conter no mínimo 5 caracteres!");
            return null;
        }

        List<Character> listaSenha = new ArrayList<>();

        listaSenha.add(LETRAS.charAt(random.nextInt(LETRAS.length())));
        listaSenha.add(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        listaSenha.add(PONTUACAO.charAt(random.nextInt(PONTUACAO.length())));

        for (int i = 0; i < (tamanho - 3); i++) {
            listaSenha.add(unirTodosAtributos.charAt(random.nextInt(unirTodosAtributos.length())));
        }

        Collections.shuffle(listaSenha);

        StringBuilder stringBuilder = new StringBuilder();
        for (char c : listaSenha) {
            stringBuilder.append(c);
        }
        this.senhaFinal = stringBuilder.toString();

        return this.senhaFinal;
    }

    public void gravarSenha() {
        try (FileWriter fileWriter = new FileWriter("não entre.txt", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("       Senhas para login        \n");
            printWriter.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
            printWriter.println("APP/Site: " + this.app + "\n");
            printWriter.println("Senha: " + this.senhaFinal + "\n");
            printWriter.println("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n");
            printWriter.println("\n");
            printWriter.println();

        }
        catch (IOException ioException) {
            System.out.print("Erro ao gravar arquivo: " + ioException.getMessage());
        }
    }
}

public class GeradorSenha {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Senha s = new Senha();

        System.out.print("Digite o número de sites ou apps: ");
        int nomeAplicativos = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < nomeAplicativos; i++) {
            System.out.print("Digite o nome do app/site: ");
            s.setNomeAPP(scanner.nextLine());

            System.out.print("Digite o tamanho da senha: ");
            int comprimento = Integer.parseInt(scanner.nextLine());

            String senha = s.gerarSenha(comprimento);
            if (senha != null) {
                System.out.println("Senha gerada com sucesso! Sua senha: " + senha);
                s.gravarSenha();
            }
        }
        scanner.close();
    }
}
