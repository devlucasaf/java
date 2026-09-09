package application.simuladores.elevador;

public class Passageiro {
    private int     andarOrigem;
    private int     andarDestino;
    private boolean noElevador;

    public Passageiro(int andarOrigem, int andarDestino) {
        this.andarOrigem = andarOrigem;
        this.andarDestino = andarDestino;
        this.noElevador = false;
    }

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
