// Importa as bibliotecas necessárias
import javax.swing.*; // Interface gráfica (JFrame, JPanel, JButton, etc.)
import java.awt.*; // Cores, dimensões, etc.
import java.awt.event.*; // Eventos do teclado

public class Game {
    // Variáveis para armazenar a posição (x, y) do personagem (Mario)
    private int x = 0, y = 0;
    private JButton buttonImage; // (não está sendo usado)

    // Construtor da classe Game
    public Game() {

        // Criação da janela principal (JFrame)
        JFrame frame = new JFrame("Movimentar Mario");
        frame.setSize(750, 480); // Define o tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao clicar em "X"
        frame.setLocationRelativeTo(null); // Centraliza a janela na tela
        frame.setLayout(null); // Layout absoluto (permite usar setBounds)
        frame.setResizable(false); // Impede redimensionamento
        frame.setVisible(true); // Torna a janela visível

        // Criação do painel principal (JPanel)
        JPanel panel = new JPanel(null); // Layout absoluto
        panel.setBounds(0, 0, 750, 480); // Ocupa toda a janela
        panel.setBackground(Color.BLACK); // Cor de fundo preta
        frame.add(panel); // Adiciona o painel à janela

        // Criação e redimensionamento da imagem do Mario
        ImageIcon imageIcon = new ImageIcon("resources/mario bros.png");
        ImageIcon resizedImage = new ImageIcon(imageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        JButton buttonImageMario = new JButton(resizedImage); // Cria um botão com imagem
        buttonImageMario.setBorder(null); // Remove a borda do botão
        buttonImageMario.setBackground(Color.BLACK); // Cor de fundo igual à do painel
        buttonImageMario.setBounds(0, 0, 60, 60); // Posição inicial de Mario
        panel.add(buttonImageMario); // Adiciona Mario ao painel

        // Criação e redimensionamento da imagem do cogumelo
        ImageIcon mushroomImage = new ImageIcon("resources/cogumelo.png");
        ImageIcon resizedImageMushroom = new ImageIcon(mushroomImage.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        JButton buttonImageMushroom = new JButton(resizedImageMushroom);
        buttonImageMushroom.setBounds(680, 150, 60, 60); // Posição do cogumelo
        buttonImageMushroom.setBackground(Color.BLACK);
        buttonImageMushroom.setBorder(null);
        panel.add(buttonImageMushroom); // Adiciona o cogumelo ao painel

        // Adiciona um ouvinte de teclado ao frame para detectar as teclas pressionadas
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode(); // Pega o código da tecla pressionada

                // Verifica qual tecla foi pressionada e ajusta a posição (x, y) do Mario
                if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                    y -= 10; // Move para cima
                } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                    y += 10; // Move para baixo
                } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                    x -= 10; // Move para a esquerda
                } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                    x += 10; // Move para a direita
                }

                // Atualiza a posição do botão do Mario na tela
                buttonImageMario.setBounds(x, y, 60, 60);

                // Verifica colisão entre o Mario e o cogumelo
                Rectangle marioBounds = buttonImageMario.getBounds();
                Rectangle mushroomBounds = buttonImageMushroom.getBounds();

                if (marioBounds.intersects(mushroomBounds)) {
                    frame.dispose(); // Fecha a janela atual
                    new GamePageTwo(); // Chama a próxima página do jogo
                }

                // Impede que o Mario ultrapasse os limites da tela
                if (x < 0) x = 0;
                if (y < 0) y = 0;
                if (x > 750 - 60) x = 750 - 60;
                if (y > 480 - 60) y = 480 - 60;
            }
        });

        // Garante que o frame receba os eventos do teclado
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    // Método principal: inicia o jogo
    public static void main(String[] args) {
        new Game(); // Cria uma nova instância do jogo
    }
}