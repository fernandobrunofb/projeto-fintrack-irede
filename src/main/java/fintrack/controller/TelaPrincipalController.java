package fintrack.controller;

import fintrack.model.Transacao;
import fintrack.service.FinTracker;
import fintrack.utils.Formatador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TelaPrincipalController {

    @FXML
    private TableView<Transacao> tabelaTransacoes;
    @FXML
    private TableColumn<Transacao, String> colunaData;
    @FXML
    private TableColumn<Transacao, String> colunaDescricao;
    @FXML
    private TableColumn<Transacao, String> colunaValor;
    @FXML
    private TableColumn<Transacao, String> colunaTipo;

    private FinTracker finTracker;

    @FXML
    private void initialize() {
        colunaData.setCellValueFactory(c ->
                new SimpleStringProperty(Formatador.formatarData(c.getValue().getData())));
        colunaDescricao.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getDescricao()));
        colunaValor.setCellValueFactory(c ->
                new SimpleStringProperty(Formatador.formatarValor(c.getValue().getValor())));
        colunaTipo.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getTipo().toString()));
    }

    public void setFinTracker(FinTracker finTracker) {
        this.finTracker = finTracker;
        atualizarTabela();
    }

    private void atualizarTabela() {
        tabelaTransacoes.setItems(FXCollections.observableArrayList(finTracker.listar()));
    }
}