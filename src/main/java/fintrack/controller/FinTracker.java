package fintrack.controller;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.model.TransacaoAvulsa;
import fintrack.model.TransacaoMensal;
import fintrack.utils.LeitorEntrada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FinTracker {
    private ArrayList<Transacao> transacoes = new ArrayList<>();

    public void cadastrarTransacao(Scanner scanner) {
        try {
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            if (descricao.isBlank()) {
                throw new EntradaInvalidaException("A descrição não pode ser vazia.");
            }

            double valor = LeitorEntrada.lerDouble(scanner, "Valor: ");

            if (valor <= 0) {
                throw new EntradaInvalidaException("O valor deve ser maior que zero.");
            }

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

                if (dia < 1 || dia > 31) {
                    throw new EntradaInvalidaException("O dia de recorrência deve estar entre 1 e 31.");
                }

                novaTransacao = new TransacaoMensal(descricao, valor, tipo, dataAtual, dia);
            } else {
                novaTransacao = new TransacaoAvulsa(descricao, valor, tipo, dataAtual);
            }

            adicionarTransacao(novaTransacao);

        } catch (EntradaInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
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