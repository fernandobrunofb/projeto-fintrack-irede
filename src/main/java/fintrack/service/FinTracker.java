package fintrack.service;

import fintrack.model.TipoTransacao;
import fintrack.model.Transacao;
import fintrack.repository.Repositorio;

import java.math.BigDecimal;
import java.util.List;

public class FinTracker {

    private final Repositorio<Transacao> repositorio;

    public FinTracker(Repositorio<Transacao> repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrar(Transacao transacao) {
        repositorio.salvar(transacao);
    }

    public List<Transacao> listar() {
        return repositorio.listarTodos();
    }

    public Transacao buscarPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public boolean atualizar(Transacao transacao) {
        return repositorio.atualizar(transacao);
    }

    public boolean remover(int id) {
        return repositorio.remover(id);
    }

    public BigDecimal calcularTotalEntradas() {
        return somarPorTipo(listar(), TipoTransacao.ENTRADA);
    }

    public BigDecimal calcularTotalSaidas() {
        return somarPorTipo(listar(), TipoTransacao.SAIDA);
    }

    public BigDecimal calcularSaldo() {
        return calcularSaldo(listar());
    }

    public static BigDecimal calcularSaldo(List<? extends Transacao> transacoes) {
        BigDecimal entradas = somarPorTipo(transacoes, TipoTransacao.ENTRADA);
        BigDecimal saidas = somarPorTipo(transacoes, TipoTransacao.SAIDA);
        return entradas.subtract(saidas);
    }

    private static BigDecimal somarPorTipo(List<? extends Transacao> transacoes, TipoTransacao tipo) {
        BigDecimal total = BigDecimal.ZERO;
        for (Transacao t : transacoes) {
            if (t.getTipo() == tipo) {
                total = total.add(t.getValor());
            }
        }
        return total;
    }
}