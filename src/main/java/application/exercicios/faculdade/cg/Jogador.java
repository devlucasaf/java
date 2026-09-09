package application.exercicios.faculdade.cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Jogador extends Entidade {
    private float   vy;
    private Image   spriteSheet;
    private int     qtdQuadros;
    private int     quadroAtual;
    private double  temporizadorQuadro;
    private double  tempoQuadro;

    private boolean noChao;

    public Jogador(float x, float y, float largura, float altura) {
        super(x, y, largura, altura);
        this.vy = 0;
        this.qtdQuadros = 1;
        this.quadroAtual = 0;
        this.temporizadorQuadro = 0;
        this.tempoQuadro = 0.1;
        this.noChao = false;
    }

    public void setSpriteSheet(Image imagem, int quadros) {
        this.spriteSheet = imagem;
        this.qtdQuadros = (quadros > 0) ? quadros : 1;
    }

    public void atualizarFisica(double dt) {
        vy += Constantes.GRAVIDADE * dt;
        y += vy * dt;

        float metadeAltura = altura / 2;
        float base = y + metadeAltura;
        if (base >= Constantes.Y_CHAO) {
            y = Constantes.Y_CHAO - metadeAltura;
            vy = 0;
            noChao = true;
        } else {
            noChao = false;
        }
    }

    public void atualizarAnimacao(double dt) {
        if (spriteSheet == null) {
            return;
        }

        temporizadorQuadro += dt;
        if (temporizadorQuadro >= tempoQuadro) {
            temporizadorQuadro = 0;
            quadroAtual = (quadroAtual + 1) % qtdQuadros;
        }
    }

    public void pular() {
        if (noChao) {
            vy = -Constantes.VEL_PULO;
            noChao = false;
        }
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        if (spriteSheet == null) {
            gc.setFill(javafx.scene.paint.Color.BLUE);
            gc.fillRect(x - largura/2, y - altura/2, largura, altura);
            return;
        }

        double larguraQuadro = spriteSheet.getWidth() / qtdQuadros;
        double xOrigem = larguraQuadro * quadroAtual;
        double yOrigem = 0;

        gc.drawImage(spriteSheet,
                xOrigem, yOrigem, larguraQuadro, spriteSheet.getHeight(),
                x - largura/2, y - altura/2, largura, altura);
    }

    public boolean estaNoChao() {
        return noChao;
    }
}