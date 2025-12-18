package org.example;

// Importações necessárias para usar componentes do Swing e eventos de ação
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Classe principal do programa
public class ExerciciosBestMinds {

    // Método principal, onde a execução do programa começa
    public static void main(String[] args) {

        // Cria uma janela usando JFrame
        JFrame window = new JFrame();

        // Cria um painel (JPanel) para organizar os componentes dentro da janela
        JPanel windows = new JPanel();

        // Adiciona o painel à janela
        window.add(windows);

        // Define a posição e o tamanho do painel (x, y, largura, altura)
        windows.setBounds(45, 23, 100, 100);

        // Define a posição e o tamanho da janela (x, y, largura, altura)
        window.setBounds(45, 23, 500, 500);

        // Define o comportamento padrão ao fechar a janela (encerrar o programa)
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define o título da janela
        window.setTitle("Calculadora");

        // Impede que a janela seja redimensionada
        window.setResizable(false);

        // Torna a janela visível
        window.setVisible(true);

        // Loop para adicionar os botões'
        for (int i = 0; i < 3 ; i++){
            // Chama a função para adicionar o botão
            add_button(windows, "Botão " + (i + 1),50 + i * 150,50,100,100);
        }

        // Chama o método para adicionar um botão ao painel
        //add_button(windows, "Marcelo", 45, 23, 100, 100);
    }

    // Método para adicionar um botão ao painel
    public static void add_button(JPanel windows, String nome, int x, int y, int w, int h) {
        // Cria um botão com o texto passado como parâmetro (nome)
        JButton button = new JButton(nome);

        // Define a posição e o tamanho do botão (x, y, largura, altura)
        button.setBounds(x, y, w, h);

        // Adiciona o botão ao painel
        windows.add(button);


    }
}
