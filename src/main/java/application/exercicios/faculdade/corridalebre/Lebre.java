package application.exercicios.faculdade.corridalebre;

import java.util.Random;

public class Lebre implements Runnable {
    private final   String          nome;
    private final   String          corHexadecimal;
    private final   String          corANSI;
    private         int             distanciaPercorrida = 0;
    private         int             totalPulos = 0;
    private         long            tempoChegada = Long.MAX_VALUE;
    static final    int             distanciaTotal = 20;

    public Lebre(String nome, String corHexadecimal) {
        this.nome = nome;
        this.corHexadecimal = corHexadecimal;
        this.corANSI = ANSIConverter.getANSIColor(corHexadecimal);
    }

    @Override
    public void run() {
        Random rand = new Random();

        while (distanciaPercorrida < distanciaTotal) {
            int salto = rand.nextInt(3) + 1;

            distanciaPercorrida += salto;
            totalPulos++;

            int distanciaAtual = Math.min(distanciaPercorrida, distanciaTotal);

            System.out.printf("%s[%s]%s Pulou %d metros. Total percorrido: %d/%d metros.\n",
                    corANSI, nome, ANSIConverter.RESET, salto, distanciaAtual, distanciaTotal);

            if (distanciaPercorrida >= distanciaTotal) {
                tempoChegada = System.currentTimeMillis();
                System.out.printf("%s>>> %s CRUZOU A LINHA DE CHEGADA!%s\n", corANSI, nome, ANSIConverter.RESET);
                break;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(nome + " foi interrompida.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public int getTotalPulos() {
        return totalPulos;
    }

    public long getTempoChegada() {
        return tempoChegada;
    }

    public int getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public String getCorANSI() {
        return corANSI;
    }
}

