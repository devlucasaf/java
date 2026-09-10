package games.text.tradutor;

public class Palavra {

    private final String original;
    private final String traducao;
    private final String idioma;

    public Palavra(String original, String traducao, String idioma) {
        this.original = original;
        this.traducao = traducao;
        this.idioma = idioma;
    }

    public String getOriginal() {
        return original;
    }

    public String getTraducao() {
        return traducao;
    }

    public String getIdioma() {
        return idioma;
    }
}

