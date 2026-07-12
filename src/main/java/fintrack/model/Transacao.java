package fintrack.model;

public abstract class Transacao {
    protected String descricao;
    protected double valor;
    protected String tipo;

    public Transacao(String descricao, double valor, String tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public abstract  String exibirDetalhes();
}
