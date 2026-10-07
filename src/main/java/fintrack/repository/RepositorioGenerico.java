package fintrack.repository;

import fintrack.model.Identificavel;

import java.util.ArrayList;
import java.util.List;

public class RepositorioGenerico<T extends Identificavel> implements Repositorio<T> {

    private final List<T> itens = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void salvar(T item) {
        item.setId(proximoId);
        proximoId++;
        itens.add(item);
    }

    @Override
    public T buscarPorId(int id) {
        for (T item : itens) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(itens);
    }

    @Override
    public boolean atualizar(T item) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getId() == item.getId()) {
                itens.set(i, item);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remover(int id) {
        return itens.removeIf(item -> item.getId() == id);
    }
}