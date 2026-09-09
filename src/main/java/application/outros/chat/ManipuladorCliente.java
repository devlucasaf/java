package application.outros.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ManipuladorCliente implements Runnable {

    private final Socket        socket;
    private final ServidorChat  servidor;
    private BufferedReader      entrada;
    private PrintWriter         saida;

    private String  nomeUsuario = "Anonimo";
    private String  salaAtual = "geral";
    private boolean desconectado = false;

    // --- GUARDA O SOCKET E O SERVIDOR DESTA CONEXÃO ---
    public ManipuladorCliente(Socket socket, ServidorChat servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    // --- RETORNA O APELIDO DO USUÁRIO ---
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    // --- ENVIA UMA LINHA DE TEXTO PARA ESTE CLIENTE ---
    void enviar(String mensagem) {
        if (saida != null) {
            saida.println(mensagem);
        }
    }

    // --- LOOP PRINCIPAL DA CONEXÃO DO CLIENTE ---
    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter(socket.getOutputStream(), true);

            saida.println("Bem-vindo ao chat! Use /nome <apelido> para se identificar.");
            servidor.entrarNaSala(salaAtual, this);
            servidor.enviarParaSala(salaAtual, "* " + nomeUsuario + " entrou na sala *", this);

            String linha;
            while ((linha = entrada.readLine()) != null) {
                processarMensagem(linha);
            }
        } catch (IOException e) {
            System.out.println("Conexao encerrada com erro: " + e.getMessage());
        } finally {
            desconectar();
        }
    }

    // --- INTERPRETA COMANDOS OU ENVIA MENSAGEM PARA A SALA ---
    private void processarMensagem(String linha) {
        if (linha.startsWith("/nome ")) {
            String novoNome = linha.substring("/nome ".length()).trim();
            String nomeAntigo = nomeUsuario;
            nomeUsuario = novoNome.isEmpty() ? nomeUsuario : novoNome;
            servidor.enviarParaSala(salaAtual, "* " + nomeAntigo + " agora se chama " + nomeUsuario + " *", null);
        } else if (linha.startsWith("/sala ")) {
            String novaSala = linha.substring("/sala ".length()).trim();
            servidor.sairDaSala(salaAtual, this);
            servidor.enviarParaSala(salaAtual, "* " + nomeUsuario + " saiu da sala *", this);
            salaAtual = novaSala.isEmpty() ? "geral" : novaSala;
            servidor.entrarNaSala(salaAtual, this);
            servidor.enviarParaSala(salaAtual, "* " + nomeUsuario + " entrou na sala *", this);
            enviar("Voce agora esta na sala: " + salaAtual);
        } else if (linha.equals("/usuarios")) {
            List<String> usuarios = servidor.listarUsuariosDaSala(salaAtual);
            enviar("Usuarios na sala '" + salaAtual + "': " + String.join(", ", usuarios));
        } else if (linha.equals("/sair")) {
            enviar("Ate mais, " + nomeUsuario + "!");
            desconectar();
        } else {
            servidor.enviarParaSala(salaAtual, "[" + salaAtual + "] " + nomeUsuario + ": " + linha, this);
        }
    }

    // --- REMOVE O CLIENTE DA SALA E FECHA O SOCKET ---
    private void desconectar() {
        if (desconectado) {
            return;
        }
        desconectado = true;
        try {
            servidor.sairDaSala(salaAtual, this);
            servidor.enviarParaSala(salaAtual, "* " + nomeUsuario + " saiu do chat *", this);
            socket.close();
        } catch (IOException ignored) {
            // --- CONEXAO JA PODE TER CAÍDO ---
        }
    }
}

