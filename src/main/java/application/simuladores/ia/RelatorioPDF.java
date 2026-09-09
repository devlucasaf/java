package application.simuladores.ia;

import java.io.IOException;
import java.nio.file.Path;

public class RelatorioPDF {

    // --- GERA O RELATÓRIO PDF COM OS DADOS E O GRÁFICO INFORMADOS ---
    public void gerar(AnaliseCronologica dados, Path graficoPath, Path caminhoSaida) throws IOException {
        System.out.println("Gerando relatório em: " + caminhoSaida);
    }
}
