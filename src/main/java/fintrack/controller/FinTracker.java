package fintrack.controller;

import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import java.util.ArrayList;

public class FinTracker {
    private ArrayList<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        System.out.println("Transação adicionada com sucesso!");
    }

    public void listarTransacoes() {
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação cadastrada.");
            return;
        }

        for (int i = 0; i < transacoes.size(); i++) {
            System.out.println(i + " - " + transacoes.get(i).exibirDetalhes());
        }
    }

    public void calcularSaldoTotal() {
        double saldo = 0;

        for (Transacao t : transacoes) {
            if (t.getTipo() == TipoTransacao.ENTRADA) {
                saldo += t.getValor();
            } else if (t.getTipo() == TipoTransacao.SAIDA) {
                saldo -= t.getValor();
            }
        }

        System.out.println("Saldo atual: R$ " + saldo);
    }

}