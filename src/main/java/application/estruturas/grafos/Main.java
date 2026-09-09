package application.estruturas.grafos;

public class Main {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        // --- VÉRTICES USADOS PELA HEURÍSTICA DO A* ---
        grafo.adicionarVertice("Brasilia", 0, 0);
        grafo.adicionarVertice("Goiania", -2, -1);
        grafo.adicionarVertice("Anapolis", -1, 0);
        grafo.adicionarVertice("Uberlandia", -4, -3);
        grafo.adicionarVertice("BeloHorizonte", -3, -6);
        grafo.adicionarVertice("SaoPaulo", -6, -8);
        grafo.adicionarVertice("Uberaba", -5, -4);

        // --- ARESTAS ---
        grafo.adicionarAresta("Brasilia", "Anapolis", 130);
        grafo.adicionarAresta("Anapolis", "Goiania", 55);
        grafo.adicionarAresta("Goiania", "Uberlandia", 340);
        grafo.adicionarAresta("Anapolis", "Uberlandia", 380);
        grafo.adicionarAresta("Uberlandia", "Uberaba", 100);
        grafo.adicionarAresta("Uberaba", "BeloHorizonte", 380);
        grafo.adicionarAresta("Uberlandia", "BeloHorizonte", 550);
        grafo.adicionarAresta("BeloHorizonte", "SaoPaulo", 590);
        grafo.adicionarAresta("Uberaba", "SaoPaulo", 480);

        String origem = "Brasilia";
        String destino = "SaoPaulo";

        System.out.println("=== Comparando Dijkstra vs A* ===");
        System.out.println("Origem: " + origem + " | Destino: " + destino);
        System.out.println();

        long inicioDijkstra = System.nanoTime();
        Resultado resultadoDijkstra = Dijkstra.calcular(grafo, origem, destino);
        long tempoDijkstra = System.nanoTime() - inicioDijkstra;

        long inicioAEstrela = System.nanoTime();
        Resultado resultadoAEstrela = AEstrela.calcular(grafo, origem, destino);
        long tempoAEstrela = System.nanoTime() - inicioAEstrela;

        System.out.println("--- Dijkstra ---");
        imprimirResultado(resultadoDijkstra, tempoDijkstra);

        System.out.println();
        System.out.println("--- A* (A-estrela) ---");
        imprimirResultado(resultadoAEstrela, tempoAEstrela);

        System.out.println();
        System.out.println("Ambos os algoritmos encontram o caminho de menor custo. A diferença");
        System.out.println("esta na quantidade de vertices visitados: o A* usa a heuristica de");
        System.out.println("distancia em linha reta para priorizar a busca na direcao do destino,");
        System.out.println("evitando explorar ramos do grafo que claramente nao levam a ele.");
    }

    private static void imprimirResultado(Resultado resultado, long tempoNano) {
        if (resultado.caminho.isEmpty()) {
            System.out.println("Nao existe caminho entre origem e destino.");
            return;
        }
        System.out.println("Caminho: " + String.join(" -> ", resultado.caminho));
        System.out.printf("Custo total: %.1f km%n", resultado.custoTotal);
        System.out.println("Vertices visitados: " + resultado.verticesVisitados);
        System.out.printf("Tempo de execucao: %.3f ms%n", tempoNano / 1_000_000.0);
    }
}
