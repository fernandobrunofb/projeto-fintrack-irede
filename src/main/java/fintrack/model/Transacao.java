package fintrack.model;

import java.time.LocalDate;

public abstract class Transacao {
    protected String descricao;
    protected double valor;
    protected TipoTransacao tipo;
    protected LocalDate data;

    public Transacao(String descricao, double valor, TipoTransacao tipo, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public abstract  String exibirDetalhes();
}
