package fintrack.model;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.utils.Formatador;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoAvulsa extends Transacao {

    public TransacaoAvulsa(String descricao, BigDecimal valor, TipoTransacao tipo, LocalDate data)
            throws EntradaInvalidaException {
        super(descricao, valor, tipo, data);
    }

    @Override
    public String exibirDetalhes() {
        return "[Avulsa] " + descricao + " - " + Formatador.formatarValor(valor) + " (" + tipo + ") - Data: " + Formatador.formatarData(data);
    }
}
