package fintrack.model;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.utils.Formatador;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoMensal extends Transacao {
    private int diaRecorrencia;

    public TransacaoMensal(String descricao, BigDecimal valor, TipoTransacao tipo, LocalDate data, int diaRecorrencia)
            throws EntradaInvalidaException {
        super(descricao, valor, tipo, data);
        if (diaRecorrencia < 1 || diaRecorrencia > 31) {
            throw new EntradaInvalidaException("O dia de recorrência deve estar entre 1 e 31.");
        }
        this.diaRecorrencia = diaRecorrencia;
    }

    public int getDiaRecorrencia() {
        return diaRecorrencia;
    }

    @Override
    public String exibirDetalhes() {
        return "[Mensal - dia " + diaRecorrencia + "] " + descricao + " - " + Formatador.formatarValor(valor) + " (" + tipo + ") - Data: " + Formatador.formatarData(data);
    }
}