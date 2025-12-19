/*
    Código de Sistema de um Elevador
    Código original desenvolvido em python por @DediXt04
    https://github.com/DediXt04/Elevator link para conferirem o código original
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Elevador {
    private int totalAndares;
    private int andarAtual;
    private List<Passageiro> passageiros;
    private List<Passageiro> chamadas;
    private int movimentos;

    public Elevador() {
        this(100);
    }

    public Elevador(int totalAndares) {
        this.totalAndares = totalAndares;
        this.andarAtual = 0;
        this.passageiros = new ArrayList<>();
        this.chamadas = new ArrayList<>();
        this.movimentos = 0;
    }

    public void adicionarChamada(Passageiro passageiro) {
        if (passageiro.getAndarOrigem() < 0 || passageiro.getAndarOrigem() >= totalAndares ||
                passageiro.getAndarDestino() < 0 || passageiro.getAndarDestino() >= totalAndares) {
            return;
        }

        chamadas.add(passageiro);
        System.out.println("📞 Chamada: " + passageiro.getAndarOrigem() +
                " -> " + passageiro.getAndarDestino());
    }

    private Integer escolherDestino() {
        List<Integer> destinos = new ArrayList<>();

        for (Passageiro p : passageiros) {
            destinos.add(p.getAndarDestino());
        }

        for (Passageiro p : chamadas) {
            destinos.add(p.getAndarOrigem());
        }

        if (destinos.isEmpty()) {
            return null;
        }

        // Encontra o destino mais próximo
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

    public void status() {
        System.out.println("🏢 Andar atual: " + andarAtual);
        System.out.println("📞 Chamadas: " +
                (chamadas.isEmpty() ? "—" : chamadas));
        System.out.println("🛗 Passageiros: " +
                (passageiros.isEmpty() ? "—" : passageiros));
        System.out.println("-".repeat(40));
    }

    // Getters para informações do elevador
    public int getTotalAndares() {
        return totalAndares;
    }

    public int getAndarAtual() {
        return andarAtual;
    }

    public int getMovimentos() {
        return movimentos;
    }
}

class Passageiro {
    private int andarOrigem;
    private int andarDestino;
    private boolean noElevador;

    public Passageiro(int andarOrigem, int andarDestino) {
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
        this.noElevador = false;
    }

    // Getters e Setters
    public int getAndarOrigem() {
        return andarOrigem;
    }

    public int getAndarDestino() {
        return andarDestino;
    }

    public boolean isNoElevador() {
        return noElevador;
    }

    public void setNoElevador(boolean noElevador) {
        this.noElevador = noElevador;
    }

    @Override
    public String toString() {
        String estado = noElevador ? "🟢" : "⚪";
        return estado + "👤(" + andarOrigem + "->" + andarDestino + ")";
    }
}

public class ElevadorMain {
    public static void main(String[] args) {
        final int TOTAL_ANDARES = 100;
        Elevador elevador = new Elevador(TOTAL_ANDARES);

        List<Passageiro> passageiros = new ArrayList<>();
        Random random = new Random();

        // Criar 100 passageiros
        for (int i = 0; i < 100; i++) {
            int origem = random.nextInt(TOTAL_ANDARES);
            int destino = random.nextInt(TOTAL_ANDARES);

            // Garantir que origem e destino sejam diferentes
            while (destino == origem) {
                destino = random.nextInt(TOTAL_ANDARES);
            }

            passageiros.add(new Passageiro(origem, destino));
        }

        // Adicionar chamadas ao elevador
        for (Passageiro p : passageiros) {
            elevador.adicionarChamada(p);
        }

        // Exemplo de uso: mover o elevador algumas vezes
        System.out.println("\n=== SIMULAÇÃO DO ELEVADOR ===\n");

        for (int i = 0; i < 20; i++) {
            elevador.status();
            elevador.mover();
        }

        System.out.println("\n=== STATUS FINAL ===");
        elevador.status();
        System.out.println("Total de movimentos: " + elevador.getMovimentos());
    }
}
