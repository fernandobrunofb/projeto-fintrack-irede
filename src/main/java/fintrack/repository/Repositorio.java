package fintrack.repository;

import fintrack.model.Identificavel;
import java.util.List;

public interface Repositorio<T extends Identificavel> {

    void salvar(T item);

    T buscarPorId(int id);

    List<T> listarTodos();

    boolean atualizar(T item);

    boolean remover(int id);
}