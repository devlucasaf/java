package games.text.historia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class No {

    private final String        texto;
    private final List<String>  opcoes;
    private final List<String>  destinos;
    private final boolean       finalDaHistoria;

    public No(String texto, String[] opcoes, String[] destinos, boolean finalDaHistoria) {
        this.texto = texto;
        this.opcoes = opcoes == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(opcoes));
        this.destinos = destinos == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(destinos));
        this.finalDaHistoria = finalDaHistoria;
    }

    public String getTexto() {
        return texto;
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public List<String> getDestinos() {
        return destinos;
    }

    public boolean isFinal() {
        return finalDaHistoria;
    }
}

