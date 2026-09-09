package application.simuladores.trafego;

import java.util.*;

public class SimuladorTrafego {
    private final int       tamanhoVia;
    private final int       posicaoSemaforo;
    private final Semaforo  semaforo;

    private final List<Carro>   carros = new ArrayList<>();
    private final Random        random = new Random();

    private int                 proximoId = 1;
    private int                 ticksAtuais = 0;
    private int                 totalCarrosGerados = 0;
    private int                 totalCarrosQuePassaram = 0;
    private final Set<Integer>  idsJaContabilizados = new HashSet<>();

    // --- DEFINE O TAMANHO DA VIA, A POSIÇÃO E O SEMÁFORO ---
    public SimuladorTrafego(int tamanhoVia, int posicaoSemaforo, Semaforo semaforo) {
        this.tamanhoVia = tamanhoVia;
        this.posicaoSemaforo = posicaoSemaforo;
        this.semaforo = semaforo;
    }

    // --- EXECUTA A SIMULACAO POR UM NÚMERO DE TICKS ---
    public void simular(int totalTicks, double probabilidadeNovoCarro) {
        for (int t = 0; t < totalTicks; t++) {
            ticksAtuais = t;
            semaforo.atualizar();

            if (random.nextDouble() < probabilidadeNovoCarro) {
                gerarCarro();
            }

            atualizarCarros();
            removerCarrosQueSairam();

            if (t % 10 == 0) {
                imprimirEstadoVia();
            }
        }

        imprimirResumo();
    }

    // --- CRIA UM CARRO NO INÍCIO DA VIA COM VELOCIDADE ALEATÓRIA ---
    private void gerarCarro() {
        int velocidadeMaxima = 2 + random.nextInt(3);
        carros.add(new Carro(proximoId++, 0, velocidadeMaxima));
        totalCarrosGerados++;
    }

    // --- MOVE CADA CARRO RESPEITANDO O CARRO DA FRENTE E O SEMÁFORO ---
    private void atualizarCarros() {
        carros.sort((a, b) -> Integer.compare(b.getPosicao(), a.getPosicao()));

        for (int i = 0; i < carros.size(); i++) {
            Carro carro = carros.get(i);
            int distanciaLivre;

            if (i == 0) {
                if (carro.getPosicao() < posicaoSemaforo && !semaforo.podePassar()) {
                    distanciaLivre = posicaoSemaforo - carro.getPosicao() - 1;
                } else {
                    distanciaLivre = tamanhoVia - carro.getPosicao();
                }
            } else {
                Carro carroDaFrente = carros.get(i - 1);
                distanciaLivre = carroDaFrente.getPosicao() - carro.getPosicao() - 1;

                if (carro.getPosicao() < posicaoSemaforo && posicaoSemaforo <= carroDaFrente.getPosicao()
                        && !semaforo.podePassar()) {
                    distanciaLivre = Math.min(distanciaLivre, posicaoSemaforo - carro.getPosicao() - 1);
                }
            }

            carro.mover(Math.max(0, distanciaLivre), semaforo.podePassar());

            if (carro.getPosicao() >= posicaoSemaforo && idsJaContabilizados.add(carro.getId())) {
                totalCarrosQuePassaram++;
            }
        }
    }

    // --- REMOVE CARROS QUE JA SAIRAM DO FIM DA VIA ---
    private void removerCarrosQueSairam() {
        carros.removeIf(c -> c.getPosicao() >= tamanhoVia);
    }

    // --- DESENHA A PISTA COM CARROS E O SEMAFORO ---
    private void imprimirEstadoVia() {
        char[] pista = new char[tamanhoVia];
        Arrays.fill(pista, '.');

        if (posicaoSemaforo < tamanhoVia) {
            pista[posicaoSemaforo] = semaforo.podePassar() ? 'V' : '|';
        }

        for (Carro carro : carros) {
            if (carro.getPosicao() >= 0 && carro.getPosicao() < tamanhoVia) {
                pista[carro.getPosicao()] = 'C';
            }
        }

        System.out.println("Tick " + ticksAtuais + " [" + new String(pista) + "] semaforo=" + semaforo.getEstado());
    }

    // --- MOSTRA ESTATISTICAS FINAIS DA SIMULACAO ---
    private void imprimirResumo() {
        System.out.println();
        System.out.println("=== Resumo da simulacao ===");
        System.out.println("Carros gerados: " + totalCarrosGerados);
        System.out.println("Carros que passaram pelo semaforo: " + totalCarrosQuePassaram);
        System.out.println("Carros ainda na via: " + carros.size());
        if (ticksAtuais > 0) {
            double fluxoMedio = (double) totalCarrosQuePassaram / ticksAtuais;
            System.out.printf("Fluxo medio: %.3f carros/tick%n", fluxoMedio);
        }
    }
}

