package math.calculadora.binario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraBinaria extends JFrame implements ActionListener {

    private JTextField  campoNumero1;
    private JTextField  campoNumero2;
    private JLabel      labelResultadoDecimal;
    private JLabel      labelResultadoBinario;
    private JButton     botaoSomar;
    private JButton     botaoSubtrair;
    private JButton     botaoMultiplicar;
    private JButton     botaoDividir;
    private JButton     botaoConverter;
    private JButton     botaoLimpar;

    // --- CONSTRUTOR ---
    public CalculadoraBinaria() {
        setTitle("Calculadora Binária");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        criarComponentes();
        organizarLayout();
        setVisible(true);
    }

    // --- CRIAÇÃO DOS COMPONENTES ---
    private void criarComponentes() {
        campoNumero1 = new JTextField(10);
        campoNumero2 = new JTextField(10);

        botaoSomar = new JButton("Somar (+)");
        botaoSubtrair = new JButton("Subtrair (-)");
        botaoMultiplicar = new JButton("Multiplicar (*)");
        botaoDividir = new JButton("Dividir (/)");
        botaoConverter = new JButton("Converter N1 para Binário");
        botaoLimpar = new JButton("Limpar");

        labelResultadoDecimal = new JLabel("Resultado (decimal): ");
        labelResultadoBinario = new JLabel("Resultado (binário): ");

        botaoSomar.addActionListener(this);
        botaoSubtrair.addActionListener(this);
        botaoMultiplicar.addActionListener(this);
        botaoDividir.addActionListener(this);
        botaoConverter.addActionListener(this);
        botaoLimpar.addActionListener(this);
    }

    // --- ORGANIZAÇÃO DO LAYOUT ---
    private void organizarLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(new JLabel("Número 1:"), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        add(campoNumero1, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(new JLabel("Número 2:"), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        add(campoNumero2, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        add(botaoSomar, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(botaoSubtrair, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        add(botaoMultiplicar, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        add(botaoDividir, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(botaoConverter, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(botaoLimpar, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        add(labelResultadoDecimal, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        add(labelResultadoBinario, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
    }

    // --- TRATAMENTO DE EVENTOS ---
    @Override
    public void actionPerformed(ActionEvent e) {
        Object origem = e.getSource();

        if (origem == botaoLimpar) {
            campoNumero1.setText("");
            campoNumero2.setText("");
            labelResultadoDecimal.setText("Resultado (decimal): ");
            labelResultadoBinario.setText("Resultado (binário): ");
            return;
        }

        if (origem == botaoConverter) {
            try {
                int numero = Integer.parseInt(campoNumero1.getText().trim());
                String binario = converterParaBinario(numero);
                labelResultadoDecimal.setText("Decimal: " + numero);
                labelResultadoBinario.setText("Binário: " + binario);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, insira um número inteiro válido no campo Número 1.",
                        "Erro de entrada",
                        JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        try {
            int num1 = Integer.parseInt(campoNumero1.getText().trim());
            int num2 = Integer.parseInt(campoNumero2.getText().trim());
            int resultado = 0;
            String operacao = "";

            if (origem == botaoSomar) {
                resultado = somar(num1, num2);
                operacao = "Soma";
            } else if (origem == botaoSubtrair) {
                resultado = subtrair(num1, num2);
                operacao = "Subtração";
            } else if (origem == botaoMultiplicar) {
                resultado = multiplicar(num1, num2);
                operacao = "Multiplicação";
            } else if (origem == botaoDividir) {
                if (num2 == 0) {
                    JOptionPane.showMessageDialog(this,
                            "Divisão por zero não é permitida.",
                            "Erro matemático",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                resultado = dividir(num1, num2);
                operacao = "Divisão";
            }

            String binario = converterParaBinario(resultado);
            labelResultadoDecimal.setText(operacao + " (decimal): " + resultado);
            labelResultadoBinario.setText(operacao + " (binário): " + binario);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, insira números inteiros válidos em ambos os campos.",
                    "Erro de entrada",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static String converterParaBinario(int numero) {
        if (numero == 0) {
            return "0";
        }
        boolean negativo = numero < 0;
        int valorAbsoluto = Math.abs(numero);
        StringBuilder binario = new StringBuilder();
        while (valorAbsoluto > 0) {
            int resto = valorAbsoluto % 2;
            binario.insert(0, resto);
            valorAbsoluto /= 2;
        }

        if (negativo) {
            binario.insert(0, "-");
        }
        return binario.toString();
    }

    // --- SOMA DOIS NÚMEROS INTEIROS ---
    public static int somar(int a, int b) {
        return a + b;
    }

    // --- SUBTRAI DOIS NÚMEROS INTEIROS ---
    public static int subtrair(int a, int b) {
        return a - b;
    }

    // --- MULTIPLICA DOIS NÚMEROS INTEIROS ---
    public static int multiplicar(int a, int b) {
        return a * b;
    }

    // --- DIVIDE DOIS NÚMEROS INTEIROS ---
    public static int dividir(int a, int b) {
        return a / b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculadoraBinaria();
            }
        });
    }
}

