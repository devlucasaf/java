import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Classe que representa a segunda tela do aplicativo
public class pageTwo {

    // Construtor - será chamado quando a tela for criada
    public pageTwo() {
        // Criação da janela principal
        JFrame frame = new JFrame("Placar");
        JPanel panel = new JPanel();
        frame.setSize(360, 640);
        frame.setVisible(true);
        frame.add(panel);
        panel.setSize(360, 640);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centraliza na tela
        panel.setLayout(null); // Layout absoluto para posicionamento manual

        // Título ou instrução
        JLabel label = new JLabel("Quem fez o ponto?");
        label.setBounds(10, 16, 150, 50);
        panel.add(label);

        // Campo de texto para inserir quem fez o ponto
        JTextField textField = new JTextField("Quem fez o ponto?");
        textField.setBounds(10, 65, 320, 50);
        panel.add(textField);

        // ========== INFORMAÇÕES DO JOGADOR 1 ==========

        JLabel labelPlayerOne = new JLabel("Pontos " + dataBase.playerOne);
        labelPlayerOne.setBounds(15, 115, 150, 50);
        panel.add(labelPlayerOne);

        // Quantidade de sets vencidos
        JLabel labelTwo = new JLabel(String.valueOf(dataBase.setPointPlayerOne));
        labelTwo.setBounds(15, 140, 150, 50);
        labelTwo.setFont(new Font("Arial", Font.BOLD, 35));
        labelTwo.setForeground(Color.RED);
        panel.add(labelTwo);

        // Pontuação atual do jogador 1
        JLabel labelThree = new JLabel(String.valueOf(dataBase.pointPlayerOne));
        labelThree.setBounds(140, 175, 200, 200);
        labelThree.setFont(new Font("Arial", Font.BOLD, 150));
        labelThree.setForeground(Color.RED);
        panel.add(labelThree);

        // ========== INFORMAÇÕES DO JOGADOR 2 ==========

        JLabel labelPlayerTwo = new JLabel("Pontos " + dataBase.playerTwo);
        labelPlayerTwo.setBounds(15, 525, 150, 50);
        panel.add(labelPlayerTwo);

        // Quantidade de sets vencidos
        JLabel labelFour = new JLabel(String.valueOf(dataBase.setPointPlayerTwo));
        labelFour.setBounds(15, 490, 150, 50);
        labelFour.setFont(new Font("Arial", Font.BOLD, 35));
        labelFour.setForeground(Color.BLUE);
        panel.add(labelFour);

        // Pontuação atual do jogador 2
        JLabel labelFive = new JLabel(String.valueOf(dataBase.pointPlayerTwo));
        labelFive.setBounds(140, 340, 200, 200);
        labelFive.setFont(new Font("Arial", Font.BOLD, 150));
        labelFive.setForeground(Color.BLUE);
        panel.add(labelFive);

        // Evento de teclado para quando o usuário pressionar Enter no campo de texto
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Verifica se a tecla pressionada foi ENTER
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Chama a função que processa o ponto marcado
                    Point(frame, textField, dataBase.playerOne, labelThree, labelFive, labelTwo, labelFour);
                }
            }
        });
    }

    /**
     * Função que processa o ponto marcado
     * Verifica quem marcou, atualiza os pontos, sets e muda de página se necessário
     */
    static void Point(JFrame frame, JTextField camp, String playerOne,
                    JLabel pointPlayerOne, JLabel pointPlayerTwo,
                    JLabel labelTwo, JLabel labelFour) {

        // Se o jogador que marcou ponto for o jogador 1
        if (camp.getText().equalsIgnoreCase(playerOne)) {
            dataBase.pointPlayerOne++;
            pointPlayerOne.setText(String.valueOf(dataBase.pointPlayerOne));
        } else {
            // Senão, foi o jogador 2
            dataBase.pointPlayerTwo++;
            pointPlayerTwo.setText(String.valueOf(dataBase.pointPlayerTwo));
        }

        // Verifica se jogador 1 atingiu os pontos necessários para vencer o set
        if (dataBase.pointPlayerOne == dataBase.point) {
            dataBase.setPointPlayerOne++;
            labelTwo.setText(String.valueOf(dataBase.setPointPlayerOne));
            dataBase.pointPlayerOne = 0;
            dataBase.pointPlayerTwo = 0;
            pointPlayerOne.setText("0");
            pointPlayerTwo.setText("0");
        }

        // Verifica se jogador 2 venceu o set
        if (dataBase.pointPlayerTwo == dataBase.point) {
            dataBase.setPointPlayerTwo++;
            labelFour.setText(String.valueOf(dataBase.setPointPlayerTwo));
            dataBase.pointPlayerTwo = 0;
            dataBase.pointPlayerOne = 0;
            pointPlayerOne.setText("0");
            pointPlayerTwo.setText("0");
        }

        // Verifica se algum jogador venceu a partida (atingiu a quantidade de sets definida)
        if (dataBase.setPointPlayerOne == dataBase.set || dataBase.setPointPlayerTwo == dataBase.set) {
            frame.dispose(); // Fecha a janela atual
            new pageThree(); // Vai para a próxima página
        }
    }
}
