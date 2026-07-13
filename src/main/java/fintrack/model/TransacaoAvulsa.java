package fintrack.model;

import java.time.LocalDate;

public class TransacaoAvulsa extends Transacao {

    public TransacaoAvulsa(String descricao, double valor, TipoTransacao tipo, LocalDate data) {
        super(descricao, valor, tipo, data);
    }

    @Override
    public String exibirDetalhes() {
        return "[Avulsa] " + descricao + " – R$ " + valor + " (" + tipo + ") – Data: " + data;
    }
}
