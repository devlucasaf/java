package application.simuladores.particulas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SistemaParticulas extends JPanel {

    private final List<Particula>   particulas = new ArrayList<>();
    private final Random            r = new Random();
    private String                  modo = "fogo";
    private int                     mx = 500;
    private int                     my = 350;

    // --- CONFIGURA A INTERFACE, OS CONTROLES E A ATUALIZAÇÃO DAS PARTÍCULAS ---
    public SistemaParticulas() {
        setBackground(Color.BLACK);
        setPreferredSize(new java.awt.Dimension(1000, 700));
        setFocusable(true);

        // --- ATUALIZA A POSIÇÃO DE EMISSÃO CONFORME O MOVIMENTO DO MOUSE ---
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mx = e.getX();
                my = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mx = e.getX();
                my = e.getY();
            }
        });

        // --- GERA UMA EXPLOSÃO AO PRESSIONAR O MOUSE NO MODO CORRESPONDENTE ---
        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                if (modo.equals("explosao")) {
                    explosao(e.getX(), e.getY());
                }
            }
        });

        // --- ALTERA O MODO DE EMISSÃO CONFORME A TECLA PRESSIONADA ---
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case '1' -> modo = "fogo";
                    case '2' -> modo = "fumaca";
                    case '3' -> modo = "chuva";
                    case '4' -> modo = "explosao";
                }
                System.out.println("Modo: " + modo);
            }
        });

        // --- EMITE, ATUALIZA E REDESENHA AS PARTÍCULAS A CADA 16 MILISSEGUNDOS ---
        Timer timer = new Timer(16, e -> {
            emitir();
            atualizar();
            repaint();
        });
        timer.start();
    }

    // --- EMITE PARTÍCULAS CONFORME O MODO SELECIONADO ---
    private void emitir() {
        switch (modo) {
            case "fogo" -> {
                for (int i = 0; i < 5; i++) {
                    double vx = (r.nextDouble() - 0.5) * 1.5;
                    double vy = -2 - r.nextDouble() * 2;
                    Color c = new Color(255, 100 + r.nextInt(155), 0, 200);
                    particulas.add(new Particula(mx, my, vx, vy, 60, c, 4 + r.nextDouble() * 4));
                }
            }
            case "fumaca" -> {
                for (int i = 0; i < 3; i++) {
                    double vx = (r.nextDouble() - 0.5) * 0.8;
                    double vy = -1 - r.nextDouble();
                    int cinza = 100 + r.nextInt(80);
                    particulas.add(new Particula(mx, my, vx, vy, 120, new Color(cinza, cinza, cinza, 150), 8));
                }
            }
            case "chuva" -> {
                for (int i = 0; i < 8; i++) {
                    double x = r.nextDouble() * getWidth();
                    particulas.add(new Particula(x, 0, 0.5, 8 + r.nextDouble() * 4, 120, new Color(150, 200, 255), 2));
                }
            }
            default -> { }
        }
    }

    // --- GERA PARTÍCULAS EM TODAS AS DIREÇÕES A PARTIR DO PONTO INFORMADO ---
    private void explosao(int cx, int cy) {
        for (int i = 0; i < 100; i++) {
            double a = r.nextDouble() * Math.PI * 2;
            double v = 3 + r.nextDouble() * 4;
            Color c = new Color(255, 100 + r.nextInt(155), r.nextInt(80));
            particulas.add(new Particula(cx, cy, Math.cos(a) * v, Math.sin(a) * v, 50, c, 3 + r.nextDouble() * 3));
        }
    }

    // --- ATUALIZA A POSIÇÃO, A VELOCIDADE E O TEMPO DE VIDA DAS PARTÍCULAS ---
    private void atualizar() {
        Iterator<Particula> it = particulas.iterator();

        while (it.hasNext()) {
            Particula p = it.next();
            p.x += p.vx;
            p.y += p.vy;
            if (modo.equals("fumaca")) {
                p.vy -= 0.02;
            } else {
                p.vy += 0.05;
            }

            p.vida--;
            if (p.vida <= 0) {
                it.remove();
            }
        }
    }

    // --- DESENHA AS PARTÍCULAS E AS INFORMAÇÕES DO MODO ATUAL ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        for (Particula p : particulas) {
            int alpha = Math.max(0, Math.min(255, (int) (255 * p.vida / p.vidaMaxima)));
            graphics2D.setColor(new Color(p.cor.getRed(), p.cor.getGreen(), p.cor.getBlue(), alpha));
            int s = (int) p.tamanho;
            graphics2D.fillOval((int) p.x - s / 2, (int) p.y - s / 2, s, s);
        }
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Modo: " + modo + " (teclas 1=fogo 2=fumaca 3=chuva 4=explosao)", 10, 20);
        graphics2D.drawString("Partículas: " + particulas.size(), 10, 35);
    }

    // --- CRIA E EXIBE A JANELA DO SISTEMA DE PARTÍCULAS ---
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sistema de Partículas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            SistemaParticulas sistemaParticulas = new SistemaParticulas();

            frame.add(sistemaParticulas);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            sistemaParticulas.requestFocus();
        });
    }
}

