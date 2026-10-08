package fintrack.service;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.model.TransacaoAvulsa;
import fintrack.model.TransacaoMensal;
import fintrack.repository.RepositorioGenerico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinTrackerTest {

    private static final LocalDate DATA = LocalDate.of(2026, 10, 8);

    private FinTracker finTracker;

    @BeforeEach
    void preparar() {
        finTracker = new FinTracker(new RepositorioGenerico<>());
    }

    @Test
    void deveCadastrarEListarTransacoes() throws EntradaInvalidaException {
        Transacao salario = new TransacaoAvulsa("Salário", new BigDecimal("3000.00"), TipoTransacao.ENTRADA, DATA);

        finTracker.cadastrar(salario);

        assertEquals(1, finTracker.listar().size());
        assertTrue(salario.getId() > 0);
    }

    @Test
    void deveRemoverTransacao() throws EntradaInvalidaException {
        Transacao salario = new TransacaoAvulsa("Salário", new BigDecimal("3000.00"), TipoTransacao.ENTRADA, DATA);
        finTracker.cadastrar(salario);

        assertTrue(finTracker.remover(salario.getId()));
        assertTrue(finTracker.listar().isEmpty());
    }

    @Test
    void saldoDeveSerZeroSemTransacoes() {
        assertEquals(BigDecimal.ZERO, finTracker.calcularSaldo());
    }

    @Test
    void deveCalcularTotaisESaldoComEntradasESaidas() throws EntradaInvalidaException {
        finTracker.cadastrar(new TransacaoAvulsa("Salário", new BigDecimal("3000.00"), TipoTransacao.ENTRADA, DATA));
        finTracker.cadastrar(new TransacaoAvulsa("Freela", new BigDecimal("500.00"), TipoTransacao.ENTRADA, DATA));
        finTracker.cadastrar(new TransacaoAvulsa("Mercado", new BigDecimal("150.00"), TipoTransacao.SAIDA, DATA));
        finTracker.cadastrar(new TransacaoMensal("Aluguel", new BigDecimal("1200.00"), TipoTransacao.SAIDA, DATA, 10));

        assertEquals(new BigDecimal("3500.00"), finTracker.calcularTotalEntradas());
        assertEquals(new BigDecimal("1350.00"), finTracker.calcularTotalSaidas());
        assertEquals(new BigDecimal("2150.00"), finTracker.calcularSaldo());
    }

    @Test
    void saldoPodeSerNegativo() throws EntradaInvalidaException {
        finTracker.cadastrar(new TransacaoAvulsa("Salário", new BigDecimal("1000.00"), TipoTransacao.ENTRADA, DATA));
        finTracker.cadastrar(new TransacaoMensal("Aluguel", new BigDecimal("1200.00"), TipoTransacao.SAIDA, DATA, 10));

        assertEquals(new BigDecimal("-200.00"), finTracker.calcularSaldo());
    }

    @Test
    void deveCalcularSaldoDeUmaListaSoDeMensais() throws EntradaInvalidaException {
        List<TransacaoMensal> mensais = List.of(
                new TransacaoMensal("Aluguel", new BigDecimal("1200.00"), TipoTransacao.SAIDA, DATA, 10),
                new TransacaoMensal("Salário", new BigDecimal("3000.00"), TipoTransacao.ENTRADA, DATA, 5)
        );

        assertEquals(new BigDecimal("1800.00"), FinTracker.calcularSaldo(mensais));
    }
}