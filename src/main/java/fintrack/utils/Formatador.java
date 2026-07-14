package fintrack.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Formatador {

    public static String formatarValor(double valor) {
        return String.format("R$ %.2f", valor);
    }

    public static String formatarData(LocalDate data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formato);
    }
}