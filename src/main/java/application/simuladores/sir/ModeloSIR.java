package application.simuladores.sir;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

public class ModeloSIR extends JPanel {
    private static final int LARGURA = 900;
    private static final int ALTURA = 600;
    private static final int GRAFICO_TOP = 400;

    private double          S = 0.99;
    private double          I = 0.01;
    private double          R = 0.0;
    private final double    beta = 0.35;
    private final double    gamma = 0.05;
    private final double    dt = 0.5;

    private final List<double[]> historico = new ArrayList<>();

    // --- CONFIGURA O PAINEL E INICIA O TIMER DA SIMULACAO ---
    public ModeloSIR() {
        setBackground(Color.WHITE);
        setPreferredSize(new java.awt.Dimension(LARGURA, ALTURA));
        historico.add(new double[]{S, I, R});

        Timer timer = new Timer(50, e -> { passo(); repaint(); });
        timer.start();
    }

    // --- AVANCA UM PASSO DAS EQUAÇÕES SIR ---
    private void passo() {
        double dS = -beta * S * I * dt;
        double dI = (beta * S * I - gamma * I) * dt;
        double dR = gamma * I * dt;
        S += dS;
        I += dI;
        R += dR;

        if (S < 0) {
            S = 0;
        }

        historico.add(new double[]{S, I, R});
        if (historico.size() > LARGURA) {
            historico.remove(0);
        }
    }

    // --- DESENHA TEXTOS, LEGENDA E CURVAS DO GRÁFICO ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.BLACK);
        g2.drawString("Modelo SIR de Pandemia", 20, 20);
        g2.drawString(String.format("Suscetiveis: %.2f%%", S * 100), 20, 40);
        g2.drawString(String.format("Infectados:  %.2f%%", I * 100), 20, 60);
        g2.drawString(String.format("Recuperados: %.2f%%", R * 100), 20, 80);
        g2.drawString(String.format("beta=%.2f gamma=%.2f R0=%.2f", beta, gamma, beta / gamma), 20, 100);

        int gW = LARGURA - 40;
        int gH = ALTURA - GRAFICO_TOP - 20;
        int x0 = 20;
        int y0 = GRAFICO_TOP;

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawRect(x0, y0, gW, gH);

        int n = historico.size();
        for (int i = 1; i < n; i++) {
            double[] p0 = historico.get(i - 1);
            double[] p1 = historico.get(i);
            int xa = x0 + (i - 1) * gW / Math.max(1, n);
            int xb = x0 + i * gW / Math.max(1, n);
            g2.setColor(Color.BLUE);
            g2.drawLine(xa, y0 + gH - (int)(p0[0] * gH), xb, y0 + gH - (int)(p1[0] * gH));
            g2.setColor(Color.RED);
            g2.drawLine(xa, y0 + gH - (int)(p0[1] * gH), xb, y0 + gH - (int)(p1[1] * gH));
            g2.setColor(new Color(0, 150, 0));
            g2.drawLine(xa, y0 + gH - (int)(p0[2] * gH), xb, y0 + gH - (int)(p1[2] * gH));
        }

        g2.setColor(Color.BLUE); g2.drawString("Suscetiveis", x0 + 10, y0 + 15);
        g2.setColor(Color.RED); g2.drawString("Infectados", x0 + 100, y0 + 15);
        g2.setColor(new Color(0, 150, 0)); g2.drawString("Recuperados", x0 + 190, y0 + 15);
    }

    // --- ABRE A JANELA DO MODELO SIR ---
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Modelo SIR");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new ModeloSIR());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}

