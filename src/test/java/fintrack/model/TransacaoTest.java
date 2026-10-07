package fintrack.model;

import fintrack.exceptions.EntradaInvalidaException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransacaoTest {

    private static final LocalDate DATA = LocalDate.of(2026, 10, 7);
    private static final BigDecimal VALOR = new BigDecimal("150.00");

    @Test
    void deveCriarTransacaoValida() throws EntradaInvalidaException {
        Transacao t = new TransacaoAvulsa("Mercado", VALOR, TipoTransacao.SAIDA, DATA);

        assertEquals("Mercado", t.getDescricao());
        assertEquals(VALOR, t.getValor());
        assertEquals(TipoTransacao.SAIDA, t.getTipo());
        assertEquals(DATA, t.getData());
    }

    @Test
    void deveRemoverEspacosDasPontasDaDescricao() throws EntradaInvalidaException {
        Transacao t = new TransacaoAvulsa("  Mercado  ", VALOR, TipoTransacao.SAIDA, DATA);

        assertEquals("Mercado", t.getDescricao());
    }

    @Test
    void naoDeveAceitarValorNegativo() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoAvulsa("Mercado", new BigDecimal("-10.00"), TipoTransacao.SAIDA, DATA));
    }

    @Test
    void naoDeveAceitarValorZero() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoAvulsa("Mercado", BigDecimal.ZERO, TipoTransacao.SAIDA, DATA));
    }

    @Test
    void naoDeveAceitarValorNulo() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoAvulsa("Mercado", null, TipoTransacao.SAIDA, DATA));
    }

    @Test
    void naoDeveAceitarDescricaoVazia() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoAvulsa("", VALOR, TipoTransacao.SAIDA, DATA));
    }

    @Test
    void naoDeveAceitarDescricaoSoComEspacos() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoAvulsa("   ", VALOR, TipoTransacao.SAIDA, DATA));
    }

    @Test
    void naoDeveAceitarDescricaoNula() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoAvulsa(null, VALOR, TipoTransacao.SAIDA, DATA));
    }

    @Test
    void naoDeveAceitarTipoNulo() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoAvulsa("Mercado", VALOR, null, DATA));
    }

    @Test
    void naoDeveAceitarDataNula() {
        assertThrows(EntradaInvalidaException.class, () ->
                new TransacaoAvulsa("Mercado", VALOR, TipoTransacao.SAIDA, null));
    }

    @Test
    void deveIniciarSemId() throws EntradaInvalidaException {
        Transacao t = new TransacaoAvulsa("Mercado", VALOR, TipoTransacao.SAIDA, DATA);

        assertEquals(0, t.getId());
    }

    @Test
    void devePermitirDefinirId() throws EntradaInvalidaException {
        Transacao t = new TransacaoAvulsa("Mercado", VALOR, TipoTransacao.SAIDA, DATA);

        t.setId(5);

        assertEquals(5, t.getId());
    }
}