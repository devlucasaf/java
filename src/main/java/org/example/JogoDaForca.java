import java.util.*;

public class JogoDaForca {
    private static final Scanner scanner = new Scanner(System.in);

    public static List<String> palavraEscondida() {
        return Arrays.asList("banana", "lorenx", "batata", "amostradinho", "receba", "ceub", "apendicite", "guilherme", "santiago", "aula", "samambaia");
    }

    public static void linha() {
        System.out.println("+=+=".repeat(56));
    }

    public static String escolherPalavra(List<String> palavras) {
        Random random = new Random();
        return palavras.get(random.nextInt(palavras.size())).trim();
    }

    public static String mostrarPalavra(String palavra, Set<Character> letraAdvinhada) {
        StringBuilder resultado = new StringBuilder();
        for (char letra : palavra.toCharArray()) {
            if (letraAdvinhada.contains(letra)) {
                resultado.append(letra).append(" ");
            } else {
                resultado.append("_ ");
            }
        }
        return resultado.toString().trim();
    }

    public static void jogar() {
        int vida = 6;
        String coracao = "❤️";
        String palavraSecreta = escolherPalavra(palavraEscondida());
        Set<Character> letraAdvinhada = new HashSet<>();
        Set<Character> letraErrada = new HashSet<>();

        linha();
        System.out.printf("%112s%n", "Jogo da forca");
        linha();
        System.out.println("Essa sua palavra tem " + palavraSecreta.length() + " letras");

        while (vida > 0) {
            System.out.println(mostrarPalavra(palavraSecreta, letraAdvinhada));
            System.out.println("Você tem " + coracao.repeat(vida));

            System.out.print("Digite uma letra: ");
            String input = scanner.nextLine().toLowerCase();
            if (input.isEmpty()) continue;
            char letra = input.charAt(0);

            if (letraAdvinhada.contains(letra) || letraErrada.contains(letra)) {
                System.out.println("Você já digitou essa letra! Tente novamente!");
                continue;
            }

            if (palavraSecreta.indexOf(letra) >= 0) {
                letraAdvinhada.add(letra);
                
                boolean venceu = true;
                for (char c : palavraSecreta.toCharArray()) {
                    if (!letraAdvinhada.contains(c)) {
                        venceu = false;
                        break;
                    }
                }

                if (venceu) {
                    System.out.println("Você adivinhou a palavra secreta! A palavra secreta era: " + palavraSecreta);
                    break;
                }
            } else {
                letraErrada.add(letra);
                vida--;
                if (vida == 0) {
                    System.out.println("Você perdeu o jogo! A palavra era: " + palavraSecreta);
                }
            }
        }
    }

    public static void main(String[] args) {
        jogar();

        while (true) {
            System.out.print("Digite [SIM] para continuar ou [NAO] para fechar o jogo: ");
            String escolha = scanner.nextLine().toUpperCase();

            if (escolha.equals("SIM")) {
                jogar();
            } else if (escolha.equals("NAO")) {
                break;
            } else {
                System.out.println("A palavra digitada não é aceita! Tente novamente!");
            }
        }
    }
}
