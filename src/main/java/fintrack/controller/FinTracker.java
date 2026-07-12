package fintrack.controller;

import fintrack.model.Transacao;
import java.util.ArrayList;

public class FinTracker {
    private ArrayList<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        System.out.println("Transação adicionada com sucesso!");
    }
}