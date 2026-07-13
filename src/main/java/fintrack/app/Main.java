package fintrack.app;

import fintrack.controller.FinTracker;
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
                case 1 -> fintracker.cadastrarTransacao(scanner);
                case 2 -> fintracker.listarTransacoes();
                case 3 -> fintracker.calcularSaldoTotal();
                case 4 -> fintracker.removerTransacao(scanner);
                case 5 -> System.out.println("Saindo... até mais!");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 5);

        scanner.close();
    }
}