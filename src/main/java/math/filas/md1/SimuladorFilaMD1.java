package math.filas.md1;

import java.util.*;

public class SimuladorFilaMD1 {
    private final double lambda;
    private final double mu;
    private final Random random = new Random();

    // --- DEFINE LAMBDA E MU E EXIGE SISTEMA ESTÁVEL ---
    public SimuladorFilaMD1(double lambda, double mu) {
        if (lambda >= mu) {
            throw new IllegalArgumentException("Sistema instável: lambda deve ser menor que mu.");
        }
        this.lambda = lambda;
        this.mu = mu;
    }

    // --- RETORNA RHO = LAMBDA / MU ---
    public double getFatorUtilizacao() {
        return lambda / mu;
    }

    // --- NÚMERO MÉDIO NA FILA TEÓRICO ---
    public double getNumeroMedioNaFilaTeorico() {
        double rho = getFatorUtilizacao();
        return (rho * rho) / (2 * (1 - rho));
    }

    // --- NÚMERO MÉDIO NO SISTEMA TEÓRICO ---
    public double getNumeroMedioNoSistemaTeorico() {
        return getNumeroMedioNaFilaTeorico() + getFatorUtilizacao();
    }

    // --- TEMPO MÉDIO DE ESPERA NA FILA PELA LEI DE LITTLE ---
    public double getTempoMedioEsperaNaFilaTeorico() {
        return getNumeroMedioNaFilaTeorico() / lambda;
    }

    // --- TEMPO MÉDIO NO SISTEMA ---
    public double getTempoMedioNoSistemaTeorico() {
        return getTempoMedioEsperaNaFilaTeorico() + 1.0 / mu;
    }

    // --- GERA INTERVALO EXPONENCIAL COM A TAXA INFORMADA ---
    private double gerarExponencial(double taxa) {
        return -Math.log(1 - random.nextDouble()) / taxa;
    }

    // --- SIMULA O ATENDIMENTO DE UM NÚMERO DE CLIENTES ---
    public ResultadoSimulacao simular(int totalClientes) {
        double somaEsperas = 0;
        double somaTemposNoSistema = 0;

        List<Double> chegadas = new ArrayList<>(totalClientes);
        List<Double> iniciosAtendimento = new ArrayList<>(totalClientes);
        List<Double> finsAtendimento = new ArrayList<>(totalClientes);

        double proximaChegada = 0;
        double servidorLivreEm = 0;

        for (int i = 0; i < totalClientes; i++) {
            proximaChegada += gerarExponencial(lambda);
            double inicioAtendimento = Math.max(proximaChegada, servidorLivreEm);
            double duracaoAtendimento = 1.0 / mu;
            double fimAtendimento = inicioAtendimento + duracaoAtendimento;

            double espera = inicioAtendimento - proximaChegada;
            somaEsperas += espera;
            somaTemposNoSistema += (fimAtendimento - proximaChegada);

            chegadas.add(proximaChegada);
            iniciosAtendimento.add(inicioAtendimento);
            finsAtendimento.add(fimAtendimento);

            servidorLivreEm = fimAtendimento;
        }

        // --- TAMANHO MÉDIO DA FILA PELA ÁREA SOB A CURVA ---
        List<double[]> eventos = new ArrayList<>();
        for (int i = 0; i < totalClientes; i++) {
            eventos.add(new double[]{chegadas.get(i), +1});
            eventos.add(new double[]{iniciosAtendimento.get(i), -1});
        }
        eventos.sort(Comparator.comparingDouble(e -> e[0]));

        double areaFila = 0;
        double instanteAnterior = 0;
        int clientesNaFilaAtual = 0;
        for (double[] evento : eventos) {
            areaFila += clientesNaFilaAtual * (evento[0] - instanteAnterior);
            instanteAnterior = evento[0];
            clientesNaFilaAtual += (int) evento[1];
        }

        double tempoTotalSimulado = finsAtendimento.get(finsAtendimento.size() - 1);

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.clientesAtendidos = totalClientes;
        resultado.tempoMedioEspera = somaEsperas / totalClientes;
        resultado.tempoMedioNoSistema = somaTemposNoSistema / totalClientes;
        resultado.tamanhoMedioFila = areaFila / instanteAnterior;
        return resultado;
    }

    // --- RETORNA A TAXA DE CHEGADA ---
    public double getLambda() {
        return lambda;
    }

    // --- RETORNA A TAXA DE ATENDIMENTO ---
    public double getMu() {
        return mu;
    }
}

