package fintrack.model;

public enum TipoTransacao {
    ENTRADA("Entrada"),
    SAIDA("Saída");

    private final String rotulo;

    TipoTransacao(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }
}