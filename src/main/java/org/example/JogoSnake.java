import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class JogoSnake extends JPanel implements ActionListener {

    private final int largura = 1200;
    private final int altura = 800;
    private final int tamanhoQuadrado = 20;
    private final int velocidade = 15;

    private final ArrayList<Point> pixels = new ArrayList<>();
    private int tamanhoCobra = 1;
    private int x, y;
    private int velocidadeX = 0;
    private int velocidadeY = 0;
    private Point comida;

    private boolean fimDeJogo = false;
    private Timer timer;

    public JogoSnake() {
        this.setPreferredSize(new Dimension(largura, altura));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new ControleTeclado());
        
        iniciarJogo();
    }

    private void iniciarJogo() {
        x = largura / 2;
        y = altura / 2;
        gerarComida();
        
        timer = new Timer(1000 / velocidade, this);
        timer.start();
    }

    private void gerarComida() {
        Random rand = new Random();
        int rX = rand.nextInt(largura / tamanhoQuadrado) * tamanhoQuadrado;
        int rY = rand.nextInt(altura / tamanhoQuadrado) * tamanhoQuadrado;
        comida = new Point(rX, rY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!fimDeJogo) {
            g.setColor(Color.RED);
            g.fillRect(comida.x, comida.y, tamanhoQuadrado, tamanhoQuadrado);

            g.setColor(Color.GREEN);
            for (Point p : pixels) {
                g.fillRect(p.x, p.y, tamanhoQuadrado, tamanhoQuadrado);
            }

            g.setColor(Color.RED);
            g.setFont(new Font("Helvetica", Font.BOLD, 35));
            g.drawString("Pontos: " + (tamanhoCobra - 1), 10, 35);
        } else {
            mostrarFimDeJogo(g);
        }
    }

    private void mostrarFimDeJogo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 50));
        String msg = "Fim de Jogo!";
        g.drawString(msg, largura / 3, altura / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!fimDeJogo) {
            x += velocidadeX;
            y += velocidadeY;

            if (x < 0 || x >= largura || y < 0 || y >= altura) {
                fimDeJogo = true;
            }

            pixels.add(new Point(x, y));
            if (pixels.size() > tamanhoCobra) {
                pixels.remove(0);
            }

            for (int i = 0; i < pixels.size() - 1; i++) {
                if (pixels.get(i).equals(new Point(x, y))) {
                    fimDeJogo = true;
                }
            }

            if (x == comida.x && y == comida.y) {
                tamanhoCobra++;
                gerarComida();
            }
        }
        repaint();
    }

    private class ControleTeclado extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int tecla = e.getKeyCode();

            if (tecla == KeyEvent.VK_UP && velocidadeY == 0) {
                velocidadeX = 0;
                velocidadeY = -tamanhoQuadrado;
            } else if (tecla == KeyEvent.VK_DOWN && velocidadeY == 0) {
                velocidadeX = 0;
                velocidadeY = tamanhoQuadrado;
            } else if (tecla == KeyEvent.VK_LEFT && velocidadeX == 0) {
                velocidadeX = -tamanhoQuadrado;
                velocidadeY = 0;
            } else if (tecla == KeyEvent.VK_RIGHT && velocidadeX == 0) {
                velocidadeX = tamanhoQuadrado;
                velocidadeY = 0;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo Snake em Java");
        JogoSnake gamePanel = new JogoSnake();
        
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
