// Importa as bibliotecas necessárias para a interface gráfica e manipulação de eventos
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePageTwo {
    // Variáveis para controlar a posição (x, y) do personagem (Mario)
    private int x = 0, y = 0;
    private JButton buttonImageMario; // Botão que representa o Mario (essa variável de instância não está sendo usada de fato, pois outra variável local com o mesmo nome é criada dentro do construtor)

    // Construtor da classe GamePageTwo
    public GamePageTwo() {

        // Criação da janela principal (JFrame)
        JFrame frame = new JFrame("Movimentar Mario");
        frame.setSize(750, 480); // Define o tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao clicar no "X"
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela
        frame.setLayout(null); // Usa layout absoluto (permite posicionar componentes com setBounds)
        frame.setResizable(false); // Impede redimensionamento
        frame.setVisible(true); // Torna a janela visível

        // Criação do painel onde os elementos do jogo são inseridos
        JPanel panel = new JPanel(null); // Layout absoluto
        panel.setBounds(0, 0, 750, 480); // Ocupa toda a área da janela
        panel.setBackground(Color.BLACK); // Cor de fundo preta
        frame.add(panel); // Adiciona o painel ao frame

        // Criação e redimensionamento da imagem do Mario
        ImageIcon marioImage = new ImageIcon("resources/mario bros.png");
        ImageIcon resizedImage = new ImageIcon(marioImage.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        JButton buttonImageMario = new JButton(resizedImage); // Cria o botão com a imagem do Mario
        buttonImageMario.setBorder(null); // Remove borda do botão
        buttonImageMario.setBackground(Color.BLACK); // Mesma cor do fundo
        buttonImageMario.setBounds(0, 0, 60, 60); // Posição inicial do Mario
        panel.add(buttonImageMario); // Adiciona o Mario ao painel

        // Criação e redimensionamento da imagem do cogumelo
        ImageIcon mushroomImage = new ImageIcon("resources/cogumelo.png");
        ImageIcon resizedImageMushroom = new ImageIcon(mushroomImage.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        JButton buttonImageMushroom = new JButton(resizedImageMushroom); // Cria botão com cogumelo
        buttonImageMushroom.setBounds(480, 340, 60, 60); // Posição do cogumelo
        buttonImageMushroom.setBackground(Color.BLACK);
        buttonImageMushroom.setBorder(null);
        panel.add(buttonImageMushroom); // Adiciona o cogumelo ao painel

        // Adiciona um ouvinte de eventos do teclado ao frame
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode(); // Obtém a tecla pressionada

                // Move o Mario conforme as teclas pressionadas
                if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                    y -= 10; // Move para cima
                } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                    y += 10; // Move para baixo
                } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                    x -= 10; // Move para a esquerda
                } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                    x += 10; // Move para a direita
                }

                // Atualiza a posição do botão do Mario
                buttonImageMario.setBounds(x, y, 60, 60);

                // Verifica colisão entre o Mario e o cogumelo
                Rectangle marioBounds = buttonImageMario.getBounds();
                Rectangle mushroomBounds = buttonImageMushroom.getBounds();

                if (marioBounds.intersects(mushroomBounds)) {
                    frame.dispose(); // Fecha a janela se o Mario encostar no cogumelo
                }

                // Impede que o Mario saia da área visível da tela
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                if (x > 750 - 100) x = 750 - 100; // margem extra de 40px por algum motivo (60 + 40?)
                if (y > 480 - 100) y = 480 - 100;
            }
        });

        // Garante que o frame possa capturar os eventos do teclado
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }
}