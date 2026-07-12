package fintrack.model;

public class TransacaoMensal extends Transacao {
    private int diaRecorrencia;

    public TransacaoMensal(String descricao, double valor, String tipo, int diaRecorrencia) {
        super(descricao, valor, tipo);
        this.diaRecorrencia = diaRecorrencia;
    }

    public int getDiaRecorrencia() {
        return diaRecorrencia;
    }

    @Override
    public String exibirDetalhes() {
        return "[Mensal — dia " + diaRecorrencia + "] " + descricao + " — R$ " + valor + " (" + tipo + ")";
    }
}