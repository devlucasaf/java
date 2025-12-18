package org.example;

import java.util.*;

public class Votacao {

    static ArrayList<Integer> votosTotais = new ArrayList<>();
    static String eleito = "";

    public static void main(String[] args) {
        System.out.println("""
                Escolha entre as seguintes opções de candidatos:
                22 - Leanderson
                13 - Mario Bitcoin
                14 - Lucao
                0  - NULO
                9  - Encerrar
                """);

        eleicao();
    }

    public static void eleicao() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Digite seu voto: ");
            int voto = sc.nextInt();

            if (voto == 9) {
                System.out.println("Votação encerrada!");
                break;
            }

            if (voto == 22 || voto == 13 || voto == 14 || voto == 0) {
                System.out.println("Voto Confirmado!");
                votosTotais.add(voto);
            } else {
                System.out.println("Número digitado não identificado a nenhum candidato! Tente outro número!");
            }

            // Atualiza quem está na frente (temporário)
            if (Collections.frequency(votosTotais, 22) > Collections.frequency(votosTotais, 13)
                    && Collections.frequency(votosTotais, 22) > Collections.frequency(votosTotais, 14)) {
                eleito = "Leanderson";
            } else if (Collections.frequency(votosTotais, 13) > Collections.frequency(votosTotais, 22)
                    && Collections.frequency(votosTotais, 13) > Collections.frequency(votosTotais, 14)) {
                eleito = "Mario Bitcoin";
            } else if (Collections.frequency(votosTotais, 14) > Collections.frequency(votosTotais, 22)
                    && Collections.frequency(votosTotais, 14) > Collections.frequency(votosTotais, 13)) {
                eleito = "Lucao";
            }
        }

        if (votosTotais.size() != 0) {
            int votosLeanderson = Collections.frequency(votosTotais, 22);
            int votosMario = Collections.frequency(votosTotais, 13);
            int votosLucao = Collections.frequency(votosTotais, 14);
            int votosNulo = Collections.frequency(votosTotais, 0);
            int totalVotos = votosTotais.size();

            // Dicionário (map) de resultados
            Map<String, Integer> resultados = new HashMap<>();
            resultados.put("Leanderson", votosLeanderson);
            resultados.put("Mario Bitcoin", votosMario);
            resultados.put("Lucao", votosLucao);

            // Determina o candidato eleito (com mais votos)
            eleito = Collections.max(resultados.entrySet(), Map.Entry.comparingByValue()).getKey();
            int votosEleito = resultados.get(eleito);

            // Calcula porcentagens
            double porc1 = (votosLeanderson / (double) totalVotos) * 100;
            double porc2 = (votosMario / (double) totalVotos) * 100;
            double porc3 = (votosLucao / (double) totalVotos) * 100;
            double porcNull = (votosNulo / (double) totalVotos) * 100;

            // Caso todos os votos sejam nulos
            if (votosNulo > votosEleito) {
                System.out.println("Votação anulada! Os votos nulos venceram com " + votosNulo);
                System.out.println("\nVotação recomeçada!\n");

                System.out.println("""
                        Escolha entre as seguintes opções de candidatos:
                        22 - Leanderson
                        13 - Mario Bitcoin
                        14 - Lucao
                        0  - NULO
                        9  - Encerrar
                        """);

                votosTotais.clear();
                eleicao();
                return;
            } else {
                // Exibe os resultados
                if (totalVotos == 1) {
                    System.out.println("Nesta eleição houve " + totalVotos + " voto!");
                } else {
                    System.out.println("Nesta eleição houve " + totalVotos + " votos!");
                    System.out.printf("Leanderson obteve %d votos com %.2f%%\n", votosLeanderson, porc1);
                    System.out.printf("Mario Bitcoin obteve %d votos com %.2f%%\n", votosMario, porc2);
                    System.out.printf("Lucao obteve %d votos com %.2f%%\n", votosLucao, porc3);
                    System.out.printf("Obteve %d votos nulos com %.2f%%\n", votosNulo, porcNull);
                    System.out.printf("O candidato %s foi eleito com %d votos!\n", eleito, votosEleito);
                }
            }

        }
        else {
            System.out.println("Não se obteve votos.");
        }
    }
}
