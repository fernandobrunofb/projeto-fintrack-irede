package fintrack.model;

import fintrack.exceptions.EntradaInvalidaException;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Transacao implements Identificavel {
    protected int id;
    protected String descricao;
    protected BigDecimal valor;
    protected TipoTransacao tipo;
    protected LocalDate data;

    public Transacao(String descricao, BigDecimal valor, TipoTransacao tipo, LocalDate data)
            throws EntradaInvalidaException {
        if (descricao == null || descricao.isBlank()) {
            throw new EntradaInvalidaException("A descrição não pode ser vazia!");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EntradaInvalidaException("O valor deve ser maior que zero!");
        }
        if (tipo == null) {
            throw new EntradaInvalidaException("O tipo da transação é obrigatório!");
        }
        if (data == null) {
            throw new EntradaInvalidaException("A data é obrigatória!");
        }

        this.descricao = descricao.trim();
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

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public abstract  String exibirDetalhes();
}
