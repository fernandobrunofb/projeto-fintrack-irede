package fintrack.model;

public class TransacaoAvulsa extends Transacao {

    public TransacaoAvulsa(String descricao, double valor, String tipo) {
        super(descricao, valor, tipo);
    }

    @Override
    public String exibirDetalhes() {
        return "[Avulsa] " + descricao + " — R$ " + valor + " (" + tipo + ")";
    }
}