package fintrack.app;

import fintrack.controller.FinTracker;
import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.model.TransacaoAvulsa;
import fintrack.model.TransacaoMensal;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FinTracker fintracker = new FinTracker();

        int opcao;

        do {
            System.out.println("""
                    ===== FINTRACK – SEU CONTROLE FINANCEIRO =====
                    1. Adicionar nova transação
                    2. Listar transações
                    3. Mostrar saldo atual
                    4. Remover transação
                    5. Sair""");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Descrição: ");
                    scanner.nextLine();
                    String descricao = scanner.nextLine();

                    System.out.print("Valor: ");
                    double valor = scanner.nextDouble();
                    scanner.nextLine();

                    int tipoEscolhido;
                    do {
                        System.out.print("Tipo (1 - entrada / 2 - saída): ");
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

                    fintracker.adicionarTransacao(novaTransacao);
                }
                case 2 -> fintracker.listarTransacoes();
                case 3 -> System.out.println("[TODO] mostrar saldo");
                case 4 -> System.out.println("[TODO] remover transação");
                case 5 -> System.out.println("Saindo... até mais!");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 5);

        scanner.close();
    }
}