package games.narrativo.rpg;

import java.util.Scanner;

public class Main {
    private static Personagem       jogador;
    private static final Scanner    scanner = new Scanner(System.in);
    private static int              inimigosDerotados = 0;

    public static void main(String[] args) {
        criarPersonagem();
        loopPrincipal();

        System.out.println("\n Fim de jogo! Você derrotou " + inimigosDerotados + " inimigos!");
        jogador.mostrarStatus();
        scanner.close();
    }

    private static void criarPersonagem() {
        System.out.print("\nDigite o nome do seu herói: ");
        String nome = scanner.nextLine();

        System.out.println("\nEscolha sua classe:");
        System.out.println("1. ️ Guerreiro (Vida alta, defesa boa)");
        System.out.println("2.  Mago (Ataque alto, vida baixa)");
        System.out.println("3.  Arqueiro (Equilibrado)");
        System.out.print("Escolha: ");

        String classe = switch (scanner.nextLine()) {
            case "1" -> "Guerreiro";
            case "2" -> "Mago";
            case "3" -> "Arqueiro";
            default -> "Guerreiro";
        };

        jogador = new Personagem(nome, classe);
        System.out.println("\nBem-vindo, " + nome + " o " + classe + "!");
        jogador.mostrarStatus();
    }

    private static void loopPrincipal() {
        while (jogador.estaVivo()) {
            System.out.println("\n--- O que deseja fazer? ---");
            System.out.println("1. ️ Explorar (encontrar inimigos)");
            System.out.println("2.  Inventário");
            System.out.println("3.  Loja");
            System.out.println("4.  Ver Status");
            System.out.println("5.  Sair do jogo");
            System.out.print("Escolha: ");

            switch (scanner.nextLine()) {
                case "1" -> explorar();
                case "2" -> usarInventario();
                case "3" -> Loja.abrir(jogador, scanner);
                case "4" -> jogador.mostrarStatus();
                case "5" -> {
                    System.out.println("Até a próxima, aventureiro!");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
        System.out.println("\n Você foi derrotado... Game Over!");
    }

    private static void explorar() {
        Inimigo inimigo = Inimigo.gerarInimigo(jogador.getNivel());
        System.out.println("\n Um " + inimigo.getNome() + " apareceu! (Vida: " + inimigo.getVidaMaxima() + ")");

        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n[" + jogador.getNome() + ": " + jogador.getVida() + "/" + jogador.getVidaMaxima() + " HP]");
            System.out.println("[" + inimigo.getNome() + ": " + inimigo.barraVida() + " " + inimigo.getVida() + "/" + inimigo.getVidaMaxima() + " HP]");
            System.out.println("1. Atacar  2. Usar item  3. Fugir");
            System.out.print("Ação: ");

            String acao = scanner.nextLine();

            if (acao.equals("3")) {
                System.out.println("Você fugiu da batalha!");
                return;
            }

            if (acao.equals("2")) {
                if (!jogador.getInventario().temItens()) {
                    System.out.println("Sem itens! Você perde o turno.");
                } else {
                    jogador.getInventario().mostrar();
                    System.out.print("Usar qual item? (número): ");
                    try {
                        int idx = Integer.parseInt(scanner.nextLine()) - 1;
                        ItemRPG item = jogador.getInventario().usarItem(idx);
                        if (item != null && item.getTipo().equals("cura")) {
                            jogador.curar(item.getValor());
                            System.out.println("❤️ Você recuperou " + item.getValor() + " HP! Vida: " + jogador.getVida());
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Inválido, turno perdido!");
                    }
                }
            } else {
                int danoJogador = jogador.atacar();
                int danoReal = inimigo.receberDano(danoJogador);
                System.out.println("🗡️ Você causou " + danoReal + " de dano ao " + inimigo.getNome() + "!");
            }

            if (inimigo.estaVivo()) {
                int danoInimigo = inimigo.atacar();
                int danoRecebido = jogador.receberDano(danoInimigo);
                System.out.println("💥 " + inimigo.getNome() + " causou " + danoRecebido + " de dano em você!");
            }
        }

        if (!inimigo.estaVivo()) {
            inimigosDerotados++;
            System.out.println("\n Você derrotou o " + inimigo.getNome() + "!");
            jogador.ganharExperiencia(inimigo.getXpRecompensa());
            jogador.ganharOuro(inimigo.getOuroRecompensa());
            System.out.println(" +" + inimigo.getOuroRecompensa() + " ouro! (Total: " + jogador.getOuro() + ")");
        }
    }

    private static void usarInventario() {
        jogador.getInventario().mostrar();
        if (!jogador.getInventario().temItens()) {
            return;
        }

        System.out.print("Usar item? (número ou 0 para sair): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine());
            if (idx == 0) {
                return;
            }

            ItemRPG item = jogador.getInventario().usarItem(idx - 1);
            if (item != null && item.getTipo().equals("cura")) {
                jogador.curar(item.getValor());
                System.out.println("❤️ Vida recuperada! Vida: " + jogador.getVida() + "/" + jogador.getVidaMaxima());
            }
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida!");
        }
    }
}

