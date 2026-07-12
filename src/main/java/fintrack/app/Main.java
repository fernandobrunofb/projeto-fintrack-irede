package fintrack.app;

import fintrack.model.Transacao;
import fintrack.model.TransacaoMensal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("""
                    ===== FINTRACK — SEU CONTROLE FINANCEIRO =====
                    1. Adicionar nova transação
                    2. Listar transações
                    3. Mostrar saldo atual
                    4. Remover transação
                    5. Sair""");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> System.out.println("[TODO] adicionar transação");
                case 2 -> System.out.println("[TODO] listar transações");
                case 3 -> System.out.println("[TODO] mostrar saldo");
                case 4 -> System.out.println("[TODO] remover transação");
                case 5 -> System.out.println("Saindo... até mais!");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 5);

        scanner.close();

    }

}