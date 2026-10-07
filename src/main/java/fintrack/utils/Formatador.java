package fintrack.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Formatador {

    public static String formatarValor(BigDecimal valor) {
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        return moeda.format(valor);
    }

    public static String formatarData(LocalDate data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formato);
    }
}