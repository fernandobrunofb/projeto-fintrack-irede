package fintrack.model;

import fintrack.utils.Formatador;

import java.time.LocalDate;

public class TransacaoMensal extends Transacao {
    private int diaRecorrencia;

    public TransacaoMensal(String descricao, double valor, TipoTransacao tipo, LocalDate data, int diaRecorrencia) {
        super(descricao, valor, tipo, data);
        this.diaRecorrencia = diaRecorrencia;
    }

    public int getDiaRecorrencia() {
        return diaRecorrencia;
    }

    @Override
    public String exibirDetalhes() {
        return "[Mensal - dia " + diaRecorrencia + "] " + descricao + " - " + Formatador.formatarValor(valor) + " (" + tipo + ") - Cadastrado em: " + data;
    }
}