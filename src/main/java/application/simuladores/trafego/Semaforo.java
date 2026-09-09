package application.simuladores.trafego;

public class Semaforo {
    private Estado      estado;
    private final int   duracaoVerde;
    private final int   duracaoAmarelo;
    private final int   duracaoVermelho;
    private int         contador;

    // --- DEFINE AS DURAÇÕES E O ESTADO INICIAL DO SEMÁFORO ---
    public Semaforo(int duracaoVerde, int duracaoAmarelo, int duracaoVermelho, Estado estadoInicial) {
        this.duracaoVerde = duracaoVerde;
        this.duracaoAmarelo = duracaoAmarelo;
        this.duracaoVermelho = duracaoVermelho;
        this.estado = estadoInicial;
        this.contador = 0;
    }

    // --- AVANÇA UM TICK E TROCA DE ESTADO QUANDO A DURAÇÃO ACABA ---
    public void atualizar() {
        contador++;
        switch (estado) {
            case VERDE:
                if (contador >= duracaoVerde) {
                    estado = Estado.AMARELO;
                    contador = 0;
                }
                break;
            case AMARELO:
                if (contador >= duracaoAmarelo) {
                    estado = Estado.VERMELHO;
                    contador = 0;
                }
                break;
            case VERMELHO:
                if (contador >= duracaoVermelho) {
                    estado = Estado.VERDE;
                    contador = 0;
                }
                break;
        }
    }

    // --- INDICA SE OS CARROS PODEM PASSAR ---
    public boolean podePassar() {
        return estado == Estado.VERDE;
    }

    // --- RETORNA O ESTADO ATUAL DO SEMAFORO ---
    public Estado getEstado() {
        return estado;
    }
}
