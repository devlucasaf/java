package math.calculadora.cientifica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class CalculadoraCientifica extends JFrame {
    private JTextField      visor;
    private ScriptEngine    motor;
    private double          ultimoResultado = 0.0;

    private final Color COR_FUNDO = new Color(30, 30, 35);
    private final Color COR_VISOR = new Color(20, 20, 25);
    private final Color COR_TEXTO = Color.WHITE;
    private final Color COR_BOTAO_NORMAL = new Color(45, 45, 50);
    private final Color COR_BOTAO_DESTAQUE = new Color(0, 120, 215);
    private final Color COR_BOTAO_FUNCAO = new Color(60, 60, 70);
    private final Color COR_BOTAO_OPERADOR = new Color(70, 70, 80);
    private final Color COR_BOTAO_IGUAL = new Color(0, 100, 180);

    // --- CONSTROI A JANELA E INICIALIZA UI E MOTOR DE CÁLCULO ---
    public CalculadoraCientifica() {
        inicializarComponentes();
        configurarMotorScript();
    }

    // --- CONFIGURA FUNÇÕES AUXILIARES NO MOTOR JAVASCRIPT ---
    private void configurarMotorScript() {
        ScriptEngineManager gerenciador = new ScriptEngineManager();
        motor = gerenciador.getEngineByName("JavaScript");
        try {
            motor.eval("function fact(n) { " +
                    "if (n < 0 || Math.floor(n) != n) return NaN; " +
                    "var r = 1; for(var i=2; i<=n; i++) r *= i; return r; }");
            motor.eval("function neg(x) { return -x; }");
            motor.eval("function pow10(x) { return Math.pow(10, x); }");
            motor.eval("function recip(x) { return 1/x; }");
            motor.eval("function cbrt(x) { return Math.pow(x, 1/3); }");
            motor.put("ans", 0.0);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    // --- MONTA A INTERFACE GRAFICA E REGISTRA ACOES DOS BOTOES ---
    private void inicializarComponentes() {
        setTitle("Calculadora Científica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 680);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COR_FUNDO);
        setLayout(new BorderLayout(10, 10));

        // Visor
        visor = new JTextField();
        visor.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        visor.setHorizontalAlignment(JTextField.RIGHT);
        visor.setBackground(COR_VISOR);
        visor.setForeground(COR_TEXTO);
        visor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 70), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        visor.setCaretColor(COR_TEXTO);
        visor.addActionListener(e -> calcular());
        add(visor, BorderLayout.NORTH);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(8, 6, 8, 8));
        painelBotoes.setBackground(COR_FUNDO);
        painelBotoes.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[][] botoes = {
                {"(", ")", "C", "CE", "DEL", "/"},
                {"7", "8", "9", "*", "^", "sqrt"},
                {"4", "5", "6", "-", "sin", "cos"},
                {"1", "2", "3", "+", "tan", "ln"},
                {"0", ".", "+/-", "=", "log", "π"},
                {"e", "asin", "acos", "atan", "sinh", "cosh"},
                {"tanh", "fact", "abs", "1/x", "pow10", "exp"},
                {"cbrt", "neg", "mod", "ans", "(", ")"}
        };

        for (String[] linha : botoes) {
            for (String texto : linha) {
                BotaoArredondado btnArredondado = new BotaoArredondado(texto);
                btnArredondado.setFont(new Font("Segoe UI", Font.BOLD, 16));

                if (texto.matches("[0-9]") || texto.equals(".")) {
                    btnArredondado.setCorFundo(COR_BOTAO_NORMAL);
                } else if (texto.matches("[+\\-*/^]|mod")) {
                    btnArredondado.setCorFundo(COR_BOTAO_OPERADOR);
                } else if (texto.equals("=")) {
                    btnArredondado.setCorFundo(COR_BOTAO_IGUAL);
                } else if (texto.equals("C") || texto.equals("CE") || texto.equals("DEL")) {
                    btnArredondado.setCorFundo(new Color(180, 60, 50));
                } else {
                    btnArredondado.setCorFundo(COR_BOTAO_FUNCAO);
                }

                btnArredondado.setCorDestaque(COR_BOTAO_DESTAQUE);
                btnArredondado.addActionListener(e -> processarBotao(texto));
                painelBotoes.add(btnArredondado);
            }
        }

        add(painelBotoes, BorderLayout.CENTER);
    }

    // --- TRATA O COMANDO DE CADA BOTÃO PRESSIONADO ---
    private void processarBotao(String comando) {
        switch (comando) {
            case "C":
                visor.setText("");
                break;
            case "CE":
                visor.setText("");
                break;
            case "DEL":
                String texto = visor.getText();
                if (!texto.isEmpty()) {
                    visor.setText(texto.substring(0, texto.length() - 1));
                }
                break;
            case "=":
                calcular();
                break;
            case "π":
                inserirConstante("pi");
                break;
            case "e":
                inserirConstante("e");
                break;
            case "ans":
                inserirTexto("ans");
                break;
            case "+/-":
                inverterSinal();
                break;
            default:
                if (comando.matches("[0-9.()+\\-*/^]|mod")) {
                    inserirTexto(comando);
                } else if (comando.equals("sqrt")) {
                    inserirFuncao("sqrt");
                } else if (comando.equals("sin") || comando.equals("cos") || comando.equals("tan") ||
                        comando.equals("asin") || comando.equals("acos") || comando.equals("atan") ||
                        comando.equals("sinh") || comando.equals("cosh") || comando.equals("tanh") ||
                        comando.equals("ln") || comando.equals("log") || comando.equals("abs") ||
                        comando.equals("fact") || comando.equals("pow10") || comando.equals("exp") ||
                        comando.equals("cbrt") || comando.equals("neg")) {
                    inserirFuncao(comando);
                } else if (comando.equals("1/x")) {
                    inserirFuncao("recip");
                } else {
                    inserirTexto(comando);
                }
                break;
        }
    }

    // --- INSERE TEXTO BRUTO NO VISOR ---
    private void inserirTexto(String texto) {
        visor.setText(visor.getText() + texto);
        visor.requestFocus();
    }

    // --- INSERE CONSTANTES COMO PI, E E ANS ---
    private void inserirConstante(String constante) {
        visor.setText(visor.getText() + constante);
        visor.requestFocus();
    }

    // --- INSERE O NOME DA FUNÇÃO E ABRE PARENTESE ---
    private void inserirFuncao(String funcao) {
        String textoAtual = visor.getText();
        visor.setText(textoAtual + funcao + "(");
        SwingUtilities.invokeLater(() -> {
            visor.setCaretPosition(visor.getText().length());
            visor.requestFocus();
        });
    }

    // --- APLICA NEGAÇÃO AO CONTEÚDO ATUAL DO VISOR ---
    private void inverterSinal() {
        String texto = visor.getText();
        if (texto.isEmpty()) {
            inserirFuncao("neg");
        } else {
            visor.setText("neg(" + texto + ")");
        }
    }

    // --- PRE-PROCESSA E AVALIA A EXPRESSÃO DO VISOR ---
    private void calcular() {
        String expressao = visor.getText();
        if (expressao == null || expressao.trim().isEmpty()) {
            return;
        }

        try {
            String processada = preProcessarExpressao(expressao);
            Object resultado = motor.eval(processada);
            double valor = Double.parseDouble(resultado.toString());

            visor.setText(formatarResultado(valor));
            motor.put("ans", valor);
            ultimoResultado = valor;
        } catch (Exception e) {
            visor.setText("Erro");
            Timer temporizador = new Timer(1000, ev -> {
                if (visor.getText().equals("Erro")) {
                    visor.setText("");
                }
            });
            temporizador.setRepeats(false);
            temporizador.start();
        }
    }

    // --- CONVERTE FUNCOES E OPERADORES PARA SINTAXE JAVASCRIPT ---
    private String preProcessarExpressao(String expressao) {
        expressao = expressao.replaceAll("\\bπ\\b", "Math.PI");
        expressao = expressao.replaceAll("\\bpi\\b", "Math.PI");
        expressao = expressao.replaceAll("\\be\\b", "Math.E");

        expressao = expressao.replaceAll("\\bsin\\(", "Math.sin(");
        expressao = expressao.replaceAll("\\bcos\\(", "Math.cos(");
        expressao = expressao.replaceAll("\\btan\\(", "Math.tan(");
        expressao = expressao.replaceAll("\\basin\\(", "Math.asin(");
        expressao = expressao.replaceAll("\\bacos\\(", "Math.acos(");
        expressao = expressao.replaceAll("\\batan\\(", "Math.atan(");
        expressao = expressao.replaceAll("\\bsinh\\(", "Math.sinh(");
        expressao = expressao.replaceAll("\\bcosh\\(", "Math.cosh(");
        expressao = expressao.replaceAll("\\btanh\\(", "Math.tanh(");
        expressao = expressao.replaceAll("\\bln\\(", "Math.log(");
        expressao = expressao.replaceAll("\\blog\\(", "Math.log10(");
        expressao = expressao.replaceAll("\\bsqrt\\(", "Math.sqrt(");
        expressao = expressao.replaceAll("\\babs\\(", "Math.abs(");
        expressao = expressao.replaceAll("\\bexp\\(", "Math.exp(");

        expressao = expressao.replaceAll("\\^", "**");
        expressao = expressao.replaceAll("\\bmod\\b", "%");

        return expressao;
    }

    // --- FORMATA O RESULTADO NUMÉRICO PARA EXIBIÇÃO ---
    private String formatarResultado(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            return "Erro";
        }

        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        }
        return String.format("%.12g", valor).replace(',', '.');
    }

    // --- PONTO DE ENTRADA DA APLICAÇÃO SWING ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CalculadoraCientifica().setVisible(true);
        });
    }
}

