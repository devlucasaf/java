package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    // Componentes da interface
    private JTextField display;
    private double num1, num2, result;
    private char operator;

    public Calculadora() {
        // Configuração da janela
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Campo de texto para exibir os números e resultados
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // Painel para os botões
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4)); // 4x4 grid com espaçamento

        String[] button = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };


        // Criação dos botões
        for (String text : button) {
            JButton buttons = new JButton(text);
            buttons.setFont(new Font("Arial", Font.BOLD, 20));
            buttons.addActionListener(this); // Adiciona o listener de eventos
            panel.add(buttons);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true); // Torna a janela visível
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Verifica se o comando é um número ou ponto decimal
        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals(".")) {
            display.setText(display.getText() + command); // Adiciona ao campo de texto
        }
        // Verifica se o comando é uma operação
        else if (command.charAt(0) == 'C' || command.charAt(0) == '-' || command.charAt(0) == '*' || command.charAt(0) == '/') {
            display.setText(""); // Limpa o campo de texto
        }
        // Verifica se o comando é "=" para calcular o resultado
        else if (command.equals("=")) {
            num2 = Double.parseDouble(display.getText()); // Armazena o segundo número

            // Realiza a operação
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        display.setText("Erro: Divisão por zero");
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(result)); // Exibe o resultado
        }
        else {
            num1 = Double.parseDouble(display.getText());
            operator = command.charAt(0);
            display.setText("");
        }
    }

    public static void main(String[] args) {
        new Calculadora(); // Inicia a calculadora
    }
}
