package fintrack.dao;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.model.TransacaoAvulsa;
import fintrack.model.TransacaoMensal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransacaoDAOTest {

    private static final LocalDate DATA = LocalDate.of(2026, 10, 7);

    private Connection conexao;
    private TransacaoDAO dao;
    private Transacao mercado;
    private TransacaoMensal aluguel;

    @BeforeEach
    void preparar() throws SQLException, EntradaInvalidaException {
        conexao = DriverManager.getConnection("jdbc:sqlite::memory:");
        dao = new TransacaoDAO(conexao);

        mercado = new TransacaoAvulsa("Mercado", new BigDecimal("150.00"), TipoTransacao.SAIDA, DATA);
        aluguel = new TransacaoMensal("Aluguel", new BigDecimal("1200.00"), TipoTransacao.SAIDA, DATA, 10);
    }

    @AfterEach
    void encerrar() throws SQLException {
        conexao.close();
    }

    @Test
    void deveAtribuirIdGeradoPeloBancoAoSalvar() {
        dao.salvar(mercado);
        dao.salvar(aluguel);

        assertEquals(1, mercado.getId());
        assertEquals(2, aluguel.getId());
    }

    @Test
    void deveRecuperarTransacaoAvulsaComTodosOsCampos() {
        dao.salvar(mercado);

        Transacao lida = dao.buscarPorId(mercado.getId());

        assertNotSame(mercado, lida);
        assertInstanceOf(TransacaoAvulsa.class, lida);
        assertEquals(mercado.getId(), lida.getId());
        assertEquals("Mercado", lida.getDescricao());
        assertEquals(new BigDecimal("150.00"), lida.getValor());
        assertEquals(TipoTransacao.SAIDA, lida.getTipo());
        assertEquals(DATA, lida.getData());
    }

    @Test
    void deveRecuperarTransacaoMensalComDiaDeRecorrencia() {
        dao.salvar(aluguel);

        Transacao lida = dao.buscarPorId(aluguel.getId());

        TransacaoMensal mensal = assertInstanceOf(TransacaoMensal.class, lida);
        assertEquals(10, mensal.getDiaRecorrencia());
    }

    @Test
    void deveRetornarNullAoBuscarIdInexistente() {
        assertNull(dao.buscarPorId(99));
    }

    @Test
    void deveListarTodos() {
        dao.salvar(mercado);
        dao.salvar(aluguel);

        assertEquals(2, dao.listarTodos().size());
    }

    @Test
    void deveAtualizarTransacaoExistente() throws EntradaInvalidaException {
        dao.salvar(mercado);
        Transacao editada = new TransacaoAvulsa("Feira", new BigDecimal("80.00"), TipoTransacao.SAIDA, DATA);
        editada.setId(mercado.getId());

        assertTrue(dao.atualizar(editada));

        Transacao lida = dao.buscarPorId(mercado.getId());
        assertEquals("Feira", lida.getDescricao());
        assertEquals(new BigDecimal("80.00"), lida.getValor());
    }

    @Test
    void naoDeveAtualizarTransacaoInexistente() {
        mercado.setId(99);

        assertFalse(dao.atualizar(mercado));
    }

    @Test
    void deveRemoverPorId() {
        dao.salvar(mercado);

        assertTrue(dao.remover(mercado.getId()));
        assertNull(dao.buscarPorId(mercado.getId()));
    }

    @Test
    void naoDeveRemoverIdInexistente() {
        assertFalse(dao.remover(99));
    }
}