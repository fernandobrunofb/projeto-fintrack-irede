package fintrack.controller;

import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.model.TransacaoAvulsa;
import fintrack.model.TransacaoMensal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FinTracker {
    private ArrayList<Transacao> transacoes = new ArrayList<>();

    public void cadastrarTransacao(Scanner scanner) {
        System.out.print("Descrição: ");
        scanner.nextLine();
        String descricao = scanner.nextLine();

        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        int tipoEscolhido;
        do {
            System.out.print("Tipo (1 - entrada / 2 - saida): ");
            tipoEscolhido = scanner.nextInt();
            scanner.nextLine();

            if (tipoEscolhido != 1 && tipoEscolhido != 2) {
                System.out.println("Opção inválida! Digite 1 ou 2.");
            }
        } while (tipoEscolhido != 1 && tipoEscolhido != 2);

        TipoTransacao tipo = (tipoEscolhido == 1) ? TipoTransacao.ENTRADA : TipoTransacao.SAIDA;

        LocalDate dataAtual = LocalDate.now();

        int resposta;
        do {
            System.out.print("É uma transação mensal ou avulsa? (1 - mensal / 2 - avulsa): ");
            resposta = scanner.nextInt();
            scanner.nextLine();

            if (resposta != 1 && resposta != 2) {
                System.out.println("Opção inválida! Digite 1 ou 2.");
            }
        } while (resposta != 1 && resposta != 2);

        Transacao novaTransacao;
        if (resposta == 1) {
            System.out.print("Dia da recorrência: ");
            int dia = scanner.nextInt();
            scanner.nextLine();
            novaTransacao = new TransacaoMensal(descricao, valor, tipo, dataAtual, dia);
        } else {
            novaTransacao = new TransacaoAvulsa(descricao, valor, tipo, dataAtual);
        }

        adicionarTransacao(novaTransacao);
    }

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

    public void removerTransacao(Scanner scanner) {
        listarTransacoes();

        if (transacoes.isEmpty()) {
            return;
        }

        System.out.print("Digite o índice da transação que deseja remover: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (indice < 0 || indice >= transacoes.size()) {
            System.out.println("Índice inválido!");
            return;
        }

        Transacao removida = transacoes.remove(indice);
        System.out.println("Transação removida: " + removida.exibirDetalhes());
    }

}