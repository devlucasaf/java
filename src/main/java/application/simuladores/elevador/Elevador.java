package application.simuladores.elevador;

import java.util.ArrayList;
import java.util.List;

public class Elevador {
    private int                 totalAndares;
    private int                 andarAtual;
    private List<Passageiro>    passageiros;
    private List<Passageiro>    chamadas;
    private int                 movimentos;

    // --- INICIALIZA O ELEVADOR COM 100 ANDARES ---
    public Elevador() {
        this(100);
    }

    // --- INICIALIZA O ELEVADOR COM A QUANTIDADE DE ANDARES INFORMADA ---
    public Elevador(int totalAndares) {
        this.totalAndares = totalAndares;
        this.andarAtual = 0;
        this.passageiros = new ArrayList<>();
        this.chamadas = new ArrayList<>();
        this.movimentos = 0;
    }

    // --- ADICIONA UMA CHAMADA APOS VALIDAR OS ANDARES DO PASSAGEIRO ---
    public void adicionarChamada(Passageiro passageiro) {
        if (passageiro.getAndarOrigem() < 0 || passageiro.getAndarOrigem() >= totalAndares ||
                passageiro.getAndarDestino() < 0 || passageiro.getAndarDestino() >= totalAndares) {
            return;
        }

        chamadas.add(passageiro);
        System.out.println("📞 Chamada: " + passageiro.getAndarOrigem() + " - " + passageiro.getAndarDestino());
    }

    // --- ESCOLHE O DESTINO MAIS PRÓXIMO DO ANDAR ATUAL ---
    private Integer escolherDestino() {
        List<Integer> destinos = new ArrayList<>();

        for (Passageiro p : passageiros) {
            destinos.add(p.getAndarDestino());
        }

        for (Passageiro p : chamadas) {
            destinos.add(p.getAndarOrigem());
        }

        if (destinos.isEmpty()) {
            return null; // Sem destino
        }

        Integer destinoMaisProximo = destinos.get(0);
        int menorDistancia = Math.abs(destinoMaisProximo - andarAtual);

        for (int i = 1; i < destinos.size(); i++) {
            int distancia = Math.abs(destinos.get(i) - andarAtual);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                destinoMaisProximo = destinos.get(i);
            }
        }

        return destinoMaisProximo;
    }

    // --- MOVIMENTA O ELEVADOR EM DIREÇÃO AO DESTINO MAIS PRÓXIMO ---
    public void mover() {
        Integer destino = escolherDestino();

        if (destino == null) {
            System.out.println("⏸ Nenhuma chamada ou passageiro no momento.");
            return;
        }

        if (andarAtual < destino) {
            andarAtual++;
            movimentos++;
            System.out.println("🔼 Subiu para " + andarAtual);
        } else if (andarAtual > destino) {
            andarAtual--;
            movimentos++;
            System.out.println("🔽 Desceu para " + andarAtual);
        } else {
            System.out.println("🚪 Chegou no andar " + andarAtual);

            boolean entrouPessoa = false;
            List<Passageiro> chamadasParaRemover = new ArrayList<>();

            for (Passageiro p : chamadas) {
                if (p.getAndarOrigem() == andarAtual) {
                    p.setNoElevador(true);
                    passageiros.add(p);
                    chamadasParaRemover.add(p);
                    entrouPessoa = true;
                    System.out.println("⬆️ Entrou: " + p);
                }
            }
            chamadas.removeAll(chamadasParaRemover);

            boolean saiuPessoa = false;
            List<Passageiro> passageirosParaRemover = new ArrayList<>();

            for (Passageiro p : passageiros) {
                if (p.getAndarDestino() == andarAtual) {
                    p.setNoElevador(false);
                    passageirosParaRemover.add(p);
                    saiuPessoa = true;
                    System.out.println("⬇️ Saiu: " + p);
                }
            }
            passageiros.removeAll(passageirosParaRemover);

            if (!entrouPessoa && !saiuPessoa) {
                System.out.println("🕓 Sem embarque/desembarque neste andar.");
            }
        }
    }

    // --- EXIBE O ESTADO ATUAL DO ELEVADOR ---
    public void status() {
        System.out.println("🏢 Andar atual: " + andarAtual);
        System.out.println("📞 Chamadas: " + (chamadas.isEmpty() ? "—" : chamadas));
        System.out.println("🛗 Passageiros: " + (passageiros.isEmpty() ? "—" : passageiros));
        System.out.println("-".repeat(40));
    }

    // --- RETORNA A QUANTIDADE TOTAL DE ANDARES ---
    public int getTotalAndares() {
        return totalAndares;
    }

    // --- RETORNA O ANDAR ATUAL DO ELEVADOR ---
    public int getAndarAtual() {
        return andarAtual;
    }

    // --- RETORNA A QUANTIDADE DE MOVIMENTOS REALIZADOS ---
    public int getMovimentos() {
        return movimentos;
    }
}
