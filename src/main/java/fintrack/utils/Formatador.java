package fintrack.utils;

public class Formatador {

    public static String formatarValor(double valor) {
        return String.format("R$ %.2f", valor);
    }
}