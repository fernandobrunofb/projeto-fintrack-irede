package fintrack.dao;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.exceptions.PersistenciaException;
import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.model.TransacaoAvulsa;
import fintrack.model.TransacaoMensal;
import fintrack.repository.Repositorio;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransacaoDAO implements Repositorio<Transacao> {

    private static final Logger LOGGER = Logger.getLogger(TransacaoDAO.class.getName());

    private final Connection conexao;

    public TransacaoDAO(Connection conexao) {
        this.conexao = conexao;
        criarTabela();
    }

    private void criarTabela() {
        String sql = """
                CREATE TABLE IF NOT EXISTS transacao (
                    id              INTEGER PRIMARY KEY AUTOINCREMENT,
                    descricao       TEXT    NOT NULL,
                    valor           TEXT    NOT NULL,
                    tipo            TEXT    NOT NULL,
                    data            TEXT    NOT NULL,
                    frequencia      TEXT    NOT NULL,
                    dia_recorrencia INTEGER
                )
                """;

        try (Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao criar a tabela de transações", e);
            throw new PersistenciaException("Erro ao criar a tabela de transações", e);
        }
    }

    @Override
    public void salvar(Transacao item) {
        String sql = "INSERT INTO transacao (descricao, valor, tipo, data, frequencia, dia_recorrencia) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherParametros(ps, item);
            ps.executeUpdate();

            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    item.setId(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar transação", e);
            throw new PersistenciaException("Erro ao salvar transação", e);
        }
    }

    @Override
    public Transacao buscarPorId(int id) {
        String sql = "SELECT * FROM transacao WHERE id = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montarTransacao(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar transação " + id, e);
            throw new PersistenciaException("Erro ao buscar transação", e);
        }
    }

    @Override
    public List<Transacao> listarTodos() {
        String sql = "SELECT * FROM transacao ORDER BY data, id";
        List<Transacao> transacoes = new ArrayList<>();

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transacoes.add(montarTransacao(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar transações", e);
            throw new PersistenciaException("Erro ao listar transações", e);
        }

        return transacoes;
    }

    @Override
    public boolean atualizar(Transacao item) {
        String sql = "UPDATE transacao SET descricao = ?, valor = ?, tipo = ?, data = ?, "
                + "frequencia = ?, dia_recorrencia = ? WHERE id = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            preencherParametros(ps, item);
            ps.setInt(7, item.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar transação " + item.getId(), e);
            throw new PersistenciaException("Erro ao atualizar transação", e);
        }
    }

    @Override
    public boolean remover(int id) {
        String sql = "DELETE FROM transacao WHERE id = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao remover transação " + id, e);
            throw new PersistenciaException("Erro ao remover transação", e);
        }
    }

    private void preencherParametros(PreparedStatement ps, Transacao item) throws SQLException {
        ps.setString(1, item.getDescricao());
        ps.setString(2, item.getValor().toPlainString());
        ps.setString(3, item.getTipo().name());
        ps.setString(4, item.getData().toString());

        if (item instanceof TransacaoMensal mensal) {
            ps.setString(5, "MENSAL");
            ps.setInt(6, mensal.getDiaRecorrencia());
        } else {
            ps.setString(5, "AVULSA");
            ps.setNull(6, Types.INTEGER);
        }
    }

    private Transacao montarTransacao(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String descricao = rs.getString("descricao");
        BigDecimal valor = new BigDecimal(rs.getString("valor"));
        TipoTransacao tipo = TipoTransacao.valueOf(rs.getString("tipo"));
        LocalDate data = LocalDate.parse(rs.getString("data"));

        try {
            Transacao transacao;
            if ("MENSAL".equals(rs.getString("frequencia"))) {
                transacao = new TransacaoMensal(descricao, valor, tipo, data, rs.getInt("dia_recorrencia"));
            } else {
                transacao = new TransacaoAvulsa(descricao, valor, tipo, data);
            }
            transacao.setId(id);
            return transacao;
        } catch (EntradaInvalidaException e) {
            LOGGER.log(Level.SEVERE, "Transação inválida no banco, id " + id, e);
            throw new PersistenciaException("Transação inválida no banco, id " + id, e);
        }
    }
}