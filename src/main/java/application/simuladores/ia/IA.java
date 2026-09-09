package application.simuladores.ia;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IA {

    // --- VALIDA A CHAVE DA API E REALIZA A ANÁLISE DO ARQUIVO PDF ---
    public static AnaliseCronologica analise(Path caminhoPdf) throws IOException {
        String apiKey = System.getenv("GEMINI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Defina a variavel de ambiente GEMINI_API_KEY.");
        }

        return new AnaliseCronologica(new ArrayList<>(), "", "");
    }

    // --- RECEBE OS DADOS DA ANÁLISE PARA GERAR UM GRÁFICO DE BARRAS ---
    public static void graficoBarras(AnaliseCronologica dados, Path caminhoSaida) {
        List<AnaliseValores> historico = dados.getHistorico();
        System.out.println("Itens recebidos para o gráfico de barras: " + historico.size());
    }

    // --- RECEBE OS DADOS DA ANÁLISE PARA GERAR UM GRÁFICO DE PIZZA ---
    public static void graficoPizza(AnaliseCronologica dados, Path caminhoSaida) {
        List<AnaliseValores> historico = dados.getHistorico();
        System.out.println("Itens recebidos para o gráfico de pizza: " + historico.size());
    }

    // --- GERA O RELATÓRIO PDF COM OS DADOS E O GRÁFICO INFORMADO ---
    public static void gerarPdf(AnaliseCronologica dados, Path graficoPath) throws IOException {
        RelatorioPDF pdf = new RelatorioPDF();
        pdf.gerar(dados, graficoPath, Path.of("Relatorio_Final.pdf"));
    }

    // --- EXECUTA A ANÁLISE, GERA O GRÁFICO E CRIA O RELATÓRIO FINAL ---
    public static void main(String[] args) {
        Path caminhoPdf = Path.of("C:", "VSCode", "GitHub", "IA", "leitor-de-documentos-ia", "teste_pizza.pdf");

        try {
            AnaliseCronologica dados = analise(caminhoPdf);
            Path grafico = Path.of("Grafico_Pizza.png");
            graficoPizza(dados, grafico);
            gerarPdf(dados, grafico);
        } catch (IOException | IllegalStateException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
