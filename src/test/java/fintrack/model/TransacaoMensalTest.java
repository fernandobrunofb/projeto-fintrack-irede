package fintrack.model;

import fintrack.exceptions.EntradaInvalidaException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransacaoMensalTest {

    private static final LocalDate DATA = LocalDate.of(2026, 10, 7);
    private static final BigDecimal VALOR = new BigDecimal("1200.00");

    @Test
    void naoDeveAceitarDiaZero() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoMensal("Aluguel", VALOR, TipoTransacao.SAIDA, DATA, 0));
    }

    @Test
    void naoDeveAceitarDiaMaiorQue31() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoMensal("Aluguel", VALOR, TipoTransacao.SAIDA, DATA, 32));
    }

    @Test
    void deveAceitarDia1() {
        assertDoesNotThrow(() ->
                new TransacaoMensal("Aluguel", VALOR, TipoTransacao.SAIDA, DATA, 1));
    }

    @Test
    void deveAceitarDia31() {
        assertDoesNotThrow(() ->
                new TransacaoMensal("Aluguel", VALOR, TipoTransacao.SAIDA, DATA, 31));
    }
}