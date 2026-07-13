package fintrack.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LeitorEntrada {

    public static int lerInteiro(Scanner scanner, String mensagem) {
        int valor = 0;
        boolean valido = false;

        do {
            System.out.print(mensagem);
            try {
                valor = scanner.nextInt();
                scanner.nextLine();
                valido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
                scanner.nextLine();
            }
        } while (!valido);

        return valor;
    }
}