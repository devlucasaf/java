package application.simuladores.ia;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Palavras {

    // --- REMOVE ESPAÇOS EXCEDENTES E NORMALIZA O TEXTO RECEBIDO ---
    public static String extrairInteligente(String textoBruto) {
        if (textoBruto == null || textoBruto.isBlank()) {
            return "";
        }

        String textoLimpo = textoBruto.trim().replaceAll("\\s+", " ");

        return textoLimpo;
    }

    // --- VERIFICA SE O ARQUIVO PDF EXISTE NO CAMINHO INFORMADO ---
    public static void pdf() {
        Path caminhoPdf = Path.of(
                "C:", "VSCode", "GitHub", "IA", "leitor-de-documentos-IA", "teste.pdf"
        );

        try {
            if (!Files.exists(caminhoPdf)) {
                System.out.println("Arquivo PDF nao encontrado: " + caminhoPdf);
                return;
            }
            System.out.println("PDF localizado: " + caminhoPdf);
        } catch (SecurityException e) {
            System.err.println("Sem permissão para acessar o PDF: " + e.getMessage());
        }
    }

    // --- CONSULTA E EXIBE O CONTEÚDO DE UM DOCUMENTO PÚBLICO DO GOOGLE DOCS ---
    public static void docs() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe a URL pública/exportável do Google Docs: ");
        String url = scanner.nextLine().trim();

        if (url.isBlank()) {
            System.out.println("URL inválida.");
            return;
        }

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicao = HttpRequest.newBuilder(URI.create(url)).GET().build();

        try {
            HttpResponse<String> respostaHttp = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status HTTP: " + respostaHttp.statusCode());
            System.out.println(extrairInteligente(respostaHttp.body()));
        } catch (IOException e) {
            System.err.println("Erro ao consultar o documento: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("A consulta foi interrompida.");
        } catch (IllegalArgumentException e) {
            System.err.println("URL invalida: " + e.getMessage());
        }
    }

    // --- SOLICITA O MODO DE LEITURA E EXECUTA A OPÇÃO SELECIONADA ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Qual modo deseja utilizar:\n[1] - Arquivo PDF\n[2] - Google Docs\nOpção: ");
        String opcao = scanner.nextLine().trim();

        switch (opcao) {
            case "1" -> pdf();
            case "2" -> docs();
            default -> System.out.println("Opção inválida");
        }
    }
}