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
        String descricao = "";
        boolean descricaoValida = false;

        do {
            try {
                System.out.print("Descrição: ");
                descricao = scanner.nextLine().trim();

                if (descricao.isBlank()) {
                    throw new EntradaInvalidaException("A descrição não pode ser vazia!");
                }

                descricaoValida = true;
            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (!descricaoValida);

        double valor = 0;
        boolean valorValido = false;

        do {
            try {
                valor = LeitorEntrada.lerDouble(scanner, "Valor: ");

                if (valor <= 0) {
                    throw new EntradaInvalidaException("O valor deve ser maior que zero!");
                }

                valorValido = true;
            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (!valorValido);

        int tipoEscolhido = 0;
        boolean tipoValido = false;

        do {
            try {
                tipoEscolhido = LeitorEntrada.lerInteiro(scanner, "Tipo (1 - entrada / 2 - saida): ");

                if (tipoEscolhido != 1 && tipoEscolhido != 2) {
                    throw new EntradaInvalidaException("Opção inválida! Digite 1 ou 2.");
                }

                tipoValido = true;
            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (!tipoValido);

        TipoTransacao tipo = (tipoEscolhido == 1) ? TipoTransacao.ENTRADA : TipoTransacao.SAIDA;

        LocalDate dataAtual = LocalDate.now();

        int resposta = 0;
        boolean respostaValida = false;

        do {
            try {
                resposta = LeitorEntrada.lerInteiro(scanner, "É uma transação mensal ou avulsa? (1 - mensal / 2 - avulsa): ");

                if (resposta != 1 && resposta != 2) {
                    throw new EntradaInvalidaException("Opção inválida! Digite 1 ou 2.");
                }

                respostaValida = true;
            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (!respostaValida);

        Transacao novaTransacao;
        if (resposta == 1) {
            int dia = 0;
            boolean diaValido = false;

            do {
                try {
                    dia = LeitorEntrada.lerInteiro(scanner, "Dia da recorrência: ");

                    if (dia < 1 || dia > 31) {
                        throw new EntradaInvalidaException("Dia inválido! Digite um valor entre 1 e 31.");
                    }

                    diaValido = true;
                } catch (EntradaInvalidaException e) {
                    System.out.println(e.getMessage());
                }
            } while (!diaValido);

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

        int indice = 0;
        boolean indiceValido = false;

        do {
            try {
                indice = LeitorEntrada.lerInteiro(scanner, "Digite o índice da transação que deseja remover: ");

                if (indice < 0 || indice >= transacoes.size()) {
                    throw new EntradaInvalidaException("Índice inválido! Digite um valor entre 0 e " + (transacoes.size() - 1) + ".");
                }

                indiceValido = true;
            } catch (EntradaInvalidaException e) {
                System.out.println(e.getMessage());
            }
        } while (!indiceValido);

        Transacao removida = transacoes.remove(indice);
        System.out.println("Transação removida: " + removida.exibirDetalhes());
    }

}