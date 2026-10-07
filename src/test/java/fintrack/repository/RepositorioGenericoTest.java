package fintrack.repository;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.model.TransacaoAvulsa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RepositorioGenericoTest {

    private RepositorioGenerico<Transacao> repositorio;
    private Transacao mercado;
    private Transacao salario;

    @BeforeEach
    void preparar() throws EntradaInvalidaException {
        repositorio = new RepositorioGenerico<>();
        LocalDate data = LocalDate.of(2026, 10, 7);
        mercado = new TransacaoAvulsa("Mercado", new BigDecimal("150.00"), TipoTransacao.SAIDA, data);
        salario = new TransacaoAvulsa("Salário", new BigDecimal("3000.00"), TipoTransacao.ENTRADA, data);
    }

    @Test
    void deveAtribuirIdsEmSequenciaAoSalvar() {
        repositorio.salvar(mercado);
        repositorio.salvar(salario);

        assertEquals(1, mercado.getId());
        assertEquals(2, salario.getId());
    }

    @Test
    void deveBuscarPorId() {
        repositorio.salvar(mercado);

        assertSame(mercado, repositorio.buscarPorId(mercado.getId()));
    }

    @Test
    void deveRetornarNullAoBuscarIdInexistente() {
        assertNull(repositorio.buscarPorId(99));
    }

    @Test
    void deveListarTodos() {
        repositorio.salvar(mercado);
        repositorio.salvar(salario);

        assertEquals(2, repositorio.listarTodos().size());
    }

    @Test
    void deveRemoverPorId() {
        repositorio.salvar(mercado);

        assertTrue(repositorio.remover(mercado.getId()));
        assertNull(repositorio.buscarPorId(mercado.getId()));
    }

    @Test
    void naoDeveRemoverIdInexistente() {
        assertFalse(repositorio.remover(99));
    }

    @Test
    void deveAtualizarItemExistente() throws EntradaInvalidaException {
        repositorio.salvar(mercado);
        Transacao editada = new TransacaoAvulsa("Feira", new BigDecimal("80.00"), TipoTransacao.SAIDA, mercado.getData());
        editada.setId(mercado.getId());

        assertTrue(repositorio.atualizar(editada));
        assertEquals("Feira", repositorio.buscarPorId(mercado.getId()).getDescricao());
    }

    @Test
    void naoDeveAtualizarItemInexistente() {
        mercado.setId(99);

        assertFalse(repositorio.atualizar(mercado));
    }
}