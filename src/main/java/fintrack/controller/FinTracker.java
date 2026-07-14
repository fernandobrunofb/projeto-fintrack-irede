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
                tipoEscolhido = LeitorEntrada.lerInteiro(scanner, "Tipo (1 - entrada / 2 - saida): ");

                if (tipoEscolhido != 1 && tipoEscolhido != 2) {
                    System.out.println("Opção inválida! Digite 1 ou 2.");
                }
            } while (tipoEscolhido != 1 && tipoEscolhido != 2);

            TipoTransacao tipo = (tipoEscolhido == 1) ? TipoTransacao.ENTRADA : TipoTransacao.SAIDA;

            LocalDate dataAtual = LocalDate.now();

            int resposta;
            do {
                resposta = LeitorEntrada.lerInteiro(scanner, "É uma transação mensal ou avulsa? (1 - mensal / 2 - avulsa): ");

                if (resposta != 1 && resposta != 2) {
                    System.out.println("Opção inválida! Digite 1 ou 2.");
                }
            } while (resposta != 1 && resposta != 2);

            Transacao novaTransacao;
            if (resposta == 1) {
                int dia;
                do {
                    dia = LeitorEntrada.lerInteiro(scanner, "Dia da recorrência: ");

                    if (dia < 1 || dia > 31) {
                        System.out.println("Dia inválido! Digite um valor entre 1 e 31.");
                    }
                } while (dia < 1 || dia > 31);

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

        int indice;
        do {
            indice = LeitorEntrada.lerInteiro(scanner, "Digite o índice da transação que deseja remover: ");

            if (indice < 0 || indice >= transacoes.size()) {
                System.out.println("Índice inválido! Digite um valor entre 0 e " + (transacoes.size() - 1) + ".");
            }
        } while (indice < 0 || indice >= transacoes.size());

        Transacao removida = transacoes.remove(indice);
        System.out.println("Transação removida: " + removida.exibirDetalhes());
    }

}