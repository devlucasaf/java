package application.simuladores.trafego;

public class Carro {

    private final int   id;
    private int         posicao;
    private int         velocidade;
    private final int   velocidadeMaxima;
    private int         ticksParado;

    public Carro(int id, int posicaoInicial, int velocidadeMaxima) {
        this.id = id;
        this.posicao = posicaoInicial;
        this.velocidadeMaxima = velocidadeMaxima;
        this.velocidade = velocidadeMaxima;
        this.ticksParado = 0;
    }

    // --- RETORNA O IDENTIFICADOR DO CARRO ---
    public int getId() {
        return id;
    }

    // --- RETORNA A POSIÇÃO ATUAL DO CARRO ---
    public int getPosicao() {
        return posicao;
    }

    // --- ATUALIZA A VELOCIDADE E AVANÇA CONFORME O ESPAÇO LIVRE E O SEMÁFORO ---
    public void mover(int distanciaLivre, boolean semaforoLiberado) {
        if (velocidade < velocidadeMaxima) {
            velocidade++;
        }

        int distanciaMaximaPermitida = semaforoLiberado ? distanciaLivre : Math.max(0, distanciaLivre);
        int deslocamento = Math.min(velocidade, distanciaMaximaPermitida);

        if (deslocamento == 0) {
            ticksParado++;
            velocidade = 0;
        }

        posicao += deslocamento;
    }

    // --- RETORNA A VELOCIDADE ATUAL ---
    public int getVelocidade() {
        return velocidade;
    }

    // --- RETORNA QUANTOS TICKS O CARRO FICOU PARADO ---
    public int getTicksParado() {
        return ticksParado;
    }
}
